package adventofcode.utils

import adventofcode.Day
import adventofcode.YearRunner

/** @return a map of day value to [Day] */
fun daysMap(vararg day: Day): Map<Int, Day> =
    day.map { it.day to it }.toMap()

/** @return a map of year value to [YearRunner] */
fun yearsMap(vararg yearRunner: YearRunner): Map<Int, YearRunner> =
    yearRunner.map { it.year to it }.toMap()
