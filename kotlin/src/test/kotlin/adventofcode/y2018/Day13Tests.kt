package adventofcode.y2018

import io.kotlintest.data.forall
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

class Day13Tests : StringSpec({
    "Day 13, Star 1" {
        forall(
            row(0 to 3, DAY13_STAR1_TEST_A),
            row(2 to 0, DAY13_STAR1_TEST_B),
            row(7 to 3, DAY13_STAR1_TEST_C),
            row(0 to 0, DAY13_STAR1_TEST_D),
            row(38 to 72, Day13.STAR1)
        ) { result: Pair<Int, Int>, input: List<String> ->
            Day13.star1Calc(input) shouldBe result
        }
    }
    "Day 13, Star 2" {
        forall(
            row(2 to 0, DAY13_STAR2_TEST_A),
            row(6 to 4, DAY13_STAR2_TEST_B),
            row(68 to 27, Day13.STAR2)
        ) { result: Pair<Int, Int>, input: List<String> ->
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
