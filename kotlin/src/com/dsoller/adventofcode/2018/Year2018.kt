package com.dsoller.adventofcode.`2018`

import com.dsoller.adventofcode.YearRunner

object Year2018 : YearRunner {
    override fun year() = 2018

    private val days = mapOf(
        1 to { println("Hello World!") }
    )

    override fun run(all: Boolean, day: Int) {
        when {
            all -> {
                days.entries.forEach {
                    println("Day ${it.key}")
                    it.value()
                }
            }
            else -> {
                days[day]
                    ?.also {
                        println("Day $day:")
                        it()
                    }
                    ?: throw IllegalArgumentException("Invalid day $day given for year ${year()}")
            }
        }
    }
}
