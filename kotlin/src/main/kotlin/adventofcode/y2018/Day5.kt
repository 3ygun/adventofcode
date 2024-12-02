package adventofcode.y2018

import adventofcode.DataLoader
import adventofcode.Day
import kotlin.math.max

object Day5 : Day {
    val STAR1_DATA = DataLoader.readLinesFromFor("/y2018/Day5Star1.txt").single()
    val STAR2_DATA = STAR1_DATA

    override val day: Int = 5

    override fun star1Run(): String {
        val result = star1Calc(STAR1_DATA)
        return "Resulting polymer is length ${result.length}"
    }

    override fun star2Run(): String {
        val result = star2Calc(STAR2_DATA)
        return "Resulting polymer is length $result"
    }

    fun star1Calc(input: String): String {
        return removeJoinedItems(input)
    }

    fun star2Calc(input: String): Int {
        val intermediate = removeJoinedItems(input)

        val result = ('a'..'z')
            .map { c -> Regex("$c|${c.uppercaseChar()}").replace(intermediate, "") }
            .map { removeJoinedItems(it) }
            .minBy { it.length }!!

        debug { "${result.length} or $result" }
        return result.length
    }

    private tailrec fun removeJoinedItems(input: String, startingIndex: Int = 0): String {
        for (i in startingIndex until (input.length-1)) {
            val char1 = input[i]
            val char2 = input[i+1]

            if (char1 == char2) continue
            if (char1 == char2.lowercaseChar() || char1.lowercaseChar() == char2) {
                return removeJoinedItems(
                    input = input.removeRange(i, i+2),
                    startingIndex = max(i-1, 0)
                )
            }
        }

        return input // Nothing changed so we're good
    }
}
