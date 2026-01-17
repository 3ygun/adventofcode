package adventofcode.y2025

import adventofcode.DataLoader
import adventofcode.Day

object Day11 : Day {
    override val day: Int get() = 11
    override val debug: Boolean get() = true

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

    class Day11Star1Node(
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

//        if (debug) {
//            devices.forEach { (k, v) ->
//                println("$k: $v")
//            }
//        }

        val paths: MutableList<Int> = mutableListOf()
        @Suppress("NON_TAIL_RECURSIVE_CALL")
        fun recurse(
            name: String,
            level: Int,
            fft: Boolean = false,
            dac: Boolean = false,
        ) {
            if (name == "out") {
                if (fft && dac) {
                    paths.add(level)
                }
                return
            } else if (level >= 500) {
                println("Found a loop, giving up")
                return
            }

            val nextFft = fft || name == "fft"
            val nextDac = dac || name == "dac"

            val found = requireNotNull(devices[name]) { "No device found for $name" }
            if (found.size == 1) {
                val current = found.first()
                return recurse(current, level + 1, fft = nextFft, dac = nextDac)
            } else {
                for (nextName in found) {
                    val current = nextName
                    // comfortable that this isn't a "tail call"
                    recurse(current, level + 1, fft = nextFft, dac = nextDac)
                }
                return
            }
        }

        val name = "svr" // from server
        val found = requireNotNull(devices[name]) { "No device found for $name" }
        for (nextName in found) {
            // comfortable that this isn't a "tail call"
            recurse(nextName, level = 2)
        }

        val maxPathLength = paths.maxOf { it }
        return "Paths with dac and fft: ${paths.size} (longest path: ${maxPathLength})"
    }
}