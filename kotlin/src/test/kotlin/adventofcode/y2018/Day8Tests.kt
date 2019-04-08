package adventofcode.y2018

import io.kotlintest.data.forall
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

class Day8Tests : StringSpec({
    "Day 8, Star 1" {
        forall(
            row(100, "0 1 100"),
            row(138, "2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2"),
            row(41028, Day8.STAR1_DATA)
        ) { result, input ->
            Day8.star1Calc(input) shouldBe result
        }
    }
    "Day 8, Star 2" {
        forall(
            row(100, "0 1 100"),
            row(66, "2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2"),
            row(20849, Day8.STAR2_DATA)
        ) { result, input ->
            Day8.star2Calc(input) shouldBe result
        }
    }
})
