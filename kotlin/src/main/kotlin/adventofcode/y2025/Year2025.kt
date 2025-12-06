package adventofcode.y2025

import adventofcode.Day
import adventofcode.YearRunner
import adventofcode.utils.daysMap

object Year2025 : YearRunner {
    override val year: Int = 2025

    override val days: Map<Int, Day> = daysMap(
        Day1,
        Day2,
        Day3,
        Day4,
        Day5,
        Day6,
    )
}
