package adventofcode.y2018

import io.kotest.core.spec.style.FunSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class Day11Tests : FunSpec({
    coroutineTestScope = false
    test("Day 11, Star 1, Point") {
        forAll(
            row(-5, 57, 122, 79),
            row(0, 39, 217, 196),
            row(4, 71, 101, 153)
        ) { powerLevel: Int, gridSerialNumber: Int, x: Int, y: Int ->
            Day11.star1PointCalc(x, y, gridSerialNumber) shouldBe powerLevel
        }
    }
    test("Day 11, Star 1") {
        forAll(
            row(Day11.Day11Result(33, 45, 3, 29), 18),
            row(Day11.Day11Result(21, 61, 3, 30), 42),
            row(Day11.Day11Result(235, 31,3,  31), Day11.STAR1)
        ) { result: Day11.Day11Result, gridSerialNumber: Int ->
            Day11.star1Calc(gridSerialNumber) shouldBe result
        }
    }
    test("Day 11, Star 2") {
        forAll(
            row(Day11.Day11Result(90, 269, 16, 113), 18),
            row(Day11.Day11Result(232, 251, 12, 119), 42),
            row(Day11.Day11Result(241, 65, 10, 73), Day11.STAR2)
        ) { result: Day11.Day11Result, gridSerialNamber: Int ->
            Day11.star2Calc(gridSerialNamber) shouldBe result
        }
    }
})
