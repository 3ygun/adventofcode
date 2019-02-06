package adventofcode.y2018

import io.kotlintest.data.forall
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

class Day2Tests : StringSpec({
    "Day 2, Star 1" {
        forall(
            row(12, listOf("abcdef", "bababc", "abbcde", "abcccd", "aabcdd", "abcdee", "ababab")),
            row(6944, Day2.STAR1_DATA)
        ) { result, input ->
            Day2.star1Calc(input) shouldBe result
        }
    }
    "Day 2, Star 2" {
        forall(
            row("fgij", listOf("abcde", "fghij", "klmno", "pqrst", "fguij", "axcye", "wvxyz")),
            row("abcdd", listOf("aabcdd", "acbcda", "zabcdd")),
            row("srijafjzloguvlntqmphenbkd", Day2.STAR2_DATA)
        ) { result, input ->
            Day2.star2Calc(input) shouldBe result
        }
    }
})
