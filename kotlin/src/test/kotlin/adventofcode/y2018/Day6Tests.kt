package adventofcode.y2018

import io.kotlintest.data.forall
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

class Day6Tests : StringSpec({
    "Day 6, Star 1" {
        forall(
            row(17, Day6Star1Data.lines()),
            row(1, star1MinimumArea.lines()),
            row(4171, Day6.STAR1_DATA) // Solution
        ) { result, input ->
            Day6.star1Calc(input) shouldBe result
        }
    }
})

private const val Day6Star1Data = """1, 1
1, 6
8, 3
3, 4
5, 5
8, 9"""

private const val star1MinimumArea = """1, 1
1, 3
3, 1
3, 3
2, 2"""
