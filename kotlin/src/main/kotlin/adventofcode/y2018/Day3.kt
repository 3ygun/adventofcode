package adventofcode.y2018

import adventofcode.DataLoader
import adventofcode.Day

object Day3 : Day {
    val STAR1_DATA = DataLoader.readLinesFromFor("/y2018/Day3Star1.txt")
    val STAR2_DATA = STAR1_DATA

    override val day = 3

    override fun star1Run(): String {
        val result = star1Calc(STAR1_DATA)
        return "Overlap of $result"
    }

    override fun star2Run(): String {
        val result = star2Calc(STAR2_DATA)
        return "Patch without overlap is #$result"
    }

    fun star1Calc(rawInput: List<String>): Int {
        val image = calculateImage(rawInput)
        return image.overlaps()
    }

    fun star2Calc(rawInput: List<String>): Int {
        val image = calculateImage(rawInput)
        return image.patchesWithoutOverlap().first()
    }

    private fun calculateImage(rawInput: List<String>): Image {
        if (rawInput.isEmpty()) throw IllegalArgumentException("Need some image input!")

        // Parse the plots
        val plots = rawInput.map { parsePlot(it) }

        // Calculate the length of the sides
        val maxWidth = plots
            .map { it.topLeftCorner.x + it.width }
            .max()!!
        val maxHeight = plots
            .map { it.topLeftCorner.y + it.height }
            .max()!!

        return Image(
            width = maxWidth,
            height = maxHeight
        ).plot(plots)
    }

    private val star1RegexParse = Regex("#(\\d+) @ (\\d+),(\\d+): (\\d+)x(\\d+)")

    private fun parsePlot(input: String): Plot {
        val groups = star1RegexParse
            .matchEntire(input)
            ?.groupValues
            ?: throw IllegalArgumentException("Input sequence invalid, given: '$input'")

        // Cleanup
        val nums = groups
            .drop(1)
            .map { it.toInt() }

        if (nums.size != 5) {
            throw IllegalArgumentException("Groups are wrong don't have 5 of them: $nums")
        }

        return Plot(
            mark = nums[0],
            topLeftCorner = Point(
                x = nums[1],
                y = nums[2]
            ),
            width = nums[3],
            height = nums[4]
        )
    }

    private data class Plot(
        val mark: Int,
        val topLeftCorner: Point,
        val width: Int,
        val height: Int
    ) {
        fun hasSpace(): Boolean = width > 0 && height > 0
    }

    private data class Point(
        val x: Int,
        val y: Int
    )

    private class Image(
        private val data: Array<IntArray>,
        private val patchesWithoutOverlap: MutableSet<Int> = HashSet()
    ) {
        constructor(
            width: Int,
            height: Int
        ) : this(Array(height + 1) { IntArray(width + 1) })
        // ^ Above + 1's make a boarder

        fun plot(plots: List<Plot>): Image {
            plots.forEach { this.plot(it) }
            return this
        }

        fun plot(toPlot: Plot) {
            if (!toPlot.hasSpace()) return // Nothing to plot
            patchesWithoutOverlap.add(toPlot.mark)

            val corner = toPlot.topLeftCorner
            for (x in corner.x until (corner.x + toPlot.width)) {
                for (y in corner.y until (corner.y + toPlot.height)) {
                    set(x, y, toPlot.mark)
                }
            }
        }

        private fun set(
            x: Int,
            y: Int,
            mark: Int
        ) {
            val existing = data[y][x]
            data[y][x] = when (existing) {
                0 -> mark
                else -> {
                    // We've hit an overlap so remove both
                    patchesWithoutOverlap.remove(existing)
                    patchesWithoutOverlap.remove(mark)
                    FLAG
                }
            }
        }

        fun overlaps(): Int {
            return data.sumOf { row ->
                row.sumOf {
                    val x: Int = if (it == FLAG) 1 else 0
                    x
                }
            }
        }

        fun patchesWithoutOverlap(): Set<Int> {
            return patchesWithoutOverlap
        }

        override fun toString(): String {
            val image = StringBuilder()

            data.forEach { x ->
                image.appendLine(x.joinToString(
                    separator = " ",
                    transform = { i ->
                        when(i) {
                            FLAG -> FLAG.toString()
                            else -> " $i"
                        }
                    }
                ))
            }

            return image.toString()
        }

        companion object {
            private const val FLAG = -1
        }
    }
}
