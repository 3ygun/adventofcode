package adventofcode.y2018

import io.kotlintest.data.forall
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

class StringSpecExample : StringSpec({
     "maximum of two numbers" {
          forall(
              row("+1", "+1", "+1", 3),
              row("+1", "+1", "-2", 0),
              row("-1", "-2", "-3", -6),
              row("", "-2", "", -2)
          ) { a, b, c, frequency ->
              Day1.star1Calc(listOf(a, b,c)) shouldBe frequency
          }
     }
 })
