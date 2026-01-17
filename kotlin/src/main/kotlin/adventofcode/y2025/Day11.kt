package adventofcode.y2025

import adventofcode.DataLoader
import adventofcode.Day
import kotlin.math.min

object Day11 : Day {
    override val day: Int get() = 11
    override val debug: Boolean get() = false

    internal val STAR1 get() = DataLoader.readNonBlankLinesFrom("/y2025/Day11Star1.txt")

    private val EXAMPLE = """
        aaa: you hhh
        you: bbb ccc
        bbb: ddd eee
        ccc: ddd eee fff
        ddd: ggg
        eee: out
        fff: out
        ggg: out
        hhh: ccc fff iii
        iii: out
    """.trimIndent().lines()

    private val EXAMPLE_STAR2 = """
        svr: aaa bbb
        aaa: fft
        fft: ccc
        bbb: tty
        tty: ccc
        ccc: ddd eee
        ddd: hub
        hub: fff
        eee: dac
        dac: fff
        fff: ggg hhh
        ggg: out
        hhh: out
    """.trimIndent().lines()

    override fun star1Run(): String {
        // Paths: 643 (longest path: 8) for me
        val lines = STAR1
        // 5 paths
//        val lines = EXAMPLE

        val devices: Map<String, List<String>> = lines
            .map { line ->
                val (name, sConnections) = line.split(':', limit = 2)
                val connections = sConnections.trim().split(' ').toSet().toList()
                name to connections
            }
            .associateBy({ it.first }) { it.second }

//        if (debug) {
//            devices.forEach { (k, v) ->
//                println("$k: $v")
//            }
//        }

        val paths: MutableSet<List<String>> = mutableSetOf()
        @Suppress("NON_TAIL_RECURSIVE_CALL")
        tailrec fun recurse(node: Day11Star1Node, level: Int = 0) {
            val name = node.name
            if (name == "out") {
                paths.add(node.printPath())
                return
            } else if (level >= 500) {
                println("Found a loop, giving up")
                return
            }

            val found = requireNotNull(devices[name]) { "No device found for $name" }
            if (found.size == 1) {
                val current = Day11Star1Node(found.first(), node)
                return recurse(current, level + 1)
            } else {
                for (nextName in found) {
                    val current = Day11Star1Node(nextName, node)
                    // comfortable that this isn't a "tail call"
                    recurse(current, level + 1)
                }
                return
            }
        }

        val name = "you"
        val start = Day11Star1Node(name)
        val found = requireNotNull(devices[name]) { "No device found for $name" }
        for (nextName in found) {
            val current = Day11Star1Node(nextName, start)
            // comfortable that this isn't a "tail call"
            recurse(current)
        }

        val maxPathLength = paths.maxOf { it.size }
        return "Paths: ${paths.size} (longest path: ${maxPathLength})"
    }

    private class Day11Star1Node(
        val name: String,
        val previousNode: Day11Star1Node? = null,
    ) {
        fun printPath(): List<String> {
            val result = mutableListOf<String>()
            var current: Day11Star1Node? = this
            while (current != null) {
                result.add(current.name)
                current = current.previousNode
            }
            return result.reversed()
        }
    }

    override fun star2Run(): String {
        // 417190406827152
        val lines = STAR1
        // 2 paths
//        val lines = EXAMPLE_STAR2

        val devices: Map<String, List<String>> = lines
            .map { line ->
                val (name, sConnections) = line.split(':', limit = 2)
                val connections = sConnections.trim().split(' ').toSet().toList()
                name to connections
            }
            .associateBy({ it.first }) { it.second }

        val cachedEnds = mutableMapOf<String, Day11Star2Cache>()
        @Suppress("NON_TAIL_RECURSIVE_CALL")
        fun recurse(
            node: Day11Star1Node,
            level: Int,
        ): Day11Star2Cache {
            val name = node.name
            if (name == "out") {
                return Day11Star2Cache(numPathsOut = 1)
            } else if (level >= 500) {
                println("Found a loop, giving up")
                return Day11Star2Cache()
            }

            // Have we already computed this path?
            val cache = cachedEnds[name]
            if (cache != null) return cache

            val found = requireNotNull(devices[name]) { "No device found for $name" }
            if (found.size == 1) {
                val current = Day11Star1Node(found.first(), node)
                return recurse(current, level + 1)
                    .let { it ->
                        val cacheData = it.cloneWith(name)
                        cachedEnds[name] = cacheData
                        cacheData
                    }
            } else {
                val results = mutableListOf<Day11Star2Cache>()
                for (nextName in found) {
                    val current = Day11Star1Node(nextName, node)
                    // comfortable that this isn't a "tail call"
                    recurse(current, level + 1)
                        .let { results.add(it.cloneWith(name)) }
                }

                val cacheData = results.fold(Day11Star2Cache()) { acc, cache ->
                    Day11Star2Cache(
                        numPathsOut = acc.numPathsOut + cache.numPathsOut,
                        numPathsWhichAddFft = acc.numPathsWhichAddFft + cache.numPathsWhichAddFft,
                        numPathsWhichAddDac = acc.numPathsWhichAddDac + cache.numPathsWhichAddDac,
                        numPathsWithBoth = acc.numPathsWithBoth + cache.numPathsWithBoth,
                    )
                }
                cachedEnds[name] = cacheData
                return cacheData
            }
        }

        val name = "svr" // from server
        val start = Day11Star1Node(name)
        val found = requireNotNull(devices[name]) { "No device found for $name" }
        val results = mutableListOf<Day11Star2Cache>()
        for (nextName in found) {
            val current = Day11Star1Node(nextName, start)
            // comfortable that this isn't a "tail call"
            recurse(current, level = 2)
                .let { results.add(it.cloneWith(name)) }
        }

        val cacheData = results.fold(Day11Star2Cache()) { acc, cache ->
            Day11Star2Cache(
                numPathsOut = acc.numPathsOut + cache.numPathsOut,
                numPathsWhichAddFft = acc.numPathsWhichAddFft + cache.numPathsWhichAddFft,
                numPathsWhichAddDac = acc.numPathsWhichAddDac + cache.numPathsWhichAddDac,
                numPathsWithBoth = acc.numPathsWithBoth + cache.numPathsWithBoth,
            )
        }

        return "Paths with dac and fft: ${cacheData.numPathsWithBoth}"
    }

    private class Day11Star2Cache(
        val numPathsOut: Long = 0,
        val numPathsWhichAddFft: Long = 0,
        val numPathsWhichAddDac: Long = 0,
        val numPathsWithBoth: Long = 0,
    ) {
        fun cloneWith(name: String): Day11Star2Cache {
            val isFft = name == "fft"
            val isDac = name == "dac"
            if (!(isFft || isDac)) return this

            val numFft = if (isFft) numPathsOut else numPathsWhichAddFft
            val numDac = if (isDac) numPathsOut else numPathsWhichAddDac
            return Day11Star2Cache(
                numPathsOut = numPathsOut,
                numPathsWhichAddFft = numFft,
                numPathsWhichAddDac = numDac,
                numPathsWithBoth = min(numFft, numDac),
            )
        }
    }
}