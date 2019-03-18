package adventofcode.y2018

import io.kotlintest.data.forall
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

class Day7Tests : StringSpec({
    "Day 7, Star 1" {
        forall(
            row("CABDFE", Day7Star1Data.lines()),
            row("ABLCFNSXZPRHVEGUYKDIMQTWJO", Day7.STAR1_DATA)
        ) { result, input ->
            Day7.star1Calc(input) shouldBe result
        }
    }
    "Day 7, Star 2" {
        forall(
            row(15 to "CABFDE", 0, 2, Day7Star1Data.lines())
        ) { result, scale, workers, input ->
            Day7.star2Calc(scale, workers, input) shouldBe result
        }
    }
})

private const val Day7Star1Data = """Step C must be finished before step A can begin.
Step C must be finished before step F can begin.
Step A must be finished before step B can begin.
Step A must be finished before step D can begin.
Step B must be finished before step E can begin.
Step D must be finished before step E can begin.
Step F must be finished before step E can begin."""
