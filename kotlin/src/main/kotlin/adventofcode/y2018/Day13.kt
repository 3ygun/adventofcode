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
        } catch (cartCollision: CartCollision) {
            cartCollision.xy
        }
    }

    internal fun star2Calc(input: List<String>): Pair<Int, Int> {
        return try {
            val board = Board.parse(input)
            board.findLastCart()
        } catch (cartCollision: CartCollision) {
            cartCollision.xy
        }
    }

    private class Board private constructor(
        private var carts: List<Cart>,
        private val board: Array<CharArray>
    ) {
        fun findAndThrowOnCollision(): Pair<Int, Int> = iterateUntilFirstColision(carts)

        private tailrec fun iterateUntilFirstColision(carts: List<Cart>, itter: Int = 0): Pair<Int, Int> {
            debug { "Iterator: $itter" }
            debug { toString() /* Board */ }

            val cartMoves: List<CartMoved> = carts.map { it.move(board) }
            val cartCollision = cartMoves.firstOrNull { it.collision }
            if (cartCollision != null) return cartCollision.cart.xy

            val newCarts = cartMoves
                .map { it.cart }
                .sorted()
            this.carts = newCarts

            return iterateUntilFirstColision(newCarts, itter + 1)
        }

        fun findLastCart(): Pair<Int, Int> = iterateUntilSingleCart(carts)

        private tailrec fun iterateUntilSingleCart(carts: List<Cart>, itter: Int = 0): Pair<Int, Int> {
            debug { "Iterator: $itter" }
            debug { toString() /* Board */ }

            val cartMoves: List<CartMoved> = carts.map { it.move(board) }

            val removedCarts = cartMoves
                .filter { it.collision }
                .map {
                    val cart = it.cart
                    val (x, y) = cart.xy
                    if (cart.previousBoardPiece != 'X') board[y][x] = cart.previousBoardPiece
                    cart
                }
            if (removedCarts.isNotEmpty()) {
                debug { "Removed Carts was empty!" }
                debug { "Iterator: $itter" }
                debug { toString() /* Board */ }
            }

            val finalCarts = cartMoves
                .filter { !it.collision }
                .map { it.cart }
                .filter { cart ->
                    val collided = removedCarts.any { it.xy == cart.xy }
                    if (collided) {
                        val (x, y) = cart.xy
                        if (cart.previousBoardPiece != 'X') board[y][x] = cart.previousBoardPiece
                    }
                    !collided
                }
            this.carts = finalCarts

            return when {
                finalCarts.size <= 1 -> finalCarts.first().xy
                else -> iterateUntilSingleCart(finalCarts.sorted(), itter + 1)
            }
        }

        override fun toString(): String {
            val result = StringBuilder()
            board.forEach { row -> result.appendLine(row) }
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
        val xy: Pair<Int, Int>,
        val direction: Direction,
        val previousBoardPiece: Char,
        private val atIntersection: IntersectionMovement = IntersectionMovement.LEFT
    ) : Comparable<Cart> {
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

        fun move(board: Array<CharArray>): CartMoved {
            // Check if someone ran into us?
            val (ox, oy) = xy
            val currentBP = board[oy][ox]
            if (currentBP == 'X') return CartMoved(true, this) // Yup, someone did :(

            // We weren't run into, fill in our old location
            board[oy][ox] = previousBoardPiece

            // Calculate where we're going and test if we run into someone else
            val newPos = direction.move(xy)
            val (nx, ny) = newPos
            val newBoardPiece = board[ny][nx]
            var collision = false
            var newAtIntersection = atIntersection
            val newDirection = when (newBoardPiece) {
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
                '+' -> when (atIntersection) {
                    IntersectionMovement.LEFT -> {
                        newAtIntersection = IntersectionMovement.STRAIT
                        when (direction) {
                            Direction.UP -> Direction.LEFT
                            Direction.DOWN -> Direction.RIGHT
                            Direction.LEFT -> Direction.DOWN
                            Direction.RIGHT -> Direction.UP
                        }
                    }
                    IntersectionMovement.STRAIT -> {
                        newAtIntersection = IntersectionMovement.RIGHT
                        direction
                    }
                    IntersectionMovement.RIGHT -> {
                        newAtIntersection = IntersectionMovement.LEFT
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
                    direction // We need a direction but don't just say we continue on with the old one
                }
                else -> throw IllegalArgumentException("Unknown board piece $newBoardPiece at $xy")
            }

            // Handle displaying our position on the board
            val newFinalBoardPiece = when {
                collision -> {
                    // Note that there was a collision here
                    // Also signals to future pieces in this iteration that we ran into them
                    board[ny][nx] = 'X'
                    'X'
                }
                else -> {
                    // We didn't run into anyone so put us on the map and save off the old piece
                    board[ny][nx] = direction.symbol
                    newBoardPiece
                }
            }

            return CartMoved(
                collision = collision,
                cart = Cart(
                    xy = newPos,
                    direction = newDirection,
                    previousBoardPiece = newFinalBoardPiece,
                    atIntersection = newAtIntersection
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
