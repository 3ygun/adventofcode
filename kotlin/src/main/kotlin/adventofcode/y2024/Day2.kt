package adventofcode.y2024

import adventofcode.DataLoader
import adventofcode.Day
import kotlin.math.absoluteValue

// https://adventofcode.com/2024/day/2
object Day2 : Day {
    override val day: Int = 2

    internal val STAR1: List<String> get() = DataLoader.readNonBlankLinesFrom("/y2024/Day2Star1.txt")

    override fun star1Run(): String {
        val safeLevels = star1(STAR1)
        return "Safe Levels: $safeLevels"
    }

    /**
     * @return safe levels
     */
    fun star1(reports: List<String>): Long {
        return reports.sumOf { report ->
            if (report.isBlank()) return@sumOf 0L

            val levels = report.split(" ").map { it.toInt() }

            assert(levels.size > 1)

            var direction = 0
            (0..< (levels.size-1)).forEach { index ->
                val a = levels[index]
                val b = levels[index + 1]

                val compare = a.compareTo(b)
                if (index == 0) direction = compare
                if (direction != compare || compare == 0) {
                    return@sumOf 0L
                }

                val difference = (a-b).absoluteValue
                if (difference <= 0 || difference >= 4) {
                    return@sumOf 0L
                }
            }

            1L
        }
    }

    override fun star2Run(): String {
        val safeLevels = star2(STAR1)
        return "Safe Levels with Problem Dampener: $safeLevels"
    }

    /**
     * @return safe levels with problem dampener
     */
    fun star2(reports: List<String>): Long {
        return reports.sumOf { report ->
            if (report.isBlank()) return@sumOf 0L

            val levels = report.split(" ").map { it.toInt() }

            assert(levels.size > 1)

            fun test(
                levels: List<Int>,
                retry: Boolean,
            ): Boolean {
                var direction = 0
                fun isValid(index: Int, a: Int, b: Int): Boolean {
                    val compare = a.compareTo(b)
                    if (index == 0) direction = compare
                    if (direction != compare || compare == 0) {
                        return false
                    }

                    val difference = (a-b).absoluteValue
                    if (difference <= 0 || difference >= 4) {
                        return false
                    }
                    return true
                }
                for (aIndex in (0..< (levels.size-1))) {
                    val a = levels[aIndex]
                    val bIndex = aIndex + 1
                    val b = levels[bIndex]
                    if (!isValid(aIndex, a, b)) {
                        return if (retry) {
                            test(levels.filterIndexed { index, _ -> index != aIndex }, retry = false)
                            || test(levels.filterIndexed { index, _ -> index != bIndex }, retry = false)
                            || test(levels.subList(1, levels.size), retry = false)
                        } else false
                    }
                }
                return true
            }

            val isValid = test(levels, retry = true)
            if (isValid) 1L else 0L
        }
    }
}

fun main() {
    val records = """
        44 45 44 47 46
    """.trimIndent().lines()
//    Day2.star2(records)
    Day2.star2Run()
}
