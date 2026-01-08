package adventofcode.y2025

import adventofcode.DataLoader
import adventofcode.Day

object Day9 : Day {
    override val day: Int = 9
    override val debug: Boolean get() = false

    internal val STAR1 get() = DataLoader.readNonBlankLinesFrom("/y2025/Day9Star1.txt")

    private val EXAMPLE get() = """
        7,1
        11,1
        11,7
        9,7
        9,5
        2,5
        2,3
        7,3
    """.trimIndent().lines()

    override fun star1Run(): String {
        // 4729332959 (from ((85705, 83098), (16429, 14832)))
        val lines = STAR1
        // 50 between 2,5 and 11,1:
//        val lines = EXAMPLE

        val redTiles = lines.map { it.split(",").let { (a, b) -> a.toLong() to b.toLong() } }

        var largestArea = 0L
        var largestPair = (-1L to -1L) to (-1L to -1L)
        redTiles.forEachIndexed { i, a ->
            for (j in (i + 1) until redTiles.size) {
                val b = redTiles[j]
                val size = when {
                    a.first == b.first -> when {
                        a.second == b.second -> 1L
                        a.second > b.second -> 1 + a.second - b.second
                        else -> 1 + b.second - a.second
                    }
                    a.first > b.first -> {
                        val x = 1 + a.first - b.first
                        when {
                            a.second == b.second -> x
                            a.second > b.second -> x * (1 + a.second - b.second)
                            else -> x * (1 + b.second - a.second)
                        }
                    }
                    else -> {
                        val x = 1 + b.first - a.first
                        when {
                            a.second == b.second -> x
                            a.second > b.second -> x * (1 + a.second - b.second)
                            else -> x * (1 + b.second - a.second)
                        }
                    }
                }

                if (size > largestArea) {
                    largestArea = size
                    largestPair = a to b
                }
            }
        }

        return "Largest area: $largestArea (from $largestPair)"
    }

    override fun star2Run(): String {
        return ""
    }
}