package com.dsoller.adventofcode

interface YearRunner {
    fun year(): Int

    fun run(
        all: Boolean,
        day: Int
    )
}
