package adventofcode.y2025

import adventofcode.DataLoader
import adventofcode.Day
import kotlin.math.abs

// https://adventofcode.com/2025/day/1
object Day1 : Day {
    override val day: Int = 1

    internal val STAR1: List<String> get() = DataLoader.readNonBlankLinesFrom("/y2025/Day1Star1.txt")

    // STAR1 == 3
    // STAR2 == 6
    private val EXAMPLE get() = """
    L68
    L30
    R48
    L5
    R60
    L55
    L1
    L99
    R14
    L82
    """.trimIndent().lines()

    override fun star1Run(): String {
        // Range is 0 to 99
        var current = 50
        var numberOfZeros = 0

        STAR1.forEach { move ->
            val direction = move[0]
            val distance = move.substring(1).toInt()
            current = when (direction) {
                'L' -> current + distance
                'R' -> current - distance
                else -> throw IllegalArgumentException("Invalid direction: $direction")
            }
                .let { it % 100 }
                .let { if (it < 0) it + 100 else it }

            if (current == 0) numberOfZeros++
        }

        return "Actual password is: $numberOfZeros"
    }

    override fun star2Run(): String {
        // Range is 0 to 99
        var current = 50
        var numberOfZeros = 0

        STAR1.forEach { move ->
            val direction = move[0]
            val distance = move.substring(1).toInt()
            var pastZero = 0
            current = when (direction) {
                'L' -> current - distance
                'R' -> current + distance
                else -> throw IllegalArgumentException("Invalid direction: $direction")
            }
                .let {
                    val newValue = it % 100
                    pastZero = when {
                        it > 0 && newValue == 0 -> -1
                        current > 0 && newValue < 0 -> 1
                        else -> 0
                    }.let { adjustment -> abs(it / 100) + adjustment }
                    newValue
                }
                .let { if (it < 0) it + 100 else it }

            if (current == 0) numberOfZeros++
            if (pastZero > 0) numberOfZeros+=pastZero
            //println("$move at $current with $numberOfZeros")
        }

        return "Method 0x434C49434B password is: $numberOfZeros"
    }
}
