package adventofcode.y2018

import adventofcode.Day
import adventofcode.y2018.data.Day1Data

object Day1 : Day {
    override val day = 1

    override fun star1Run(): String {
        val result = star1Calc(Day1Data.star1())
        return "Frequency is $result"
    }

    fun star1Calc(input: List<String>): Int {
        return input
            .map { parseFrequency(it) }
            .sum()
    }

    override fun star2Run(): String {
        val result = star2Calc(Day1Data.star2())
        return "Frequency is $result"
    }

    fun star2Calc(input: List<String>): Long {
        val inputs: List<Int> = input.map { parseFrequency(it) }
        var currentVal: Long = 0
        val foundFreq: MutableList<Long> = ArrayList()
        var nextIndex = 0

        while (!foundFreq.contains(currentVal)) {
            foundFreq.add(currentVal)
            val (index, value) = nextArg(nextIndex, inputs)
            nextIndex = index
            currentVal += value
        }

        return currentVal
    }

    fun nextArg(index: Int, input: List<Int>): Next  {
        if (input.size <= index) {
            return nextArg(0, input)
        }

        val value = input[index]
        return Next(index + 1, value)
    }

    data class Next(val index: Int, val value: Int)

    @Throws(IllegalArgumentException::class)
    private fun parseFrequency(todo: String): Int {
        if (todo.isEmpty()) return 0
        val opp = todo.substring(0, 1)
        val num = todo.substring(1).toInt()

        return when (opp) {
            "+" -> num
            "-" -> -1 * num
            else -> throw IllegalArgumentException("Received: $todo")
        }
    }
}
