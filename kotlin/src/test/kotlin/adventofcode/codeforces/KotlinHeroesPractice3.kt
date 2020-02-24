package adventofcode.codeforces

import io.kotlintest.should
import io.kotlintest.shouldBe
import io.kotlintest.specs.FreeSpec
import io.kotlintest.tables.row

class KotlinHeroesPractice3Tests : FreeSpec({
    "Problem A - Restoring Three Numbers" - {
        listOf(
            row(row(3L, 6L, 5L, 4L), Triple(1L, 2L, 3L)),
            row(row(4L, 7L, 6L, 4L), Triple(1L, 3L, 3L)),
            row(row(40L, 40L, 40L, 60L), Triple(20L, 20L, 20L)),
            row(row(120L, 120L, 120L, 180L), Triple(60L, 60L, 60L)),
            row(row(201L, 101L, 101L, 200L), Triple(1L, 100L, 100L)),
            row(row(5L, 100L, 101L, 103L), Triple(2L, 3L, 98L))
        ).map { (input, output) ->
            "$input to $output" {
                val (x1, x2, x3, x4) = input
                KotlinHeroesPractice3.problemA(x1, x2, x3, x4) should { it == output.toList() }
            }
        }
    }

    "Problem B - Remove Duplicates" - {
        listOf(
            row("6", "1 5 5 1 6 1", "3", "5 6 1"),
            row("5", "2 4 2 4 4", "2", "2 4"),
            row("5", "6 6 6 6 6", "1", "6")
        ).map { (numInputs, input, numOutputs, output) ->
            "From: '$input' To: '$output'" {
                val (numResult, result) = KotlinHeroesPractice3.problemB(numInputs, input)
                numResult shouldBe numOutputs
                result shouldBe output
            }
        }
    }
})
