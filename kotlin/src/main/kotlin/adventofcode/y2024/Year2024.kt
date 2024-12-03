package adventofcode.y2024

import adventofcode.Day
import adventofcode.YearRunner
import adventofcode.utils.daysMap

object Year2024 : YearRunner {
    override val year: Int = 2024

    override val days: Map<Int, Day> = daysMap(
        Day1,
    )
}
