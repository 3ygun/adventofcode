package adventofcode.y2019

import io.kotlintest.shouldBe
import io.kotlintest.specs.FreeSpec
import io.kotlintest.tables.row

class Day1Tests : FreeSpec({
    "Day 1, Star 1" - {
        listOf(
            row(1969L, 654L, "Given case 1"),
            row(100756L, 33583L, "Given case 2")
        ).map { (mass, fuel, descripton) ->
            "$descripton for mass $mass to fuel $fuel" {
                Day1.star1Calc(mass) shouldBe fuel
            }
        }
    }
})
