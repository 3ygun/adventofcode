package adventofcode.y2024

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.core.spec.style.describeSpec
import io.kotest.matchers.shouldBe

class Day3Tests : DescribeSpec({
    describe("Day 3, Star 1") {
        it("Base Version") {
            val input = """xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))""".lines()
            Day3.star1(input) shouldBe 161L
        }

        it("Puzzle Inputs") {
            Day3.star1Run() shouldBe "Result: 179834255"
        }
    }
    describe("Day 3, Star 2") {
        it("Base Version") {
            val input = """xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))""".lines()
            Day3.star2(input) shouldBe 161L
        }

        it("Base Version Star 2") {
            val input = """xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))""".lines()
            Day3.star2(input) shouldBe 48
        }

        it("Puzzle Inputs") {
            Day3.star2Run() shouldBe "Result: 80570939"
        }
    }
})
