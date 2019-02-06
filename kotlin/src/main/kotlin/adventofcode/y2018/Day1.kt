package adventofcode.y2018

import adventofcode.DataLoader
import adventofcode.Day

object Day1 : Day {
    val STAR1_DATA = DataLoader.readLinesFromFor("/y2018/Day1Star1.txt")
    val STAR2_DATA = DataLoader.readLinesFromFor("/y2018/Day1Star2.txt")

    override val day = 1

    override fun star1Run(): String {
        val result = star1Calc(STAR1_DATA)
        return "Frequency is $result"
    }

    fun star1Calc(input: List<String>): Int {
        return input
            .map { parseFrequency(it) }
            .sum()
    }

    override fun star2Run(): String {
        val result = star2Calc(STAR2_DATA)
        return "Frequency is $result"
    }

    fun star2Calc(input: List<String>): Int {
        val inputs: List<Int> = input.map { parseFrequency(it) }
        var currentVal = 0
        val foundFreq: MutableSet<Int> = HashSet()
        var nextIndex = 0
        val inputSize = inputs.size

        while (!foundFreq.contains(currentVal)) {
            foundFreq.add(currentVal)
            if (inputSize <= nextIndex) nextIndex = 0

            currentVal += inputs[nextIndex]
            nextIndex++
        }

        return currentVal
    }

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
