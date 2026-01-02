package adventofcode.y2025

import adventofcode.DataLoader
import adventofcode.Day
import kotlin.math.sqrt

object Day8 : Day {
    override val day: Int = 8
    override val debug: Boolean get() = false

    internal val STAR1 get() = DataLoader.readNonBlankLinesFrom("/y2025/Day8Star1.txt")

    private val EXAMPLE
        get() = """
        162,817,812
        57,618,57
        906,360,560
        592,479,940
        352,342,300
        466,668,158
        542,29,236
        431,825,988
        739,650,466
        52,470,668
        216,146,977
        819,987,18
        117,168,530
        805,96,715
        346,949,466
        970,615,88
        941,993,340
        862,61,35
        984,92,344
        425,690,689
    """.trimIndent().lines()

    // EXAMPLE2: Tests the Union-Find chain merge bug
    // 6 boxes, 5 pairs to connect. Shortest pairs in order:
    //   1. A-B (dist=1) → circuit {A,B}
    //   2. C-D (dist=2) → circuit {C,D}
    //   3. E-F (dist=3) → circuit {E,F}
    //   4. D-E (dist=8) → merges {C,D} with {E,F} → c3.id = c2.id
    //   5. B-C (dist=9) → merges {A,B} with {C,D,E,F} → c2.id = c1.id
    // BUG: c3 still has old id, so {E,F} becomes orphaned!
    // Buggy answer: 4 * 2 = 8 (two circuits: {A,B,C,D} and {E,F})
    // Correct answer: 6 (one circuit: {A,B,C,D,E,F})
    private val EXAMPLE2
        get() = """
        0,0,0
        1,0,0
        10,0,0
        12,0,0
        20,0,0
        23,0,0
    """.trimIndent().lines()

    override fun star1Run(): String {
//        val lines = STAR1
//        val maxItems = 1000
        // Expecting 40
//        val lines = EXAMPLE
//        val maxItems = 10
        // Expecting 6 (buggy code gives 8)
        val lines = EXAMPLE2
        val maxItems = 5
        val jBoxes = lines.map { line ->
            line.split(",")
                .map { sNum -> sNum.toLong() }
                .toList()
                .let { Star1JBox(it[0], it[1], it[2]) }
        }
        check(jBoxes.size == jBoxes.toSet().size) { "No duplicate lights" }

        val sortedClosestPairs = Star1SortedClosestPairs(maxItems)
        jBoxes.forEachIndexed { i, a ->
            jBoxes.forEachIndexed { j, b ->
                // Skip everything we've already added
                if (j > i) {
                    sortedClosestPairs.checkAndAddPair(a, b)
                }
            }
        }
        sortedClosestPairs.printPairs()

        val circuits = sortedClosestPairs.toCircuits()
        if (debug) {
            println(circuits)
            println(
                circuits.entries
                .map { it.value.size }
                .sortedDescending())
            println()
            println("[")
            circuits.entries
                .sortedByDescending { it.value.size }
                .forEach { println("Size: ${it.value.size}, Data: $it") }
            println("]")
            println()
        }

        val top3Values = circuits.entries
            .map { it.value.size }
            .sortedDescending()
            .take(3)
            .fold(1) { acc, i -> acc * i }

        return "Top 3 sections Lights: $top3Values"
    }

    data class Star1JBox(
        val x: Long,
        val y: Long,
        val z: Long,
    ) {
        fun distanceTo(other: Star1JBox): Double {
            fun Long.squared() = this * this
            return sqrt(
                ((x - other.x).squared() + (y - other.y).squared() + (z - other.z).squared()).toDouble()
            )
        }
    }

    data class Star1Pair(
        val distance: Double,
        val a: Star1JBox,
        val b: Star1JBox,
    ) {
        var formerPair: Star1Pair? = null
        var nextPair: Star1Pair? = null
    }

