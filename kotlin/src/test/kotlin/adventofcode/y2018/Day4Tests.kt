package adventofcode.y2018

import io.kotest.core.spec.style.StringSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class Day4Tests : StringSpec({
    "Day 4, Star 1" {
        forAll(
            row(240, star1Input1.lines()), // GuardId 10, FavoriteMinute 24
            row(87681, Day4.STAR1_DATA)
        ) { result, input ->
            Day4.star1Calc(input).guardIdTimesFavoriteMinute() shouldBe result
        }
    }

    "Day 4, Star 2" {
        forAll(
            row(4455, star1Input1.lines()), // GuardId 99, FavoriteMinute 45
            row(136461, Day4.STAR2_DATA)
        ) { result, input ->
            Day4.star2Calc(input).guardIdTimesFavoriteMinute() shouldBe result
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
