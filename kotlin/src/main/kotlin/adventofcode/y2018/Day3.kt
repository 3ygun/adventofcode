package adventofcode.y2018

import adventofcode.Day
import adventofcode.y2018.data.Day3Data
import java.lang.IllegalArgumentException

object Day3 : Day {
    override val day = 3

    override fun star1Run(): String {
        val result = star1Calc(Day3Data.star1())
        return "Overlap of $result"
    }

    override fun star2Run(): String {
        val result = star2Calc(Day3Data.star2())
        return "Patch without overlap is #$result"
    }

    fun star1Calc(rawInput: List<String>): Int {
        val inputs = rawInput.map { parsePlot(it) }
        val image = createBaseImageFromPlots(inputs)

        inputs.map { image.plot(it) }

        // println(image)

        return image.overlaps()
    }

    fun star2Calc(rawInput: List<String>): Int {
        val inputs = rawInput.map { parsePlot(it) }
        val image = createBaseImageFromPlots(inputs)

        inputs.map { image.plot(it) }

        // println(image)

        return image.patchesWithoutOverlap().first()
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
        fun hasSpace(): Boolean {
            return width > 0 && height > 0
        }
    }

    private data class Point(
        val x: Int,
        val y: Int
    )

    private fun createBaseImageFromPlots(inputs: List<Plot>): Image {
        val maxWidth = inputs
            .map { it.topLeftCorner.x + it.width }
            .max()

        val maxHeight = inputs
            .map { it.topLeftCorner.y + it.height }
            .max()

        // These should never be null
        return Image(
            width = maxWidth!!,
            height = maxHeight!!
        )
    }

    private class Image(
        private val data: Array<IntArray>,
        private val patchesWithoutOverlap: MutableSet<Int> = HashSet()
    ) {
        constructor(
            width: Int,
            height: Int
        ) : this(Array(height + 1) { IntArray(width + 1) })

        fun plot(toPlot: Plot) {
            if (!toPlot.hasSpace()) return // Nothing to plot
            patchesWithoutOverlap.add(toPlot.mark)

            val corner = toPlot.topLeftCorner
            for (x in corner.x..(corner.x + toPlot.width - 1)) {
                for (y in corner.y..(corner.y + toPlot.height - 1)) {
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
                    patchesWithoutOverlap.remove(existing)
                    patchesWithoutOverlap.remove(mark)
                    -1 // Flag saying multiple things hit
                }
            }
        }

        fun overlaps(): Int {
            return data.sumBy { row ->
                row.sumBy {
                    when (it) {
                        -1 -> 1
                        else -> 0
                    }
                }
            }
        }

        fun patchesWithoutOverlap(): Set<Int> {
            return patchesWithoutOverlap
        }

        override fun toString(): String {
            val image = StringBuilder()

            data.forEach { x ->
                image.appendln(x.joinToString(
                    separator = " ",
                    transform = { i ->
                        when(i) {
                            -1 -> "-1"
                            else -> " $i"
                        }
                    }
                ))
            }

            return image.toString()
        }
    }
}