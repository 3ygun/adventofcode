package adventofcode.y2025

import adventofcode.DataLoader
import adventofcode.Day
import kotlin.math.sqrt

object Day8 : Day {
    override val day: Int = 8
    override val debug: Boolean get() = true

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

    override fun star1Run(): String {
        val lines = EXAMPLE
        val maxItems = 10
        val jBoxes = lines.map { line ->
            line.split(",")
                .map { sNum -> sNum.toLong() }
                .toList()
                .let { Star1JBox(it[0], it[1], it[2]) }
        }
        check(jBoxes.size == jBoxes.toSet().size) { "No duplicate lights" }

        val sortedClosestPairs = Star1SortedClosestPairs(maxItems)
        for (a in jBoxes) {
            for (b in jBoxes) {
                if (a == b) continue
                sortedClosestPairs.checkAndAddPair(a, b)
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
            fun Double.squared() = this * this
            return sqrt(
                (x - other.x).toDouble().squared() + (y - other.y).toDouble().squared() + (z - other.z).toDouble()
                    .squared()
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

        init {
            require(maxItems > 0) { "maxItems > 0" }
        }

        fun checkAndAddPair(a: Star1JBox, b: Star1JBox) {
            val distance = a.distanceTo(b)
            val pair = Star1Pair(distance, a, b)
            if (numOfItems < maxItems) {
                pushPair(pair, end = lastPair, endItem = true)
                numOfItems++
                if (numOfItems == 1) lastPair = firstPair
                if (debug) printPairs()
            } else {
                val pushed = pushPair(pair, end = lastPair, endItem = true, addToEnd = false)
                if (pushed) {
                    val previousLast = lastPair!!
                    lastPair = previousLast.formerPair
                    lastPair!!.nextPair = null // mark new "end" as "end"
                    previousLast.formerPair = null // unlink previous "end"
                    if (debug) printPairs()
                }
            }
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

        fun toCircuits(): Map<Star1CircuitId, List<Star1JBox>> {
            var nextCircuitId = 1
            val pointToCircuitId = mutableMapOf<Star1JBox, Star1CircuitId>()

            var currentPair: Star1Pair? = firstPair
            while (currentPair != null) {
                val aExistingCircuit = pointToCircuitId[currentPair.a]
                val bExistingCircuit = pointToCircuitId[currentPair.b]
                when {
                    aExistingCircuit == null && bExistingCircuit == null -> {
                        val circuit = Star1CircuitId(nextCircuitId)
                        nextCircuitId++
                        pointToCircuitId[currentPair.a] = circuit
                        pointToCircuitId[currentPair.b] = circuit
                    }

                    aExistingCircuit != null && bExistingCircuit != null -> {
                        bExistingCircuit.id = aExistingCircuit.id // combine the circuits
                    }

                    aExistingCircuit != null -> {
                        pointToCircuitId[currentPair.b] = aExistingCircuit
                    }

                    bExistingCircuit != null -> {
                        pointToCircuitId[currentPair.a] = bExistingCircuit
                    }
                }
                currentPair = currentPair.nextPair
            }

            return pointToCircuitId.entries.groupBy({ it.value }) { it.key }
        }
    }

    data class Star1CircuitId(
        var id: Int
    )

    override fun star2Run(): String {
        return ""
    }
}