package adventofcode.y2019

import io.kotest.core.spec.style.FreeSpec
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class Day1Tests : FreeSpec({
    "Day 1, Star 1" - {
        listOf(
            row(listOf(1969L), 654L, "Given case 1"),
            row(listOf(100756L), 33583L, "Given case 2"),
            row(Day1.STAR1, 3429947L, "Problem case")
        ).map { (masses, fuel, description) ->
            "$description for ${masses.size} masses to fuel $fuel" {
                Day1.star1Calc(masses) shouldBe fuel
            }
        }
    }

    "Day 1, Star 2" - {
        listOf(
            row(listOf(14L), 2L, "Given case 1"),
            row(listOf(1969L), 966L, "Given case 2"),
            row(listOf(100756L), 50346L, "Given case 3"),
            row(Day1.STAR2, 5142043L, "Problem case")
        ).map { (masses, fuel, description) ->
            "$description for ${masses.size} masses to fuel $fuel" {
                Day1.star2Calc(masses) shouldBe fuel
            }
        }
    }
})
