package adventofcode.y2018

import io.kotlintest.data.forall
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

class Day4Tests : StringSpec({
    "Day 4, Star 1" {
        forall(
            row(Day4.GuardIdWithFavoriteMinute(10, 24), star1Input1.lines())
        ) { result, input ->
            Day4.star1Calc(input) shouldBe result
        }
    }

    "Day 4, Star 2" {
        forall(
            row(Day4.GuardIdWithFavoriteMinute(99, 45), star1Input1.lines())
        ) { result, input ->
            Day4.star2Calc(input) shouldBe result
        }
    }
})

private const val star1Input1 = """[1518-11-01 00:00] Guard #10 begins shift
[1518-11-01 00:05] falls asleep
[1518-11-01 00:25] wakes up
[1518-11-01 00:30] falls asleep
[1518-11-01 00:55] wakes up
[1518-11-01 23:58] Guard #99 begins shift
[1518-11-02 00:40] falls asleep
[1518-11-02 00:50] wakes up
[1518-11-03 00:05] Guard #10 begins shift
[1518-11-03 00:24] falls asleep
[1518-11-03 00:29] wakes up
[1518-11-04 00:02] Guard #99 begins shift
[1518-11-04 00:36] falls asleep
[1518-11-04 00:46] wakes up
[1518-11-05 00:03] Guard #99 begins shift
[1518-11-05 00:45] falls asleep
[1518-11-05 00:55] wakes up"""
