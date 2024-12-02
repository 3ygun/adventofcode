package adventofcode.y2018

import adventofcode.DataLoader
import adventofcode.Day

object Day10 : Day {
    val STAR1 = DataLoader.readLinesFromFor("/y2018/Day10Star1.txt")
    private const val STAR1_SECONDS: Int = 10519

    override val day: Int = 10

    override fun star1Run(): String {
        val result = star1Calc(STAR1_SECONDS, STAR1)
        return "The image in the stars is:\n$result"
    }

    override fun star2Run(): String = "The image appeared in $STAR1_SECONDS"

    /**
     * @return the image after the given number of iterations
     */
    fun star1Calc(
        itter: Int,
        rawInput: List<String>
    ): String {
        val stars = parse(rawInput)
        val board = Board(stars)

        repeat(itter) { board.nextItteration() }

        debug { board.toString() }
        debug { "" }
        debug { "" }
        return board.toString()
    }

    //<editor-fold desc="Star 1">

    data class Point(
        val x: Int,
        val y: Int
    )

    data class Velocity(
        val inX: Int,
        val inY: Int
    )

    class Star(
        val position: Point,
        val velocity: Velocity
    ) : Comparable<Star> {
        fun applyVelocity(): Star = Star(
            position = Point(
                x = position.x + velocity.inX,
                y = position.y + velocity.inY
            ),
            velocity = velocity
        )

        override fun compareTo(other: Star): Int {
            return when {
                position.y < other.position.y -> -1
                position.y > other.position.y -> 1
                else -> when {
                    position.x <= other.position.x -> -1
                    position.x > other.position.x -> 1
                    else -> -1 // Shouldn't get here because <= is already covered
                }
            }
        }
    }

    private fun List<Star>.dimensions(): Point {
        val maxX = maxBy { it.position.x }!!.position.x
        val maxY = maxBy { it.position.y }!!.position.y
        return Point(x = maxX, y = maxY)
    }

    private fun List<Star>.minDimensions(): Point {
        val minX = minBy { it.position.x }!!.position.x
        val minY = minBy { it.position.y }!!.position.y
        return Point(x = minX, y = minY)
    }

    private val LINE_PARSE = Regex("(?>position=<\\ *(\\-?\\d+),\\ *(\\-?\\d+)> velocity=<\\ *(\\-?\\d+),\\ *(\\-?\\d+)>)")
    private fun parse(rawInputs: List<String>): List<Star> {
        return rawInputs
            .map { input ->
                val (_, posX, posY, velX, velY) = LINE_PARSE.matchEntire(input)?.groupValues
                    ?.takeIf { it.size == 5 }
                    ?: throw IllegalArgumentException("The following line wasn't valid: '$input'")

                Star(
                    position = Point(x = posX.toInt(), y = posY.toInt()),
                    velocity = Velocity(inX = velX.toInt(), inY = velY.toInt())
                )
            }
    }

    private class Board(
        private var stars: List<Star>
    ) {

        fun nextItteration() {
            stars = stars.map { it.applyVelocity() }
        }

        override fun toString(): String {
            stars = stars.sorted()
            val maxDimensions = stars.dimensions()
            val minDimensions = stars.minDimensions()

            val (minX, minY) = minDimensions
                .let { (minX, minY) -> Math.abs(minX) to Math.abs(minY) }
            val (maxX, maxY) = maxDimensions
                .let { (maxX, maxY) -> (Math.abs(maxX) + Math.abs(minX) + 1) to (Math.abs(maxY) + Math.abs(minY) + 1) }
            var index = 0

            // This avoids the case where minX = 4 and we used to start at 4 instead of 8
            val (startingX, startingY) = minDimensions
                .let { (x, y) -> (minX + x) to (minY + y) }

            // Avoid printing too much
            if (maxX > 1000) {
                return "maxX = $maxX, maxY = $maxY"
            }

            tailrec fun boardGen(
                nextStar: Iterator<Star>,
                currentStar: Star? = null,
                currentX: Int = startingX,
                currentY: Int = startingY,
                board: StringBuilder = StringBuilder()
            ): String {
                if (currentStar == null) {
                    return when {
                        nextStar.hasNext() -> boardGen(nextStar, nextStar.next(), currentX, currentY, board)
                        else -> board.append(".".repeat(maxX - currentX)).toString()
                    }
                }

                val (sX, sY) = currentStar.position
                    .let { (sX, sY) -> (sX + minX) to (sY + minY) }
                if (sY > currentY) {
                    board.appendLine(".".repeat(maxX - currentX))
                    return boardGen(nextStar, currentStar, startingX, currentY + 1, board)
                }

                // Is it already inserted?
                if (sX < currentX) return boardGen(nextStar, null, currentX, currentY, board)
                if (sX > currentX) board.append(".".repeat(sX - currentX))

                board.append('#')
                if (debug) board.append(index)
                index++
                return boardGen(nextStar, null, sX+1, sY, board)
            }

            return boardGen(stars.iterator())
        }
    }

    //</editor-fold>
}
