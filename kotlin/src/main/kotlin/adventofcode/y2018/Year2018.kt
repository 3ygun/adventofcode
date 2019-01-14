package adventofcode.y2018

import adventofcode.Day
import adventofcode.YearRunner

object Year2018 : YearRunner {
    override fun year() = 2018

    private val days = mapOf(
        1 to Day1
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
