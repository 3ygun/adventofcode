package adventofcode.y2018

import adventofcode.DataLoader
import adventofcode.Day

object Day13 : Day {
    internal val STAR1 = DataLoader.readLinesFromFor("/y2018/Day13Star1.txt")
    internal val STAR2 = STAR1

    override val day = 13

    override fun star1Run(): String {
        val (x, y) = star1Calc(STAR1)
        return "The collision was at x,y: $x,$y"
    }

    override fun star2Run(): String {
        val (x, y) = star2Calc(STAR2)
        return "The collision was at x,y: $x,$y"
    }

    internal fun star1Calc(input: List<String>): Pair<Int, Int> {
        return try {
            val board = Board.parse(input)
            board.findAndThrowOnCollision()
            -1 to -1 // Shouldn't get here
        } catch (cartCollision: CartCollision) {
            cartCollision.xy
        }
//        val board = Board.parse(input)
//        return board.findAndThrowOnCollision()
    }

    internal fun star2Calc(input: List<String>): Pair<Int, Int> {
        val board = Board.parse(input)
        return board.findLastCart()
    }

    private class Board private constructor(
        private val carts: List<Cart>,
        private val board: Array<CharArray>
    ) {
        fun findAndThrowOnCollision() = iterateCarts(carts)
//        fun findAndThrowOnCollision(): Pair<Int, Int> = iterateCarts(carts)

        private tailrec fun iterateCarts(carts: List<Cart>, itter: Int = 0) {
            //println(toString())
            println(itter)

            carts.forEach { it.move(board) }
            return iterateCarts(carts.sorted(), itter + 1)
        }

//        private tailrec fun iterateCarts(carts: List<Cart>, itter: Int = 0): Pair<Int, Int> {
//            //println(toString())
////            println(itter)
//
//            val cartMoves: List<CartMoved> = carts.map { it.move2(board) }
//            val cartCollision = cartMoves.firstOrNull { it.collision }
//            if (cartCollision != null) return cartCollision.cart.xy
//
//            val newCarts = cartMoves
//                .map { it.cart }
//                .sorted()
//
//            return iterateCarts(newCarts, itter + 1)
//        }

        fun findLastCart(): Pair<Int, Int> = iterateCartsUntil1(carts)

        private tailrec fun iterateCartsUntil1(carts: List<Cart>, itter: Int = 0): Pair<Int, Int> {
//            println(toString())

            val cartMoves: List<CartMoved> = carts.map { it.move2(board) }

            val removedCarts = cartMoves
                .filter { it.collision }
                .map { it.cart }

            val finalCarts = cartMoves
                .filter { !it.collision }
                .map { it.cart }
                .filter { cart ->
                    val collided = removedCarts.any { it.xy == cart.xy }
                    if (collided) {
                        val (x, y) = cart.xy
                        board[y][x] = cart.previousBoardPiece
                    }
                    !collided
                }

            return when {
                finalCarts.size <= 1 -> finalCarts.first().xy
                else -> iterateCartsUntil1(finalCarts.sorted(), itter + 1)
            }
        }

        override fun toString(): String {
            val result = StringBuilder()
            board.forEach { row -> result.appendln(row) }
            return result.toString()
        }

        companion object {
            @JvmStatic
            fun parse(input: List<String>): Board {
                val yAxis = input.size
                val xAxis = input.map { it.length }.max()!!

                val carts: MutableList<Cart> = mutableListOf()
                val board = Array(yAxis) { CharArray(xAxis) { ' ' } }
                input.forEachIndexed { y, path ->
                    path.forEachIndexed { x, char ->
                        if (char == 'X') throw CartCollision(x to y)

                        board[y][x] = char
                        Direction.parse(char)
                            ?.let { carts.add(Cart(x to y, it)) }
                    }
                }

                return Board(carts.sorted(), board)
            }
        }
    }

