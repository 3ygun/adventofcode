package adventofcode.y2018

import adventofcode.y2018.data.Day1Data

object Day1 {
    fun star1() {
        val result = star1Calc(Day1Data.star1())
        println("Frequency is $result")
    }

    fun star1Calc(input: List<String>): Int {
        return input.map { todo ->
            if (todo.isEmpty()) return@map 0
            val opp = todo.substring(0, 1)
            val num = todo.substring(1).toInt()

            when (opp) {
                "+" -> num
                "-" -> -1 * num
                else -> throw IllegalArgumentException("Received: $todo")
            }
        }.sum()
    }
}
