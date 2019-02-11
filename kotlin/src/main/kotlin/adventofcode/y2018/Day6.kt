package adventofcode.y2018

import adventofcode.DataLoader
import adventofcode.Day
import adventofcode.utils.maxValueAndIndex

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

        val nextName = (0 until input.size).iterator()
        val points = input.map { rawPoint ->
                val points = rawPoint.split(",", limit = 2)
                Point(
                    x = points[0].trim().toInt(),
                    y = points[1].trim().toInt(),
                    name = nextName.nextInt()
                )
            }

        val grid = Grid.of(points)
        grid.populate()
//        grid.print()

        return grid.largestArea()
    }
}

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

private class Grid private constructor(
    private val points: List<Point>,
    private val grid: GridArray,
    val width: Int,
    val height: Int
) {
    fun largestArea(): Int {
        val counts = IntArray(points.size)

        grid.forEach { row -> row.forEach { point ->
            if (!point!!.isContended()) {
                counts[point.name] = counts[point.name] + 1
            }
        } }

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

    fun print() {
        grid.forEach { row ->
            row.forEach { point -> print("%3d".format(point?.name ?: -2)) }
            println()
        }
    }

    fun printDistance() {
        grid.forEach { row ->
            row.forEach { point -> print("%3d".format(point?.distance ?: -1)) }
            println()
        }
    }

    fun populate() {
        for (point in points) {
//            println("${point.name} with ${point.x}, ${point.y}")
//            print()
//            println()
//
//            printDistance()
//            println()

            val name = point.name
            // Initial Point
            claimTerritory(point.x, point.y, name, distance = 0) ?: continue

            // Top Left Corner
            xTLC@for (x in point.x downTo 0) {
                for (y in point.y downTo 0) {
                    if (x == point.x && y == point.y) continue

                    val claim = claimTerritory(x, y, name, distance = ((point.x - x) + (point.y - y)))
                    if (claim != null) continue

                    if (y == point.y) break@xTLC
                    break // otherwise we hit the a overflow on y
                }
            }

            // Top Right Corner
            xTRC@for (x in point.x until width) {
                for (y in point.y downTo 0) {
                    if (x == point.x && y == point.y) continue

                    val claim = claimTerritory(x, y, name, distance = ((x - point.x) + (point.y - y)))
                    if (claim != null) continue

                    if (y == point.y) break@xTRC
                    break // otherwise we hit the a overflow on y
                }
            }

            // Bottom Left Corner
            xBLC@for (x in point.x downTo 0) {
                for (y in point.y until height) {
                    if (x == point.x && y == point.y) continue

                    val claim = claimTerritory(x, y, name, distance = ((point.x - x) + (y - point.y)))
                    if (claim != null) continue

                    if (y == point.y) break@xBLC
                    break // otherwise we hit the a overflow on y
                }
            }

            // Bottom Right Corner
            xBRC@for (x in point.x until width) {
                for (y in point.y until height) {
                    if (x == point.x && y == point.y) continue

                    val claim = claimTerritory(x, y, name, distance = ((x - point.x) + (y - point.y)))
                    if (claim != null) continue

                    if (y == point.y) break@xBRC
                    break // otherwise we hit the a overflow on y
                }
            }
        }
    }

    private fun claimTerritory(
        x: Int,
        y: Int,
        name: Int,
        distance: Int
    ): GridPoint? {
        val existing = at(x, y)
            ?: return claim(x, y, GridPoint(name, distance))

        // we don't have to care about the distance in our algorithm
        if (existing.name == name) return existing

        return when(existing.distanceCompareTo(distance)) {
            1 -> claim(x, y, GridPoint(name, distance))
            0 -> claim(x, y, GridPoint.Flag(distance))
            else -> null
        }
    }

    private fun at(x: Int, y: Int): GridPoint? = grid[y][x]

    private fun claim(x: Int, y: Int, gridPoint: GridPoint): GridPoint {
        grid[y][x] = gridPoint
        return gridPoint
    }

    companion object {
        fun of(points: List<Point>): Grid {
            val width = points.maxBy { it.x }!!.x + 1 // + 1 to account for the 0 start
            val height = points.maxBy { it.y }!!.y + 1 // + 1 to account for the 0 start

            // Height, Width because the top left corner is 0, 0
            val grid = Array(height) { Array<GridPoint?>(width) { null } }
            return Grid(points, grid, width, height)
        }

    }
}

private typealias GridArray = Array<Array<GridPoint?>>
