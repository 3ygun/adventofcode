package adventofcode.y2018

import io.kotlintest.data.forall
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

class Day11Tests : StringSpec({
    "Day 11, Star 1, Point" {
        forall(
            row(-5, 57, 122, 79),
            row(0, 39, 217, 196),
            row(4, 71, 101, 153)
        ) { powerLevel: Int, gridSerialNumber: Int, x: Int, y: Int ->
            Day11.star1PointCalc(x, y, gridSerialNumber) shouldBe powerLevel
        }
    }
    "Day 11, Star 1" {
        forall(
            row(Day11.Day11Result(33, 45, 3, 29), 18),
            row(Day11.Day11Result(21, 61, 3, 30), 42),
            row(Day11.Day11Result(235, 31,3,  31), Day11.STAR1)
        ) { result: Day11.Day11Result, gridSerialNumber: Int ->
            Day11.star1Calc(gridSerialNumber) shouldBe result
        }
    }
    "Day 11, Star 2" {
        forall(
            row(Day11.Day11Result(90, 269, 16, 113), 18),
            row(Day11.Day11Result(232, 251, 12, 119), 42),
            row(Day11.Day11Result(241, 65, 10, 73), Day11.STAR2)
        ) { result: Day11.Day11Result, gridSerialNamber: Int ->
            Day11.star2Calc(gridSerialNamber) shouldBe result
        }
    }
})
