package adventofcode.y2018

import adventofcode.Day

object Day11 : Day {
    const val STAR1 = 8772

    override val day: Int = 11

    override fun star1Run(): String {
        val result = star1Calc(STAR1)
        // The X,Y coordinate of the top-left fuel cell of the 3x3 square
        return "The X,Y coordinate is: ${result.tlx},${result.tly} (producing total power of ${result.totalPower})"
    }

    override fun star2Run(): String {
        TODO("build")
    }

    internal fun star1Calc(gridSerialNumber: Int): Day11Star1Result {
        val board = Array(300) { IntArray(300) }

        // Fill in the board
        (0 until 300).forEach { y ->
            (0 until 300).forEach { x ->
                board[y][x] = star1PointCalc(x, y, gridSerialNumber)
            }
        }

        // Calculate
        var result = Day11Star1Result(0, 0, 0)
        (0 until 298).forEach { y ->
            (0 until 298).forEach { x ->
                var squareResult = 0
                for (sy in 0 until 3) {
                    for (sx in 0 until 3) {
                        squareResult += board[y + sy][x + sx]
                    }
                }

                if (result.totalPower < squareResult) {
                    result = Day11Star1Result(x, y, squareResult)
                }
            }
        }

        return result
    }

    internal fun star1PointCalc(
        x: Int,
        y: Int,
        gridSerialNumber: Int
    ): Int {
        // Find the fuel cell's rack ID, which is its X coordinate plus 10.
        val rackId: Long = x + 10L

        // Begin with a power level of the rack ID times the Y coordinate.
        var powerLevel = rackId * y

        // Increase the power level by the value of the grid serial number (your puzzle input).
        powerLevel += gridSerialNumber

        // Set the power level to itself multiplied by the rack ID.
        powerLevel *= rackId

        // Keep only the hundreds digit of the power level (so 12345 becomes 3; numbers with no hundreds digit become 0).
        powerLevel = ((powerLevel % 1000) / 100)

        // Subtract 5 from the power level.
        return powerLevel.toInt() - 5
    }

    internal data class Day11Star1Result(
        val tlx: Int,
        val tly: Int,
        val totalPower: Int
    )
}
