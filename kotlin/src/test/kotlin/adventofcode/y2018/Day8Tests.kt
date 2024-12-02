package adventofcode.y2018

import io.kotest.core.spec.style.StringSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class Day8Tests : StringSpec({
    "Day 8, Star 1" {
        forAll(
            row(100, "0 1 100"),
            row(138, "2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2"),
            row(41028, Day8.STAR1_DATA)
        ) { result, input ->
            Day8.star1Calc(input) shouldBe result
        }
    }
    "Day 8, Star 2" {
        forAll(
            
            row(100, "0 1 100"),
            row(66, "2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2"),
            row(20849, Day8.STAR2_DATA)
        ) { result, input ->
            Day8.star2Calc(input) shouldBe result
        }
    }
})
