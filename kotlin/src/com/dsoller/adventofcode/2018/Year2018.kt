package com.dsoller.adventofcode.`2018`

import com.dsoller.adventofcode.YearRunner

object Year2018 : YearRunner {
    override fun year() = 2018

    private val days = mapOf(
        1 to { println("Hello World!") }
    )

    override fun run(all: Boolean, day: Int) {
        when {
            all -> days.values.forEach { it() }
            else -> days[day]?.invoke()
                ?: throw IllegalArgumentException("Invalid day $day given for year ${this.year()}")
        }
    }
}
