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
            row(Day11.Day11Star1Result(33, 45, 29), 18),
            row(Day11.Day11Star1Result(21, 61, 30), 42),
            row(Day11.Day11Star1Result(235, 31, 31), Day11.STAR1)
        ) { result: Day11.Day11Star1Result, gridSerialNumber: Int ->
            Day11.star1Calc(gridSerialNumber) shouldBe result
        }
    }
    "Day 11, Star 2" {
        forall(
            row(Day11.Day11Star2Result(90, 269, 16, 113), 18),
            row(Day11.Day11Star2Result(232, 251, 12, 119), 42),
            row(Day11.Day11Star2Result(241, 65, 10, 73), Day11.STAR2)
        ) { result: Day11.Day11Star2Result, gridSerialNamber: Int ->
            Day11.star2Calc(gridSerialNamber) shouldBe result
        }
    }
})
