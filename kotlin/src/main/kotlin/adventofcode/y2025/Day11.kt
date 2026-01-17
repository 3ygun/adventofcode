package adventofcode.y2025

import adventofcode.Day

object Day11 : Day {
    override val day: Int get() = 11

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

    override fun star1Run(): String {
        // 5 paths
        val lines = EXAMPLE

        val devices: Map<String, List<String>> = lines
            .map { line ->
                val (name, sConnections) = line.split(':', limit = 2)
                val connections = sConnections.trim().split(' ')
                name to connections
            }
            .associateBy({ it.first }) { it.second }

        devices.forEach { (k, v) ->
            println("$k: $v")
        }

        val paths = 0
        return "Paths: $paths"
    }

    override fun star2Run(): String {
        return ""
    }
}