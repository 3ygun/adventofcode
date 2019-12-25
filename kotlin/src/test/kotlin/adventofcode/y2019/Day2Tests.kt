package adventofcode.y2019

import io.kotlintest.shouldBe
import io.kotlintest.specs.FreeSpec
import io.kotlintest.tables.row

class Day2Tests : FreeSpec({
    "Day 2, Star 1" - {
        listOf(
            row("Example 1", "1,0,0,0,99", 2),
            row("Example 2", "2,3,0,3,99", 2),
            row("Example 3", "2,4,4,5,99,0", 2),
            row("Example 4", "1,1,1,4,99,5,6,0,99", 30),
            row("Star 1", Day2.STAR1, 3267740)
        ).map { (description, input, expected) ->
            description {
                Day2.star1Calc(input) shouldBe expected
            }
        }
    }
    "Day 2, Star 2" - {
        listOf(
            row("Star 1 noun 12, verb 2", Day2.STAR1, 3267740, 1202), // noun=12, verb=2
            row("Star 2 noun ?, verb ?", Day2.STAR2, 19690720, 7870) // ends up being noun=78, verb=70
        ).map { (description, input, outputValue, expected) ->
            description {
                Day2.star2Calc(input, outputValue) shouldBe expected
            }
        }
    }
})
