package adventofcode.y2018

import io.kotest.core.spec.style.StringSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class Day3Tests : StringSpec({
    "Day 3, Star 1" {
        forAll(
            row(4, listOf("#1 @ 1,3: 4x4", "#2 @ 3,1: 4x4", "#3 @ 5,5: 2x2")),
            row(4, listOf("#1 @ 1,3: 4x4", "#2 @ 3,1: 4x4", "#3 @ 5,5: 0x0")),
            row(0, listOf("#1 @ 1,3: 2x2", "#2 @ 3,1: 2x2", "#3 @ 5,5: 0x0")),
            row(107043, Day3.STAR1_DATA)
        ) { result, input ->
            Day3.star1Calc(input) shouldBe result
        }
    }
    "Day 3, Star 2" {
        forAll(
            row(3, listOf("#1 @ 1,3: 4x4", "#2 @ 3,1: 4x4", "#3 @ 5,5: 2x2")),
            row(4, listOf("#1 @ 1,3: 4x4", "#2 @ 3,1: 4x4", "#3 @ 5,5: 0x0", "#4 @ 6,6: 1x1")),
            row(1, listOf("#1 @ 1,3: 2x2", "#2 @ 3,1: 2x2", "#3 @ 4,2: 1x1")),
            row(346, Day3.STAR2_DATA)
        ) { result, input ->
            Day3.star2Calc(input) shouldBe result
        }
    }
})
