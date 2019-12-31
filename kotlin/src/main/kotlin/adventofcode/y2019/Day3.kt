package adventofcode.y2019

import adventofcode.DataLoader
import adventofcode.Day
import adventofcode.commons.Point
import adventofcode.commons.xy
import kotlin.math.abs

/**
 * Final solution holds all the points for each wire in memory.
 * While this isn't super memory efficient it is sure easier to build a solution off.
 *
 * Other thoughts:
 * You could probably build a solution that determines if 2 points overlap and then only hold
 * the points of each wire turn in memory. Then calculate values from there. I tried building such
 * a solution for a bit but it wasn't clicking. Therefore I went with the following more strait
 * forward solution for now.
 */
object Day3 : Day {
    internal val STAR1 = DataLoader.readNonBlankLinesFrom("/y2019/Day3Star1.txt")
        .apply { require(this.size == 2) { "Too many wires in 2019, Day 3 Star 1" } }
        .let { it[0] to it[1] }
    internal val STAR2 = STAR1

    override val day = 3
    override val debug = false

    // <editor-fold desc="Star 1">

    override fun star1Run(): String {
        val result = star1Calc(STAR2.first, STAR2.second)
        return "The closest wire intersection to the starting point is: $result distance away"
    }

    internal fun star1Calc(
        rawWire1: String,
        rawWire2: String
    ): Int {
        val wire1 = star1WireOf(rawWire1)
        val wire2 = star1WireOf(rawWire2)

        val intersections = star1IntersectionsOf(wire1, wire2)
            .filterNot { it.x == 0 && it.y == 0 }
        debug { "Intersections size=${intersections.size} and $intersections" }

        val distanceAndPoint = intersections
            .map { point ->
                val distance = abs(point.x) + abs(point.y)
                distance to point
            }
            .minBy { it.first }!!

        return distanceAndPoint.first
    }

    private class Star1Wire(
        val points: Set<Point>
    )

    private fun star1WireOf(rawWire: String): Star1Wire {
        val movements = rawWire.split(",")

        var x = 0
        var y = 0
        val points = mutableSetOf(x xy y)
        for (movement in movements) {
            val direction = Direction.of(movement[0])
            val amount = movement.substring(1).toInt()

            when {
                direction.xAxis -> for (i in 1..amount) {
                    x += direction.amountEncoding
                    points.add(x xy y)
                }
                else -> for (i in 1..amount) {
                    y += direction.amountEncoding
                    points.add(x xy y)
                }
            }
        }

        return Star1Wire(points)
    }

    /** Note: This only deals with segments that are NOT diagonal */
    private fun star1IntersectionsOf(wire1: Star1Wire, wire2: Star1Wire): Set<Point> {
        val intersections = mutableSetOf<Point>()

        wire1.points.forEach { w1Point ->
            if (w1Point in wire2.points) intersections.add(w1Point)
        }

        return intersections
    }

    // </editor-fold>
    // <editor-fold desc="Star 2">

    override fun star2Run(): String {
        val result = star2Calc(STAR2.first, STAR2.second)
        return "The intersection with the fewest combined wire steps occurred: $result steps away"
    }

    internal fun star2Calc(
        rawWire1: String,
        rawWire2: String
    ): Int {
        val wire1 = star2WireOf(rawWire1)
        val wire2 = star2WireOf(rawWire2)

        // Rotating to iterate through the smallest wire didn't seem to do anything
        return star2DistanceToClosestIntersection(wire1, wire2)
    }

    private fun star2DistanceToClosestIntersection(
        wire1: Star2Wire,
        wire2: Star2Wire
    ): Int {
        var distanceToClosestIntersection: Int = Int.MAX_VALUE
        w1@ for ((w1Point, w1d) in wire1.pointToDistance) {
            // Did we already pass the shortest distance without finding on the other wire?
            if (w1d > distanceToClosestIntersection) break@w1

            // Look for an intersection with the other wire
            val w2d: Int = wire2.pointToDistance[w1Point]
                ?: continue@w1 // Not an intersection..next!

            // Intersection found how long did it take us to get here?
            val distance = w1d + w2d
            if (distance < distanceToClosestIntersection) {
                distanceToClosestIntersection = distance
            }
        }
        return distanceToClosestIntersection
    }

    private class Star2Wire(
        val pointToDistance: Map<Point, Int>
    )

    private fun star2WireOf(rawWire: String): Star2Wire {
        val movements = rawWire.split(",")

        var x = 0
        var y = 0
        var d = 0
        val pointToDistance = mutableMapOf<Point, Int>()
        for (movement in movements) {
            val direction = Direction.of(movement[0])
            val amount = movement.substring(1).toInt()

            when {
                direction.xAxis -> for (i in 1..amount) {
                    x += direction.amountEncoding
                    d++
                    pointToDistance.merge(x xy y, d) { old, new ->
                        if (old >= new) old else new // Only keep the shortest distance to the point
                    }
                }
                else -> for (i in 1..amount) {
                    y += direction.amountEncoding
                    d++
                    pointToDistance.merge(x xy y, d) { old, new ->
                        if (old >= new) old else new // Only keep the shortest distance to the point
                    }
                }
            }
        }

        // Don't have the starting point it messes with stuff
        // Do down here incase it got added again
        pointToDistance.remove(0 xy 0)
        return Star2Wire(pointToDistance)
    }

    // </editor-fold>
    // <editor-fold desc="Common">

    private enum class Direction(
        val direction: Char,
        val amountEncoding: Int,
        val xAxis: Boolean
    ) {
        Right('R', 1, true),
        Left('L', -1, true),
        Up('U', 1, false), // top left be 0, 0
        Down('D', -1, false), // top left be 0, 0
        ;

        companion object {
            fun of(rawDirection: Char): Direction {
                return values().firstOrNull { it.direction == rawDirection }
                    ?: throw IllegalArgumentException("Unknown direction: $rawDirection")
            }
        }
    }

    // </editor-fold>
}
