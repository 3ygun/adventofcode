package adventofcode.y2018

import adventofcode.DataLoader
import adventofcode.Day
import kotlin.math.abs

object Day6 : Day {
    val STAR1_DATA = DataLoader.readLinesFromFor("/y2018/Day6Star1.txt")
    val STAR2_DATA = STAR1_DATA
    const val STAR2_MAX_DISTANCE = 10_000

    override val day: Int = 6

    override fun star1Run(): String {
        val result = star1Calc(STAR1_DATA)
        return "largest non infinite area is $result"
    }

    override fun star2Run(): String {
        val result = star2Calc(STAR2_DATA, STAR2_MAX_DISTANCE)
        return "area of region with max 1000 distance to all coordinates is $result"
    }

    fun star1Calc(input: List<String>): Int {
        if (input.isEmpty()) return 0

        val points = parsePoints(input)
        val grid = Grid(points)
        grid.populate()
//        grid.print()

        return grid.largestArea()
    }

    fun star2Calc(input: List<String>, maxDistance: Int): Int {
        if (input.isEmpty()) return 0

        val points = parsePoints(input)
        val grid = Grid(points)
        grid.populateAllPointsWithinDistance(maxDistance)
//        grid.print()
//        grid.printDistance()

        return grid.flagged()
    }

    private fun parsePoints(input: List<String>): List<Point> {
        val nextName = (1 .. input.size).iterator()
        return input.map { rawPoint ->
            val points = rawPoint.split(",", limit = 2)
            Point(
                x = points[0].trim().toInt(),
                y = points[1].trim().toInt(),
                name = nextName.nextInt()
            )
        }
    }
}

private typealias GridArray = Array<Array<GridPoint?>>

private data class Point(val x: Int, val y: Int, val name: Int) {
    fun distanceTo(x: Int, y: Int): Int = abs(this.x - x) + abs(this.y - y)
}

private data class GridPoint(val name: Int, val distance: Int) {
    fun isContended() = name == NAME_FLAG

    fun distanceCompareTo(distance: Int): Int {
        return this.distance.compareTo(distance)
    }

    companion object {
        const val NAME_FLAG = -1

        fun Flag(distance: Int): GridPoint = GridPoint(NAME_FLAG, distance)
    }
}

private data class Grid(
    val points: List<Point>
) {
    val width: Int = points.maxBy { it.x }!!.x + 1 // + 1 to account for the 0 start
    val height: Int = points.maxBy { it.y }!!.y + 1 // + 1 to account for the 0 start

    // Height, Width because the top left corner is 0, 0
    private val grid: GridArray = Array(height) { Array<GridPoint?>(width) { null } }

    fun at(x: Int, y: Int): GridPoint? = grid[y][x]

    fun claim(x: Int, y: Int, name: Int, distance: Int): Boolean {
        val existing = at(x, y)
            ?: return claimLocation(x, y, GridPoint(name, distance))

        // we don't have to care about the distance in our algorithm
        if (existing.name == name) return true

        return when(existing.distanceCompareTo(distance)) {
            1 -> claimLocation(x, y, GridPoint(name, distance))
            0 -> claimLocation(x, y, GridPoint.Flag(distance))
            else -> false
        }
    }

    private fun claimLocation(x: Int, y: Int, gridPoint: GridPoint): Boolean {
        grid[y][x] = gridPoint
        return true // Claimed
    }

    inline fun forEachPoint(run: (GridPoint?) -> Unit) {
        for (y in 0 until height) {
            for (x in 0 until width) {
                run(at(x, y))
            }
        }
    }

    fun print() = forEachPrinting { it?.name ?: 0 }
    fun printDistance() = forEachPrinting { it?.distance ?: 0 }

    private inline fun forEachPrinting(run: (GridPoint?) -> Int) {
        for (y in 0 until height) {
            for (x in 0 until width) {
                print("%3d".format(run(at(x, y))))
            }
            println()
        }
        println()
    }
}

//<editor-fold desc="Star 1">

private fun Grid.populate() {
    for (point in points) {
//            println("${point.name} with ${point.x}, ${point.y}")
//            print()
//            println()
//
//            printDistance()
//            println()

        // Initial Point
        claim(point.x, point.y, point.name, distance = 0)

        // Top Left Corner
        claimAllFor(
            point = point,
            xRange = point.x downTo 0,
            yRange = point.y downTo 0
        )

        // Top Right Corner
        claimAllFor(
            point = point,
            xRange = point.x until width,
            yRange = point.y downTo 0
        )

        // Bottom Left Corner
        claimAllFor(
            point = point,
            xRange = point.x downTo 0,
            yRange = point.y until height
        )

        // Bottom Right Corner
        claimAllFor(
            point = point,
            xRange = point.x until width,
            yRange = point.y until height
        )
    }
}

private fun Grid.claimAllFor(
    point: Point,
    xRange: IntProgression,
    yRange: IntProgression
) {
    for (x in xRange) {
        for (y in yRange) {
            if (x == point.x && y == point.y) continue

            val madeClaim = claim(x, y, point.name, distance = point.distanceTo(x, y))
            if (madeClaim) continue

            if (y == point.y) return
            break // otherwise we hit the a overflow on y
        }
    }
}

private fun Grid.largestArea(): Int {
    // This array is one larger than the original points because I'm saving 0 as "untouched"
    val counts = IntArray(points.size + 1)

    forEachPoint { point ->
        if (point != null && !point.isContended()) {
            counts[point.name] = counts[point.name] + 1
        }
    }

    val infinite = HashSet<Int>()
    (0 until width).forEach { x ->
        at(x, 0)?.run { infinite.add(this.name) }
        at(x, height-1)?.run { infinite.add(this.name) }
    }
    (0 until height).forEach { y ->
        at(0, y)?.run { infinite.add(this.name) }
        at(width-1, y)?.run { infinite.add(this.name) }
    }

    return counts
        .mapIndexed { index, count -> Pair(index, count) }
        .filter { !infinite.contains(it.first) }
        .maxBy { it.second }!!
        .second
}

//</editor-fold>

//<editor-fold desc="Star 2">

private fun Grid.populateAllPointsWithinDistance(maxDistance: Int) {
    for (x in 0 until width) {
        y@for (y in 0 until height) {
            var distance = 0
            for (point in points) {
                distance += point.distanceTo(x, y)
                if (distance >= maxDistance) continue@y // point too far
            }

            claim(x, y, GridPoint.NAME_FLAG, distance)
        }
    }
}

private fun Grid.flagged(): Int {
    var flagged = 0

    forEachPoint { point ->
        if (point != null && point.isContended()) flagged ++
    }

    return flagged
}

//</editor-fold>
