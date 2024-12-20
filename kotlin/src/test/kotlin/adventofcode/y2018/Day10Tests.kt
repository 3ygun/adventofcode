package adventofcode.y2018

import io.kotest.core.spec.style.StringSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class Day10Tests : StringSpec({
    "Day 10, Star 1" {
        forAll(
            row(0, DAY_10_STAR_1_TEST_A, DAY_10_STAR_1_TEST_A_INITIAL),
            row(1, DAY_10_STAR_1_TEST_A, DAY_10_STAR_1_TEST_A_ITTER_1),
            row(3, DAY_10_STAR_1_TEST_A, DAY_10_STAR_1_TEST_A_RESULT),
            row(10519, Day10.STAR1, DAY_10_STAR_1_RESULT)
        ) { itter: Int, inputs: List<String>, results: String ->
            Day10.star1Calc(itter, inputs) shouldBe results
        }
    }
})

private val DAY_10_STAR_1_TEST_A = """position=< 9,  1> velocity=< 0,  2>
position=< 7,  0> velocity=<-1,  0>
position=< 3, -2> velocity=<-1,  1>
position=< 6, 10> velocity=<-2, -1>
position=< 2, -4> velocity=< 2,  2>
position=<-6, 10> velocity=< 2, -2>
position=< 1,  8> velocity=< 1, -1>
position=< 1,  7> velocity=< 1,  0>
position=<-3, 11> velocity=< 1, -2>
position=< 7,  6> velocity=<-1, -1>
position=<-2,  3> velocity=< 1,  0>
position=<-4,  3> velocity=< 2,  0>
position=<10, -3> velocity=<-1,  1>
position=< 5, 11> velocity=< 1, -2>
position=< 4,  7> velocity=< 0, -1>
position=< 8, -2> velocity=< 0,  1>
position=<15,  0> velocity=<-2,  0>
position=< 1,  6> velocity=< 1,  0>
position=< 8,  9> velocity=< 0, -1>
position=< 3,  3> velocity=<-1,  1>
position=< 0,  5> velocity=< 0, -1>
position=<-2,  2> velocity=< 2,  0>
position=< 5, -2> velocity=< 1,  2>
position=< 1,  4> velocity=< 2,  1>
position=<-2,  7> velocity=< 2, -2>
position=< 3,  6> velocity=<-1, -1>
position=< 5,  0> velocity=< 1,  0>
position=<-6,  0> velocity=< 2,  0>
position=< 5,  9> velocity=< 1, -2>
position=<14,  7> velocity=<-2,  0>
position=<-3,  6> velocity=< 2, -1>""".lines()

private const val DAY_10_STAR_1_TEST_A_INITIAL = """........#.............
................#.....
.........#.#..#.......
......................
#..........#.#.......#
...............#......
....#.................
..#.#....#............
.......#..............
......#...............
...#...#.#...#........
....#..#..#.........#.
.......#..............
...........#..#.......
#...........#.........
...#.......#.........."""

private const val DAY_10_STAR_1_TEST_A_ITTER_1 = """........#....#....
......#.....#.....
#.........#......#
..................
....#.............
..##.........#....
....#.#...........
...##.##..#.......
......#.#.........
......#...#.....#.
#...........#.....
..#.....#.#......."""

private const val DAY_10_STAR_1_TEST_A_RESULT = """#...#..###
#...#...#.
#...#...#.
#####...#.
#...#...#.
#...#...#.
#...#...#.
#...#..###"""

private const val DAY_10_STAR_1_RESULT = """#####...#.......#####...#####....####...######..#####...#####.
#....#..#.......#....#..#....#..#....#..#.......#....#..#....#
#....#..#.......#....#..#....#..#.......#.......#....#..#....#
#....#..#.......#....#..#....#..#.......#.......#....#..#....#
#####...#.......#####...#####...#.......#####...#####...#####.
#.......#.......#....#..#.......#..###..#.......#..#....#..#..
#.......#.......#....#..#.......#....#..#.......#...#...#...#.
#.......#.......#....#..#.......#....#..#.......#...#...#...#.
#.......#.......#....#..#.......#...##..#.......#....#..#....#
#.......######..#####...#........###.#..#.......#....#..#....#"""
