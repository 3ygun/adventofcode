package adventofcode.y2018

import adventofcode.Day
import adventofcode.y2018.data.Day2Data

object Day2 : Day {
    override val day: Int = 2

    override fun star1Run(): String {
        val result = Day2.star1Calc(Day2Data.star1())
        return "Checksum is $result"
    }

    fun star1Calc(input: List<String>): Int {
        var foundFor2 = 0
        var foundFor3 = 0

        input
            .map { parseInput(it) }
            .forEach {
                foundFor2 += it.first
                foundFor3 += it.second
            }

        if (foundFor2 == 0) foundFor2 = 1
        if (foundFor3 == 0) foundFor3 = 1

        return foundFor2 * foundFor3
    }

    private fun parseInput(input: String): Pair<Int, Int> = input
        .toCharArray()
        .groupBy { it }
        .map { Pair(it.key, it.value.size) }
        .associateBy( { it.second }, { it.first } )
        .run {
            val twoOfKind = if (this.containsKey(2)) 1 else 0
            val threeOfKind = if (this.containsKey(3)) 1 else 0

//            val foundFor2 = this[2]
//            val foundFor3 = this[3]
//
//            println("For $input, found for 2 = $foundFor2, found for 3 = $foundFor3")

            return Pair(twoOfKind, threeOfKind)
        }

    override fun star2Run(): String {
        val result = star2Calc(Day2Data.star2())
        return "Unchanged characters $result"
    }

    fun star2Calc(input: List<String>): String {
        input.forEach { checking ->
            input.forEach { against ->
                val doesNotMatch = calculateResult(checking, against)
                if (doesNotMatch == 1) {
                    return result(checking, against)
                }
            }
        }
        return "BAD"
    }

    private fun calculateResult(checking: String, against: String): Int {
        val checkingChars = checking.toCharArray()
        val againstChars = against.toCharArray()

        var doesNotMatch = 0
        for (i in 0 until checkingChars.size) {
            if (checkingChars[i] != againstChars[i]) {
                doesNotMatch++
                if (doesNotMatch > 1) {
                    return 2
                }
            }
        }
        return doesNotMatch
    }

    private fun result(checking: String, against: String): String {
        val checkingChars = checking.toCharArray()
        val againstChars = against.toCharArray()

//        println("Day 2, Star 2:")
//        println(checking)
//        println(against)

        for (i in 0 until checking.length) {
            if (checkingChars[i] != againstChars[i]) {
                return checking.removeRange(i, i+1)
            }
        }
        return "."
    }
}
