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
        val lines = STAR1
        // Area 24. One way to do this is between 9,5 and 2,3
//        val lines = EXAMPLE

        val redTiles = lines.map { it.split(",").let { (a, b) -> Star2Point(a.toInt(), b.toInt()) } }
        val maxCol = redTiles.maxOf { it.x }
        val maxRow = redTiles.maxOf { it.y }
//        return "maxCol = $maxCol and maxRow = $maxRow so squares: ${maxCol.toLong() * maxRow.toLong()}"

        val colChecks = Array<Star2OutOfRangeChecks>(maxCol + 1) { Star2OutOfRangeChecks(maxRow) { it.y } }
        val rowChecks = Array<Star2OutOfRangeChecks>(maxRow + 1) { Star2OutOfRangeChecks(maxCol) { it.x } }

        for (i in 0 until redTiles.size) {
            val iNext = if (i + 1 < redTiles.size) i + 1 else 0
            val iPrev = if (i - 1 < 0) redTiles.size - 1 else i - 1
            val current = redTiles[i]
            val next = redTiles[iNext]
            val previous = redTiles[iPrev]
            if (current == next) {
                throw IllegalStateException("Current Point: $current is same as the next Point: $next")
            }
            if (current == previous) {
                throw IllegalStateException("Current Point: $current is same as the previous Point: $previous")
            }

            val inside = findInside(redTiles, i)
            val line = Star2Line(current, next, inside)

            when {
                current.x == next.x -> {
                    // vertical col line
                    val col = current.x
                    val small = min(current.y, next.y)
                    val large = max(current.y, next.y)

                    colChecks[col].exclude(small..large, line)


                    // Vertical line
                    when {
                        current.y != previous.y -> throw IllegalStateException("Diagonal Line! Current Point: $current, Previous Point: $previous")
                        current.x == previous.x -> findInside(redTiles, iPrev)
                        current.x < previous.x -> Star2Inside.RIGHT
                        else /* current.x > previous.x */ -> Star2Inside.LEFT
                    }
                }
            }
        }

        val a = 1..1


        return ""
    }

    fun findInside(
        redTiles: List<Star2Point>,
        i: Int
    ): Star2Inside {
        val iNext = if (i + 1 < redTiles.size) i + 1 else 0
        val iPrev = if (i - 1 < 0) redTiles.size - 1 else i - 1
        val current = redTiles[i]
        val next = redTiles[iNext]
        val previous = redTiles[iPrev]
        if (current == next) {
            throw IllegalStateException("Current Point: $current is same as the next Point: $next")
        }
        if (current == previous) {
            throw IllegalStateException("Current Point: $current is same as the previous Point: $previous")
        }

        return when {
            current.x == next.x -> {
                // Vertical line
                when {
                    current.y != previous.y -> throw IllegalStateException("Diagonal Line! Current Point: $current, Previous Point: $previous")
                    current.x == previous.x -> findInside(redTiles, iPrev)
                    current.x < previous.x -> Star2Inside.RIGHT
                    else /* current.x > previous.x */ -> Star2Inside.LEFT
                }
            }
            current.y == next.y -> {
                // Horizontal line
                when {
                    current.x != previous.x -> throw IllegalStateException("Diagonal Line! Current Point: $current, Previous Point: $previous")
                    current.y == previous.y -> findInside(redTiles, iPrev)
                    current.y < previous.y -> Star2Inside.TOP
                    else /* current.y > previous.y */ -> Star2Inside.BOTTOM
                }
            }
            else -> throw IllegalStateException("Diagonal Line! Current Point: $current, Next Point: $next")
        }
    }

    class Star2Point(var x: Int, var y: Int)

    enum class Star2Inside {
        TOP,
        BOTTOM,
        LEFT,
        RIGHT,
    }

    class Star2Line(
        val pointA: Star2Point,
        val pointB: Star2Point,
        val inside: Star2Inside,
    ) {
        fun isPoint(): Boolean = pointA.x == pointB.x && pointA.y == pointB.y
    }

    class Star2View(
        private val num: Int,
    ) {
        private var lines = mutableListOf<Star2Line>()

        fun addLine(line: Star2Line) { lines += line }

        /**
         * @return things inside
         */
        fun flattenAsColum(): List<IntRange> {
            // We're trying to find what on the y-axis is covered
            val pointX = num
            lines.sortBy { min(it.pointA.y, it.pointB.x) }
            val results = mutableListOf<IntRange>()

            var i = 0
            var start = Int.MAX_VALUE
            var end = Int.MIN_VALUE
            while (i < lines.size) {
                val line = lines[i]
                val next = lines.getOrNull(i + 1)
                i++

                val ay = line.pointA.y
                val by = line.pointB.y
                val yMin = min(ay, by)
                val yMax = max(ay, by)
//                if (line.isPoint()) {
//                    start = min(start, line.pointA.y)
//                    continue
//                }
                if (line.pointA.x == line.pointB.x) {
                    // Either point or vertical line
                    start = min(start, yMin)
                    end = max(end, yMax)
                    continue
                } else {
                    // Horizontal line
                    when (line.inside) {
                        Star2Inside.LEFT,
                        Star2Inside.RIGHT -> throw IllegalStateException("Not caught in right place. left/right column")

                        Star2Inside.TOP -> {
                            if (start == Int.MAX_VALUE) throw IllegalStateException("Not caught in top place. nothing above num")
                            end = max(end, yMax)
                            results.add(start..end)
                            start = Int.MAX_VALUE
                            end = Int.MIN_VALUE
                        }
                        Star2Inside.BOTTOM -> {
                            start = min(start, yMin)
                        }
                    }
                }
            }

            return emptyList()
        }
    }

    class Star2ColOutOfRangeChecks(max: Int) {
        // Any ranges are "outside the safe area"
        private var checks = mutableListOf<Any>(1..max)

        fun exclude(
            range: IntRange,
            line: Star2Line,
        ) {
            require(range.first <= range.last) { "Descending range: $range for $line" }
            when (line.inside) {
                Star2Inside.LEFT,
                Star2Inside.RIGHT -> {
                    // doesn't matter for col
                }

                Star2Inside.TOP,
                Star2Inside.BOTTOM -> {
                    // matters for col
                }
            }

            val newChecks = mutableListOf<Any>()
            fun IntRange.addIfNotEmpty() {
                if (!this.isEmpty()) newChecks.add(this)
            }
            var lineAdded = false
            fun addLineIfNeeded() {
                if (lineAdded) return

                newChecks.add(line)
                lineAdded = true
            }
            for (existingCheck in checks) {
                when (existingCheck) {
                    is IntRange -> {
                        val ecf = existingCheck.first
                        val ecl = existingCheck.last
                        val rf = range.first
                        val rl = range.last
                        when {
                            ecl < rf -> newChecks.add(existingCheck)
                            ecf > rl -> {
                                addLineIfNeeded()
                                newChecks.add(existingCheck)
                            }
                            rf <= ecf && ecl <= rl -> {
                                /* No longer need this check */
                                addLineIfNeeded()
                            }

                            // range in existing
                            ecf <= rf && rl <= ecl -> {
                                (ecf until rf).addIfNotEmpty()
                                addLineIfNeeded()
                                ((rl + 1)..ecl).addIfNotEmpty()
                            }

                            // range shortens existing
                            ecf <= rf -> {
                                (ecf until rf).addIfNotEmpty()
                                addLineIfNeeded()
                            }
                            ecl >= rl -> {
                                addLineIfNeeded()
                                ((rl + 1)..ecl).addIfNotEmpty()
                            }
                            else -> throw IllegalStateException("don't think I can get here")
                        }
                    }
                    is Star2Line -> {
                        TODO("Basically need to deal with all the space within the line")
                    }
                }
            }
            checks = newChecks
        }
    }

    class Star2OutOfRangeChecks(
        max: Int,
        val op: (Star2Point) -> Int,
    ) {
        private var checks = mutableListOf<IntRange>(1..max)
        private val lines = mutableListOf<Star2Line>()

        fun exclude(
            range: IntRange,
            line: Star2Line,
        ) {
            val newChecks = mutableListOf<IntRange>()
            for (existingCheck in checks) {
                if (existingCheck.contains(range.))
            }
            range.con
            checks
            lines.add(line)
        }
    }
}
