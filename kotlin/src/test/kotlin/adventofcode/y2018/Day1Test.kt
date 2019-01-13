package adventofcode.y2018

import io.kotlintest.data.forall
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

class StringSpecExample : StringSpec({
     "Day 1, Star 1" {
          forall(
              row("+1", "+1", "+1", 3),
              row("+1", "+1", "-2", 0),
              row("-1", "-2", "-3", -6),
              row("", "-2", "", -2)
          ) { a, b, c, frequency ->
              Day1.star1Calc(listOf(a, b,c)) shouldBe frequency
          }
     }

    "Day 1, Star 2" {
        forall(
            row(0, listOf("+1", "-1")),
            row(10, listOf("+3", "+3", "+4", "-2", "-4")),
            row(5, listOf("-6", "+3", "+8", "+5", "-6")),
            row(14, listOf("+7", "+7", "-2", "-7", "-4"))
        ) { result, input ->
            Day1.star2Calc(input) shouldBe result
        }
    }
 })
