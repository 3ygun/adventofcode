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

    "Problem C - File Name" - {
        listOf(
            row("xxxiii", 1),
            row("xxoxx", 0),
            row("xxxxxxxxxx", 8)
        ).map { (input, expected) ->
            "Expected removal $expected from: $input" {
                KotlinHeroesPractice3.problemC(input) shouldBe expected
            }
        }
    }

    "Problem D - Bus Video System" - {
        listOf(
            row("3 5", "2 1 -3", 3L),
            row("2 4", "-1 1", 4L),
            row("4 10", "2 4 1 2", 2L),
            row("3 10", "-2 -2 -5", 2L),
            row("1 10", "10", 1L),
            row("2 10", "9 -10", 1L),
            row("3 10", "9 -5 -5", 1L),
            row("4 10", "-2 -2 -5 9", 2L),
            row("4 10", "9 -5 6 -10", 1L),
            row("4 12", "9 -5 3 -7", 4L),
            row("1 99", "-99", 1L),
            row("5 99", "0 0 0 0 0", 100L),
            row("2 99", "-55 -43", 2L),
            row("2 100", "-50 1", 51L),
            row("1 10", "-100", 0L),
            row("1 10", "100", 0L),
            row("4 10", "-1 -9 -7 10", 0L),
            row("5 10", "5 -1 -9 -7 10", 0L),
            row("3 10", "1 2", 0L)
        ).map { (bus, changes, expected) ->
            "Bus Inputs: '$bus', Changes: '$changes', Expecting: $expected" {
                KotlinHeroesPractice3.problemD(bus, changes) shouldBe expected
            }
        }
    }
})
