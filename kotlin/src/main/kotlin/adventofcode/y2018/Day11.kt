package adventofcode.y2018

import adventofcode.Day
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

object Day11 : Day {
    const val STAR1 = 8772
    const val STAR2 = STAR1

    override val day: Int = 11

    override fun star1Run(): String {
        val result = star1Calc(STAR1)
        // The X,Y coordinate of the top-left fuel cell of the 3x3 square
        return "The X,Y coordinate is: ${result.tlx},${result.tly} (producing total power of ${result.totalPower})"
    }

    override fun star2Run(): String {
        val result = star2Calc(STAR2)
        // The X,Y coordinate of the top-left fuel cell of the 3x3 square
        return "The X,Y,Slide coordinate is: ${result.tlx},${result.tly},${result.size} (producing total power of ${result.totalPower})"
    }

    internal data class Day11Result(
        val tlx: Int,
        val tly: Int,
        val size: Int,
        val totalPower: Int
    )

    private fun createBoard(gridSerialNumber: Int): Array<IntArray> {
        val board = Array(300) { IntArray(300) }

        // Fill in the board
        for (y in 0 until 300) {
            for (x in 0 until 300) {
                board[y][x] = star1PointCalc(x, y, gridSerialNumber)
            }
        }

        return board
    }

    internal fun star1Calc(gridSerialNumber: Int): Day11Result {
        val board = createBoard(gridSerialNumber)

        // Calculate
        var result = Day11Result(0, 0, 0, 0)
        (0 until 298).forEach { y ->
            (0 until 298).forEach { x ->
                var squareResult = 0
                for (sy in 0 until 3) {
                    for (sx in 0 until 3) {
                        squareResult += board[y + sy][x + sx]
                    }
                }

                if (result.totalPower < squareResult) {
                    result = Day11Result(x, y, 3, squareResult)
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

    internal fun star2Calc(gridSerialNumber: Int): Day11Result {
        val board = createBoard(gridSerialNumber)

        // Calculate
        val results = Channel<Day11Result>()
        for (y in 299 downTo 0) {
            GlobalScope.launch {
                results.send(calculate(y, board))
            }
        }

        var result = Day11Result(0, 0, 0, 0)
        runBlocking {
            repeat(300) {
                val aResult = results.receive()
                if (aResult.totalPower > result.totalPower) result = aResult
            }
        }
        results.close()

        return result
    }

    private fun calculate(y: Int, board: Array<IntArray>): Day11Result {
        var result = Day11Result(0, 0, 0, 0)
        for (x in 299 downTo 0) {
            // Want to avoid recalculating the old square multiple times
            var lastSquareResult = 0
            for (side in 1 .. (299 - Math.max(y, x))) {
                var squareResult = 0

                // Bottom row
                for (sx in 0 until side) {
                    squareResult += board[y + side - 1][x + sx]
                }

                // Vertical column, -1 to avoid counting bottom right corner twice
                for (sy in 0 until (side - 1)) {
                    squareResult += board[y + sy][x + side - 1]
                }

                squareResult += lastSquareResult
                lastSquareResult = squareResult

                if (result.totalPower < squareResult) {
                    result = Day11Result(x, y, side, squareResult)
                }
            }
        }
        return result
    }
}
