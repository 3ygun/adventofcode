package adventofcode.y2024

import adventofcode.DataLoader
import adventofcode.Day
import java.lang.IllegalStateException

object Day3 : Day {
    override val day: Int = 3

    internal val STAR1: List<String> get() = DataLoader.readNonBlankLinesFrom("/y2024/Day3Star1.txt")

    override fun star1Run(): String {
        val result = star1(STAR1)
        return "Result: $result"
    }

    internal fun star1(lines: List<String>): Long {
        val regex = Regex("mul\\((\\d+),(\\d+)\\)")
        return lines.sumOf { line ->
            regex.findAll(line).sumOf {
                val groups = it.groupValues
                assert(groups.size == 3)
                val num1 = groups[1].toLong()
                val num2 = groups[2].toLong()
                num1 * num2
            }
        }
    }

    override fun star2Run(): String {
        val result = star2(STAR1)
        return "Result: $result"
    }

    internal fun star2(lines: List<String>): Long {
        val regex = Regex("(?:(do)\\(\\))|(?:(don\\\'t)\\(\\))|(?:(mul)\\((\\d+),(\\d+)\\))")
        var useNextMul = true
        return lines.sumOf { line ->
            regex.findAll(line).sumOf {
                val groups = it.groupValues
                when {
                    groups[1] == "do" -> {
                        useNextMul = true
                        0L
                    }
                    groups[2] == "don't" -> {
                        useNextMul = false
                        0L
                    }
                    groups[3] == "mul" -> {
                        if (useNextMul) {
                            val num1 = groups[4].toLong()
                            val num2 = groups[5].toLong()
                            num1 * num2
                        } else {
                            0
                        }
                    }
                    else -> throw IllegalStateException("Invalid OP: $groups")
                }
            }
        }
    }
}

fun main() {
    val input = """xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))""".lines()
    Day3.star2(input)
}
