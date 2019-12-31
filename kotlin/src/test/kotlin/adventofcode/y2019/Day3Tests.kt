package adventofcode.y2019

import io.kotlintest.shouldBe
import io.kotlintest.specs.FreeSpec
import io.kotlintest.tables.row

class Day3Tests : FreeSpec({
    "Day 3, Star 1" - {
        listOf(
            row("Example 1", "R8,U5,L5,D3", "U7,R6,D4,L4", 6),
            row("Example 2", "R75,D30,R83,U83,L12,D49,R71,U7,L72", "U62,R66,U55,R34,D71,R55,D58,R83", 159),
            row("Example 3", "R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51", "U98,R91,D20,R16,D67,R40,U7,R15,U6,R7", 135),
            row("Star 1", Day3.STAR1.first, Day3.STAR1.second, 896)
        ).map { (description, wire1, wire2, distance) ->
            description {
                Day3.star1Calc(wire1, wire2) shouldBe distance
            }
        }
    }
    "Day 3, Star 2" - {
        listOf(
            row("Example 1", "R8,U5,L5,D3", "U7,R6,D4,L4", 30),
            row("Example 2", "R75,D30,R83,U83,L12,D49,R71,U7,L72", "U62,R66,U55,R34,D71,R55,D58,R83", 610),
            row("Example 3", "R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51", "U98,R91,D20,R16,D67,R40,U7,R15,U6,R7", 410),
            row("Star 2", Day3.STAR2.first, Day3.STAR2.second, 16524)
        ).map { (description, wire1, wire2, distance) ->
            description {
                Day3.star2Calc(wire1, wire2) shouldBe distance
            }
        }
    }
})