    class Star1SortedClosestPairs(
        private val maxItems: Int,
    ) {
        private var numOfItems = 0
        private var lastPair: Star1Pair? = null
        private var firstPair: Star1Pair? = null
        private var i = 0

        init {
            require(maxItems > 0) { "maxItems > 0" }
        }

        fun checkAndAddPair(a: Star1JBox, b: Star1JBox) {
            val distance = a.distanceTo(b)
            val pair = Star1Pair(distance, a, b)
            i++
            if (numOfItems < maxItems) {
                val pushed = pushPair(pair, end = lastPair, endItem = true)
                if (pushed) { // Handle duplicate pairs
                    numOfItems++
                    if (numOfItems == 1) lastPair = firstPair
//                    if (debug) printPairs()
                }
            } else {
                val pushed = pushPair(pair, end = lastPair, endItem = true, addToEnd = false)
                if (pushed) {
                    val previousLast = lastPair!!
                    lastPair = previousLast.formerPair
                    lastPair!!.nextPair = null // mark new "end" as "end"
                    previousLast.formerPair = null // unlink previous "end"
                    if (i % 10 == 0) {
                        println("On [i: $i] length: ${computeCurrentLength(firstPair, 0)}, $numOfItems")
                    }
//                    if (debug) printPairs()
                }
            }
        }

        private tailrec fun computeCurrentLength(node: Star1Pair?, count: Int): Int {
            if (node == null) return count
            return computeCurrentLength(node.nextPair, count + 1)
        }

        /**
         * We're trying to iterate from "right to left" or "furthest to nearest"
         *
         * @return true/false was something added
         */
        private tailrec fun pushPair(
            pair: Star1Pair,
            end: Star1Pair?,
            endItem: Boolean = false,
            addToEnd: Boolean = true
        ): Boolean {
            if (end == null) {
                // New "shortest element"
                val priorFirstPair = firstPair
                pair.nextPair = priorFirstPair
                priorFirstPair?.formerPair = pair
                firstPair = pair
                return true
            }

            if (pair.distance < end.distance) {
                return pushPair(pair, end.formerPair)
            } else if (pair.distance == end.distance) {
                return if (pair.a == end.b && pair.b == end.a) {
                    // We've already recorded this pair
                    false
                } else {
                    pushPair(pair, end.formerPair)
                }
            } else if (addToEnd) {
                // pair.distance > end.distance
                pair.formerPair = end
                pair.nextPair = end.nextPair
                end.nextPair = pair
                pair.nextPair?.formerPair = pair
                if (endItem) lastPair = pair
                return true
            } else {
                return false
            }
        }

        fun printPairs() {
            println()
            println("first: $firstPair")
            println("[ (size: $numOfItems)")
            var count = 0
            var currentPair: Star1Pair? = firstPair
            while (currentPair != null) {
                println("$count : $currentPair, previous: ${currentPair.formerPair?.distance}, next: ${currentPair.nextPair?.distance}")
                currentPair = currentPair.nextPair
                count++
            }
            println("]")
            println("lastPair: $lastPair")
            println()
        }

        fun toCircuits(): Map<Star1JBox, List<Star1JBox>> {
            // Union-Find with path compression
            val parent = mutableMapOf<Star1JBox, Star1JBox>()

            fun find(x: Star1JBox): Star1JBox {
                if (parent[x] != x) {
                    parent[x] = find(parent[x]!!)  // Path compression
                }
                return parent[x]!!
            }

            fun union(x: Star1JBox, y: Star1JBox) {
                val px = find(x)
                val py = find(y)
                if (px != py) {
                    parent[py] = px
                }
            }

            var currentPair: Star1Pair? = firstPair
            while (currentPair != null) {
                // Initialize if not seen
                if (currentPair.a !in parent) parent[currentPair.a] = currentPair.a
                if (currentPair.b !in parent) parent[currentPair.b] = currentPair.b
                // Union the two boxes
                union(currentPair.a, currentPair.b)
                currentPair = currentPair.nextPair
            }

            // Group by root representative
            return parent.keys.groupBy { find(it) }
        }
    }

    override fun star2Run(): String {
        return ""
    }
}