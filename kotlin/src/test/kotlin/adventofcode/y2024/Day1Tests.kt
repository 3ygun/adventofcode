package adventofcode.y2024

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class Day1Tests : DescribeSpec({
    describe("Day 1, Star 1") {
        it("Base version") {
            val input = """
                3   4
                4   3
                2   5
                1   3
                3   9
                3   3
            """.trimIndent()
            Day1.star1(input.lines()) shouldBe 11
        }

        it("Puzzle Inputs") {
            Day1.star1Run() shouldBe "Difference in the lists: 3569916"
        }
    }
    describe("Day 2, Star 2") {
        it("Base version") {
            val input = """
                3   4
                4   3
                2   5
                1   3
                3   9
                3   3
            """.trimIndent()
            Day1.star2(input.lines()) shouldBe 31
        }

        it("Puzzel Inputs") {
            Day1.star2Run() shouldBe "Similarity score: 26407426"
        }
    }
})
