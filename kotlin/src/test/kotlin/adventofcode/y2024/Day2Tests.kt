package adventofcode.y2024

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class Day2Tests : DescribeSpec({
    describe("Day 2, Star 1") {
        it("Base version") {
            val records = """
                7 6 4 2 1
                1 2 7 8 9
                9 7 6 2 1
                1 3 2 4 5
                8 6 4 4 1
                1 3 6 7 9
            """.trimIndent().lines()
            Day2.star1(records) shouldBe 2L
        }

        it("Puzzle Input") {
            Day2.star1Run() shouldBe "Safe Levels: 510"
        }
    }
    describe("Day 2, Star 2") {
        it("Base version") {
            val records = """
                7 6 4 2 1
                1 2 7 8 9
                9 7 6 2 1
                1 3 2 4 5
                8 6 4 4 1
                1 3 6 7 9
            """.trimIndent().lines()
            Day2.star2(records) shouldBe 4L
        }

        it("Skip starter") {
            val records = """
                1 5 4 3 2 1
            """.trimIndent().lines()
            Day2.star2(records) shouldBe 1L
        }

        it("Skip ender") {
            val records = """
                5 4 3 2 1 5
            """.trimIndent().lines()
            Day2.star2(records) shouldBe 1L
        }

        it("Puzzle Input") {
            Day2.star2Run() shouldBe "Safe Levels with Problem Dampener: 553"
        }
    }
})
