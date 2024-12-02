package adventofcode.y2019

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FreeSpec
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class Day4Tests : FreeSpec({
    "Day 4, Star 1" - {
        "Bounds check" {
            shouldThrow<IllegalArgumentException> {
                Day4.star1Calc(100, 0)
            }
        }
        "Single Digits and 10 don't work" {
            for (from in 0..10) {
                for (to in from..10) {
                    Day4.star1Calc(from, to) shouldBe 0
                }
            }
        }

        listOf(
            row("11", 11, 11, 1),
            row("22", 22, 22, 1),
            row("333", 333, 333, 1),
            row("1299", 1299, 1299, 1),
            row("11-99", 11, 99, 9),
            row("100-112", 100, 112, 2), // 111, 112

            row("Star 1", Day4.STAR1.first, Day4.STAR1.second, 1019)
        ).map { (description, from, to, expected) ->
            description {
                Day4.star1Calc(from, to) shouldBe expected
            }
        }
    }
    "Day 4, Star 2" - {
        "Bounds check" {
            shouldThrow<IllegalArgumentException> {
                Day4.star2Calc(100, 0)
            }
        }
        "Single Digits and 10 don't work" {
            for (from in 0..10) {
                for (to in from..10) {
                    Day4.star2Calc(from, to) shouldBe 0
                }
            }
        }

        listOf(
            row("11", 11, 11, 1),
            row("22", 22, 22, 1),
            row("1299", 1299, 1299, 1),
            row("11-99", 11, 99, 9),

            // Star 2 cases
            row("333", 333, 333, 0),
            row("100-112", 100, 112, 1), // 111
            row("111222", 111222, 111222, 0),
            row("111226", 111226, 111226, 1),

            row("Star 2", Day4.STAR2.first, Day4.STAR2.second, 660)
        ).map { (description, from, to, expected) ->
            description {
                Day4.star2Calc(from, to) shouldBe expected
            }
        }
    }
})
