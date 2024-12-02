package adventofcode.y2018

import io.kotest.core.spec.style.StringSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class Day12Tests : StringSpec({
    "Day 12, Star 1 & 2" {
        forAll(
            row(325L, 0L, DAY12_STAR1_TEST_A, -3),
            row(325L, 20L, DAY12_STAR1_TEST_B, 0),
            row(3890L, 20L, Day12.STAR1, 0), // The answer to Star 1
            row(4800000001087L, 50000000000L, Day12.STAR2, 0) // The answer to Star 2
        ) { result: Long, generations: Long, input: List<String>, startIndex: Int ->
            Day12.star1Calc(generations, input, startIndex) shouldBe result
        }
    }
})

private val DAY12_STAR1_TEST_A = """initial state: .#....##....#####...#######....#.#..##.

...## => #
..#.. => #
.#... => #
.#.#. => #
.#.## => #
.##.. => #
.#### => #
#.#.# => #
#.### => #
##.#. => #
##.## => #
###.. => #
###.# => #
####. => #
""".lines()

private val DAY12_STAR1_TEST_B = """initial state: #..#.#..##......###...###

...## => #
..#.. => #
.#... => #
.#.#. => #
.#.## => #
.##.. => #
.#### => #
#.#.# => #
#.### => #
##.#. => #
##.## => #
###.. => #
###.# => #
####. => #
""".lines()
