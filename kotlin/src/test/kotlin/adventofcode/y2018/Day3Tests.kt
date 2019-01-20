package adventofcode.y2018

import io.kotlintest.data.forall
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

class Day3Tests : StringSpec({
    "Day 3, Star 1" {
        forall(
            row(4, listOf("#1 @ 1,3: 4x4", "#2 @ 3,1: 4x4", "#3 @ 5,5: 2x2")),
            row(4, listOf("#1 @ 1,3: 4x4", "#2 @ 3,1: 4x4", "#3 @ 5,5: 0x0")),
            row(0, listOf("#1 @ 1,3: 2x2", "#2 @ 3,1: 2x2", "#3 @ 5,5: 0x0"))
        ) { result, input ->
            Day3.star1Calc(input) shouldBe result
        }
    }
    "Day 3, Star 2" {
        forall(
            row(3, listOf("#1 @ 1,3: 4x4", "#2 @ 3,1: 4x4", "#3 @ 5,5: 2x2")),
            row(4, listOf("#1 @ 1,3: 4x4", "#2 @ 3,1: 4x4", "#3 @ 5,5: 0x0", "#4 @ 6,6: 1x1")),
            row(1, listOf("#1 @ 1,3: 2x2", "#2 @ 3,1: 2x2", "#3 @ 4,2: 1x1"))
        ) { result, input ->
            Day3.star2Calc(input) shouldBe result
        }
    }
})
