package adventofcode.y2025

import adventofcode.DataLoader
import adventofcode.Day
import kotlin.math.max
import kotlin.math.min

object Day9 : Day {
    override val day: Int = 9
    override val debug: Boolean get() = false

    internal val STAR1 get() = DataLoader.readNonBlankLinesFrom("/y2025/Day9Star1.txt")

    private val EXAMPLE get() = """
        7,1
        11,1
        11,7
        9,7
        9,5
        2,5
        2,3
        7,3
    """.trimIndent().lines()

    override fun star1Run(): String {
        // 4729332959 (from ((85705, 83098), (16429, 14832)))
        val lines = STAR1
        // 50 between 2,5 and 11,1:
//        val lines = EXAMPLE

        val redTiles = lines.map { it.split(",").let { (a, b) -> a.toLong() to b.toLong() } }

        var largestArea = 0L
        var largestPair = (-1L to -1L) to (-1L to -1L)
        redTiles.forEachIndexed { i, a ->
            for (j in (i + 1) until redTiles.size) {
                val b = redTiles[j]
                val size = when {
                    a.first == b.first -> when {
                        a.second == b.second -> 1L
                        a.second > b.second -> 1 + a.second - b.second
                        else -> 1 + b.second - a.second
                    }
                    a.first > b.first -> {
                        val x = 1 + a.first - b.first
                        when {
                            a.second == b.second -> x
                            a.second > b.second -> x * (1 + a.second - b.second)
                            else -> x * (1 + b.second - a.second)
                        }
                    }
                    else -> {
                        val x = 1 + b.first - a.first
                        when {
                            a.second == b.second -> x
                            a.second > b.second -> x * (1 + a.second - b.second)
                            else -> x * (1 + b.second - a.second)
                        }
                    }
                }

                if (size > largestArea) {
                    largestArea = size
                    largestPair = a to b
                }
            }
        }

        return "Largest area: $largestArea (from $largestPair)"
    }

    override fun star2Run(): String {
//        val lines = STAR1
        // Area 24. One way to do this is between 9,5 and 2,3
        val lines = EXAMPLE

        val redTiles = lines.map { it.split(",").let { (a, b) -> Star2Point(a.toInt(), b.toInt()) } }
        val maxCol = redTiles.maxOf { it.x }

        val colChecksLines = Array<Star2View>(maxCol + 2) { Star2View(it) }
        for (i in 0 until redTiles.size) {
            val iNext = if (i + 1 < redTiles.size) i + 1 else 0
            val current = redTiles[i]
            val next = redTiles[iNext]
            val line = Star2Line(current, next)

            if (current.x == next.x || current.y == next.y) {
                // fine
            } else {
                throw IllegalStateException("Diagonal line: $line")
            }

            if (current.x == next.x) {
                colChecksLines[current.x].addLine(line)
            } else {
                val cX = current.x
                val nX = next.x
                val xMin = min(cX, nX)
                val xMax = max(cX, nX)
                (xMin..xMax).forEach { x ->
                    colChecksLines[x].addLine(line)
                }
            }
        }

        val colInside = Array<List<IntRange>>(maxCol + 2) { colChecksLines[it].flattenAsColum() }

        var largestArea = 0L
        var largestPair = Star2Point(-1, -1) to Star2Point(-1, -1)
        redTiles.forEachIndexed { i, aa ->
            val a = aa.x.toLong() to aa.y.toLong()
            for (j in (i + 1) until redTiles.size) {
                val bb = redTiles[j]
                val b = bb.x.toLong() to bb.y.toLong()
                val size = when {
                    a.first == b.first -> when {
                        a.second == b.second -> 1L
                        a.second > b.second -> 1 + a.second - b.second
                        else -> 1 + b.second - a.second
                    }
                    a.first > b.first -> {
                        val x = 1 + a.first - b.first
                        when {
                            a.second == b.second -> x
                            a.second > b.second -> x * (1 + a.second - b.second)
                            else -> x * (1 + b.second - a.second)
                        }
                    }
                    else -> {
                        val x = 1 + b.first - a.first
                        when {
                            a.second == b.second -> x
                            a.second > b.second -> x * (1 + a.second - b.second)
                            else -> x * (1 + b.second - a.second)
                        }
                    }
                }

                if (size <= largestArea) {
                    // Wouldn't be bigger even if valid
                } else {
                    // Validate
                    val leftX = min(aa.x, bb.x)
                    val rightX = max(aa.x, bb.x)
                    val topY = min(aa.y, bb.y)
                    val bottomY = max(aa.y, bb.y)

                    val valid = (leftX..rightX).all { x ->
                        val col = colInside[x]
                        (topY..bottomY).all { y ->
                            col.any { it.contains(y) }
                        }
                    }

                    if (valid) {
                        largestArea = size
                        largestPair = aa to bb
                    }
                }
            }
        }

        return "Largest area: $largestArea (from $largestPair)"
    }

    data class Star2Point(var x: Int, var y: Int)

    data class Star2Line(
        val pointA: Star2Point,
        val pointB: Star2Point,
    )

    class Star2View(
        private val col: Int,
    ) {
        private var lines = mutableListOf<Star2Line>()

        fun addLine(line: Star2Line) { lines += line }

        /**
         * @return things inside
         */
        fun flattenAsColum(): List<IntRange> {
            // We're trying to find what on the y-axis is covered
            lines.sortWith { line1, line2 ->
                val line1Vertical = line1.pointA.x == line1.pointB.x
                val line2Vertical = line2.pointA.x == line2.pointB.x
                val line1MinY = min(line1.pointA.y, line1.pointB.y)
                val line2MinY = max(line2.pointA.y, line2.pointB.y)

                when {
                    line1Vertical && line2Vertical -> line1MinY.compareTo(line2MinY)
                    line1Vertical -> {
                        // TODO : Deal
                        0
                    }
                    line2Vertical -> {
                        0
                    }
                    else -> 0
                }
            }

            lines.sortBy {
                when {
                    it.pointA.x == it.pointB.x -> (min(it.pointA.y, it.pointB.y) * 2) + 1
                    it.pointA.x == col -> it.pointA.y * 2
                    it.pointB.x == col -> it.pointB.y * 2
                    it.pointA.y == it.pointB.y -> it.pointA.y * 2
                    else -> throw IllegalStateException("Unknown stuff")
                }
            }
            val results = mutableListOf<IntRange>()

            var start = Int.MAX_VALUE
            var end = Int.MIN_VALUE
            lines.forEach { line ->
                val ay = line.pointA.y
                val by = line.pointB.y
                val yMin = min(ay, by)
                val yMax = max(ay, by)
                if (line.pointA.x == line.pointB.x) {
                    // Either point or vertical line
                    val aStart = min(start, yMin)
                    val aEnd = max(end, yMax)
                    results.add(aStart..aEnd)
                } else {
                    val inside = start != Int.MAX_VALUE
                    if (inside) {
                        end = max(end, yMax)
                        results.add(start..end)
                        start = Int.MAX_VALUE
                        end = Int.MIN_VALUE
                    } else {
                        start = min(start, yMin)
                    }
                }
            }

            if (start == Int.MAX_VALUE && end == Int.MIN_VALUE) {
                // We're good
            } else {
                require(start != Int.MAX_VALUE) { "Col: $col, Start: $start and End: $end. Don't want start max_value. $lines" }
                require(end != Int.MIN_VALUE) { "Col: $col, Start: $start and End: $end. Don't want end min_value. $lines" }
                results.add(start..end)
            }

            return results
        }
    }
}
