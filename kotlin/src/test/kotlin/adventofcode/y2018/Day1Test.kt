package adventofcode.y2018

import io.kotest.core.spec.style.StringSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class Day1Test : StringSpec({
     "Day 1, Star 1" {
          forAll(
              row(3, listOf("+1", "+1", "+1")),
              row(0, listOf("+1", "+1", "-2")),
              row(-6, listOf("-1", "-2", "-3")),
              row(-2, listOf("", "-2", "")),
              row(493, Day1.STAR1_DATA)
          ) { result, input ->
              Day1.star1Calc(input) shouldBe result
          }
     }

    "Day 1, Star 2" {
        forAll(
            row(0, listOf("+1", "-1")),
            row(10, listOf("+3", "+3", "+4", "-2", "-4")),
            row(5, listOf("-6", "+3", "+8", "+5", "-6")),
            row(14, listOf("+7", "+7", "-2", "-7", "-4")),
            row(413, Day1.STAR2_DATA)
        ) { result, input ->
            Day1.star2Calc(input) shouldBe result
        }
    }
 })
