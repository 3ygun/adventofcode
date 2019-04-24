package adventofcode.y2018

import io.kotlintest.data.forall
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

class Day9Tests : StringSpec({
    "Day 9, Star 1" {
        forall(
            row(32L, "9 players; last marble is worth 23 points"),
            row(32L, "9 players; last marble is worth 25 points"),
            row(8317L, "10 players; last marble is worth 1618 points"),
            row(146373L, "13 players; last marble is worth 7999 points"),
            row(2764L, "17 players; last marble is worth 1104 points"),
            row(54718L, "21 players; last marble is worth 6111 points"),
            row(37305L, "30 players; last marble is worth 5807 points"),
            row(382055L, Day9.STAR1_DATA)
        ) { result, input ->
            Day9.star1Calc(input) shouldBe result
        }
    }
})
