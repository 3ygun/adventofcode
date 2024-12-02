package adventofcode.y2018

import io.kotest.core.spec.style.FunSpec
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class Day13Tests : FunSpec({
    listOf(
        row(0 to 3, DAY13_STAR1_TEST_A, "Test A"),
        row(2 to 0, DAY13_STAR1_TEST_B, "Test B"),
        row(7 to 3, DAY13_STAR1_TEST_C, "Test C"),
        row(0 to 0, DAY13_STAR1_TEST_D, "Test D"),
        row(38 to 72, Day13.STAR1, "Star 1")
    ).map { (result: Pair<Int, Int>, input: List<String>, description: String) ->
        test("Day 13, Star 1 : $description") {
            Day13.star1Calc(input) shouldBe result
        }
    }

    listOf(
        row(2 to 0, DAY13_STAR2_TEST_A, "Test A"),
        row(6 to 4, DAY13_STAR2_TEST_B, "Test B"),
        row(68 to 27, Day13.STAR2, "Star 2")
    ).map { (result: Pair<Int, Int>, input: List<String>, description: String) ->
        test("Day 13, Star 2 : $description") {
            Day13.star2Calc(input) shouldBe result
        }
    }
})

private val DAY13_STAR1_TEST_A = """|
v
|
|
|
^
|""".lines()

private val DAY13_STAR1_TEST_B = """>--<""".lines()

private val DAY13_STAR1_TEST_C = """/->-\
|   |  /----\
| /-+--+-\  |
| | |  | v  |
\-+-/  \-+--/
  \------/   """.lines()

private val DAY13_STAR1_TEST_D = """X""".lines()

private val DAY13_STAR2_TEST_A = """>->---<""".lines()

private val DAY13_STAR2_TEST_B = """/>-<\
|   |
| /<+-\
| | | v
\>+</ |
  |   ^
  \<->/""".lines()