    private class Cart private constructor(
        xy: Pair<Int, Int>,
        direction: Direction,
        previousBoardPiece: Char
    ) : Comparable<Cart> {
        var xy: Pair<Int, Int> = xy
            private set
        var direction: Direction = direction
            private set
        var previousBoardPiece: Char = previousBoardPiece
            private set
        private var nextIntersectionMovement: IntersectionMovement = IntersectionMovement.LEFT

        constructor(
            xy: Pair<Int, Int>,
            direction: Direction
        ) : this(xy, direction, direction.board)

        override fun compareTo(other: Cart): Int {
            val (x, y) = xy
            val (ox, oy) = other.xy
            return when {
                y < oy -> -1
                y > oy -> 1
                else -> x.compareTo(ox)
            }
        }

        fun move(board: Array<CharArray>) {
            board[xy.second][xy.first] = previousBoardPiece
            val newPos = direction.move(xy)
            val boardPoint = board[newPos.second][newPos.first]
            val newDirection = when (boardPoint) {
                '|' -> direction
                '-' -> direction
                '/' -> when (direction) {
                    Direction.UP -> Direction.RIGHT
                    Direction.DOWN -> Direction.LEFT
                    Direction.LEFT -> Direction.DOWN
                    Direction.RIGHT -> Direction.UP
                }
                '\\' -> when (direction) {
                    Direction.UP -> Direction.LEFT
                    Direction.DOWN -> Direction.RIGHT
                    Direction.LEFT -> Direction.UP
                    Direction.RIGHT -> Direction.DOWN
                }
                '+' -> when (nextIntersectionMovement) {
                    IntersectionMovement.LEFT -> {
                        nextIntersectionMovement = IntersectionMovement.STRAIT
                        when (direction) {
                            Direction.UP -> Direction.LEFT
                            Direction.DOWN -> Direction.RIGHT
                            Direction.LEFT -> Direction.DOWN
                            Direction.RIGHT -> Direction.UP
                        }
                    }
                    IntersectionMovement.STRAIT -> {
                        nextIntersectionMovement = IntersectionMovement.RIGHT
                        direction
                    }
                    IntersectionMovement.RIGHT -> {
                        nextIntersectionMovement = IntersectionMovement.LEFT
                        when (direction) {
                            Direction.UP -> Direction.RIGHT
                            Direction.DOWN -> Direction.LEFT
                            Direction.LEFT -> Direction.UP
                            Direction.RIGHT -> Direction.DOWN
                        }
                    }
                }
                '^', 'v', '<', '>' -> throw CartCollision(newPos)
                else -> throw IllegalArgumentException("Unknown boardPoint $boardPoint at $xy")
            }

            xy = newPos
            previousBoardPiece = boardPoint
            direction = newDirection
            board[newPos.second][newPos.first] = direction.symbol
        }

        fun move2(board: Array<CharArray>): CartMoved {
            board[xy.second][xy.first] = previousBoardPiece
            val newPos = direction.move(xy)
            val boardPoint = board[newPos.second][newPos.first]
            var collision = false
            val newDirection = when (boardPoint) {
                '|' -> direction
                '-' -> direction
                '/' -> when (direction) {
                    Direction.UP -> Direction.RIGHT
                    Direction.DOWN -> Direction.LEFT
                    Direction.LEFT -> Direction.DOWN
                    Direction.RIGHT -> Direction.UP
                }
                '\\' -> when (direction) {
                    Direction.UP -> Direction.LEFT
                    Direction.DOWN -> Direction.RIGHT
                    Direction.LEFT -> Direction.UP
                    Direction.RIGHT -> Direction.DOWN
                }
                '+' -> when (nextIntersectionMovement) {
                    IntersectionMovement.LEFT -> {
                        nextIntersectionMovement = IntersectionMovement.STRAIT
                        when (direction) {
                            Direction.UP -> Direction.LEFT
                            Direction.DOWN -> Direction.RIGHT
                            Direction.LEFT -> Direction.DOWN
                            Direction.RIGHT -> Direction.UP
                        }
                    }
                    IntersectionMovement.STRAIT -> {
                        nextIntersectionMovement = IntersectionMovement.RIGHT
                        direction
                    }
                    IntersectionMovement.RIGHT -> {
                        nextIntersectionMovement = IntersectionMovement.LEFT
                        when (direction) {
                            Direction.UP -> Direction.RIGHT
                            Direction.DOWN -> Direction.LEFT
                            Direction.LEFT -> Direction.UP
                            Direction.RIGHT -> Direction.DOWN
                        }
                    }
                }
                '^', 'v', '<', '>' -> {
                    collision = true
                    direction
                }
                else -> throw IllegalArgumentException("Unknown boardPoint $boardPoint at $xy")
            }

            // Will need to handle cleaning up the other part
            when {
                collision -> board[newPos.second][newPos.first] = 'X' // To show if something wasn't cleaned up
                else -> board[newPos.second][newPos.first] = direction.symbol
            }

            return CartMoved(
                collision = collision,
                cart = Cart(
                    xy = newPos,
                    direction = newDirection,
                    previousBoardPiece = boardPoint
                )
            )
        }
    }



    private enum class Direction(
        val symbol: Char,
        val board: Char,
        val move: (Pair<Int, Int>) -> Pair<Int, Int>
    ) {
        UP('^', '|', { (x, y) -> x to (y - 1) }),
        DOWN('v', '|', { (x, y) -> x to (y + 1) }),
        LEFT('<', '-', { (x, y) -> (x - 1) to y }),
        RIGHT('>', '-', { (x, y) -> (x + 1) to y }),
        ;

        companion object {
            @JvmStatic
            fun parse(c: Char): Direction? = values().firstOrNull { it.symbol == c }
        }
    }

    private enum class IntersectionMovement {
        LEFT, RIGHT, STRAIT
    }

    private class CartCollision(
        val xy: Pair<Int, Int>
    ) : Exception()

    private data class CartMoved(
        val collision: Boolean,
        val cart: Cart
    )
}