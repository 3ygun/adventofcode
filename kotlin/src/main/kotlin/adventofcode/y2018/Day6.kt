package adventofcode.y2018

import adventofcode.DataLoader
import adventofcode.Day

object Day6 : Day {
    val STAR1_DATA = DataLoader.readLinesFromFor("/y2018/Day6Star1.txt")
    val STAR2_DATA = STAR1_DATA

    override val day: Int = 6

    override fun star1Run(): String {
        val result = star1Calc(STAR1_DATA)
        return "largest non infinite area is $result"
    }

    override fun star2Run(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun star1Calc(input: List<String>): Int {
        if (input.isEmpty()) return 0

        val points = parsePoints(input)
        val grid = Grid(points)
        grid.populate()
//        grid.print()

        return grid.largestArea()
    }

    private fun parsePoints(input: List<String>): List<Point> {
        val nextName = (0 until input.size).iterator()
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

private data class Point(val x: Int, val y: Int, val name: Int)

private data class GridPoint(val name: Int, val distance: Int) {
    fun isContended() = name == -1

    fun distanceCompareTo(distance: Int): Int {
        return this.distance.compareTo(distance)
    }

    companion object {
        fun Flag(distance: Int): GridPoint = GridPoint(-1, distance)
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

    fun print() = grid.forEach { row ->
        row.forEach { point -> print("%3d".format(point?.name ?: -2)) }
        println()
    }

    fun printDistance() = grid.forEach { row ->
        row.forEach { point -> print("%3d".format(point?.distance ?: -1)) }
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

        val name = point.name
        // Initial Point
        claim(point.x, point.y, name, distance = 0)

        // Top Left Corner
        claimAllFor(
            point = point,
            xRange = point.x downTo 0,
            yRange = point.y downTo 0
        ) { x, y -> (point.x - x) + (point.y - y) }

        // Top Right Corner
        claimAllFor(
            point = point,
            xRange = point.x until width,
            yRange = point.y downTo 0
        ) { x, y -> (x - point.x) + (point.y - y) }

        // Bottom Left Corner
        claimAllFor(
            point = point,
            xRange = point.x downTo 0,
            yRange = point.y until height
        ) { x, y -> (point.x - x) + (y - point.y) }

        // Bottom Right Corner
        claimAllFor(
            point = point,
            xRange = point.x until width,
            yRange = point.y until height
        ) { x, y -> (x - point.x) + (y - point.y) }
    }
}

private inline fun Grid.claimAllFor(
    point: Point,
    xRange: IntProgression,
    yRange: IntProgression,
    distanceCalc: (Int, Int) -> Int // Passing x, y
) {
    for (x in xRange) {
        for (y in yRange) {
            if (x == point.x && y == point.y) continue

            val madeClaim = claim(x, y, point.name, distance = distanceCalc(x, y))
            if (madeClaim) continue

            if (y == point.y) return
            break // otherwise we hit the a overflow on y
        }
    }
}

private fun Grid.largestArea(): Int {
    val counts = IntArray(points.size)

    for (x in 0 until width) {
        for (y in 0 until height) {
            val point = at(x, y)
            if (point != null && !point.isContended()) {
                counts[point.name] = counts[point.name] + 1
            }
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
