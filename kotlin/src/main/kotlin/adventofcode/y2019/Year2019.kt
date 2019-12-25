package adventofcode.y2019

import adventofcode.YearRunner
import adventofcode.utils.daysMap

object Year2019 : YearRunner {
    override val year = 2019
    override val days = daysMap(
        Day1,
        Day2
    )
}
