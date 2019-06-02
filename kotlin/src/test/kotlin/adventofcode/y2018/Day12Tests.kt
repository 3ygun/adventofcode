package adventofcode.y2018

import io.kotlintest.data.forall
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

class Day12Tests : StringSpec({
    "Day 12, Star 1 & 2" {
        forall(
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
