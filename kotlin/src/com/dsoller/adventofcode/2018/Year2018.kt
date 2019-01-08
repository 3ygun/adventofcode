package com.dsoller.adventofcode.`2018`

import com.dsoller.adventofcode.YearRunner
import kotlin.system.measureNanoTime

object Year2018 : YearRunner {
    override fun year() = 2018

    private val days = mapOf(
        1 to { println("Hello World!") }
    )

    override fun run(all: Boolean, day: Int) {
        when {
            all -> {
                days.entries.forEach { printWithDay(it.key, it.value) }
            }
            else -> {
                days[day]
                    ?.also { printWithDay(day, it) }
                    ?: throw IllegalArgumentException("Invalid day $day given for year ${year()}")
            }
        }
    }

    private fun printWithDay(
        day: Int,
        func: () -> Unit
    ) {
        println("Day $day")
        val took = measureNanoTime {
            func()
        }
        println("Day $day took ${took / 1_000_000.0} ms")
    }
}
