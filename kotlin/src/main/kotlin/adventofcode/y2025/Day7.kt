package adventofcode.y2025

import adventofcode.DataLoader
import adventofcode.Day

object Day7 : Day {
    override val day: Int get() = 7
    override val debug: Boolean get() = true

    internal val STAR1 get() = DataLoader.readNonBlankLinesFrom("/y2025/Day7Star1.txt")

    /**
     * Star1: 21
     * Star2: 40
     */
    private val EXAMPLE get() = """
        .......S.......
        ...............
        .......^.......
        ...............
        ......^.^......
        ...............
        .....^.^.^.....
        ...............
        ....^.^...^....
        ...............
        ...^.^...^.^...
        ...............
        ..^...^.....^..
        ...............
        .^.^.^.^.^...^.
        ...............
    """.trimIndent().lines()

    override fun star1Run(): String {
        val lines = STAR1
        val with = lines.first().length
        require(lines.all { it.length == with }) { "One or more of the lines is invalid" }

        val splits = mutableSetOf<Pair<Int, Int>>()
        val start = lines.first().indexOfFirst { it == 'S' }

        star1DepthFirstDecent(lines, lineIndex = 1, widthIndex = start, splits = splits)


        return "Total Splits: ${splits.size}"
    }

    private fun star1DepthFirstDecent(
        lines: List<String>,
        lineIndex: Int,
        widthIndex: Int,
        splits: MutableSet<Pair<Int, Int>>,
    ): Int {
        if (lineIndex >= lines.size) return 0
        if (widthIndex < 0) return 0
        val line = lines[lineIndex]
        if (widthIndex >= line.length) return 0

        when (val next = line[widthIndex]) {
            '.' -> return star1DepthFirstDecent(lines, lineIndex + 1, widthIndex, splits)
            '^' -> {
                val split = lineIndex to widthIndex
                if (splits.contains(split)) return 0 // Already indexed

                splits.add(split)
                return star1DepthFirstDecent(lines, lineIndex, widthIndex - 1, splits) + star1DepthFirstDecent(lines, lineIndex, widthIndex + 1, splits)
            }
            else -> throw IllegalArgumentException("Unknown split char: $next")
        }
    }

    override fun star2Run(): String {
        val lines = STAR1
        val with = lines.first().length
        require(lines.all { it.length == with }) { "One or more of the lines is invalid" }

        val splits = mutableMapOf<Pair<Int, Int>, Long>()
        val start = lines.first().indexOfFirst { it == 'S' }

        val timelines = star2DepthFirstDecent(lines, lineIndex = 1, widthIndex = start, splits = splits)


        return "Total Timelines: $timelines"
    }

    private fun star2DepthFirstDecent(
        lines: List<String>,
        lineIndex: Int,
        widthIndex: Int,
        splits: MutableMap<Pair<Int, Int>, Long>,
    ): Long {
        if (lineIndex >= lines.size) return 1L
        val line = lines[lineIndex]
        check(widthIndex >= 0) { "Past Left Boarder" }
        check(widthIndex < line.length) { "Past Right Boarder" }

        when (val next = line[widthIndex]) {
            '.' -> return star2DepthFirstDecent(lines, lineIndex + 1, widthIndex, splits)
            '^' -> {
                val split = lineIndex to widthIndex
                val cached = splits[split]
                if (cached != null) {
                    // The caching is required as this is in the range of 53 trillion for my input 🤣
                    return cached
                }

                val timelines = star2DepthFirstDecent(lines, lineIndex, widthIndex - 1, splits) + star2DepthFirstDecent(lines, lineIndex, widthIndex + 1, splits)
                splits[split] = timelines
                return timelines
            }
            else -> throw IllegalArgumentException("Unknown split char: $next")
        }
    }
}