package adventofcode.y2018

import adventofcode.Day
import adventofcode.YearRunner

object Year2018 : YearRunner {
    override fun year() = 2018

    private val days = mapOf(
        1 to Day1,
        2 to Day2,
        3 to Day3,
        4 to Day4,
        5 to Day5,
        6 to Day6,
        7 to Day7,
        8 to Day8,
        9 to Day9,
        10 to Day10
    )

    override fun run(all: Boolean, day: Int) {
        when {
            all -> {
                days.entries.forEach { printWithDay(it.value) }
            }
            else -> {
                days[day]
                    ?.also { printWithDay(it) }
                    ?: throw IllegalArgumentException("Invalid day $day given for year ${year()}")
            }
        }
    }

    private fun printWithDay(day: Day) {
        day.star1()
        day.star2()
    }
}
