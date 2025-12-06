package adventofcode.y2025

import adventofcode.DataLoader
import adventofcode.Day
import kotlin.math.max

object Day5 : Day {
    override val day = 5

    internal val STAR1: List<String> get() = DataLoader.readLinesFromFor("/y2025/Day5Star1.txt")

    /**
     * Star1: 3
     * Star2: 14
     */
    private val EXAMPLE get() = """
        3-5
        10-14
        16-20
        12-18

        1
        5
        8
        11
        17
        32
    """.trimIndent().lines()

    /**
     * Star2: 14
     */
    private val EXAMPLE2 get() = """
        3-5
        10-14
        16-20
        12-18
        17-19

    """.trimIndent().lines()

    override fun star1Run(): String {
        val lines = STAR1

        val ingredients = mutableListOf<LongRange>()
        val walker = lines.iterator()
        while (walker.hasNext()) {
            val line = walker.next()
            if (line.isBlank()) {
                // Found a blank line, so going forward is "available ingredients" going forward
                break
            }

            ingredients.add(line.split("-").map { it.toLong() }.let { it[0]..it[1] })
        }
        println()
        println("Star1 ingredients found: ${ingredients.size}")
        println()

        var freshIngredients = 0L
        while (walker.hasNext()) {
            val availableIngredientId = walker.next().toLong()
            val isFresh = ingredients.any { availableIngredientId in it }
            if (isFresh) freshIngredients++
        }

        return "Number of fresh ingredients: $freshIngredients"
    }

    override fun star2Run(): String {
        val lines = STAR1

        val ingredients = mutableListOf<LongRange>()
        val walker = lines.iterator()
        while (walker.hasNext()) {
            val line = walker.next()
            if (line.isBlank()) {
                // Found a blank line, so going forward is "available ingredients" going forward
                break
            }

            ingredients.add(line.split("-").map { it.toLong() }.let { it[0]..it[1] })
        }

        println()
        println("Star1 ingredients found: ${ingredients.size}")
        println()

        ingredients.forEach {
            require((it.first > 0) || (it.last > 0)) {
                "Given ingredient range $it includes negative numbers. Redo the code to handle this!"
            }
            require(it.first <= it.last) {
                "Given ingredient range $it decrements instead of increments. Redo the code to handle this!"
            }
        }
        ingredients.sortBy { it.first }

//        println()
//        ingredients.forEach { println(it) }
//        println()

        val finalIngredients = mutableListOf<LongRange>()
        var mostRecentRange: LongRange? = null
        ingredients.forEach {
            val lastRange = mostRecentRange
            if (lastRange == null) mostRecentRange = it
            else {
                if (it.first in lastRange) {
                    // Modify the last range to be "larger"
                    mostRecentRange = lastRange.first..(max(lastRange.last, it.last))
                } else {
                    // Net new range
                    finalIngredients.add(lastRange)
                    mostRecentRange = it
                }
            }
        }
        finalIngredients.add(mostRecentRange!!)

//        println()
//        finalIngredients.forEach { println(it) }
//        println()

        val possibleFreshIngredients: Long = finalIngredients.sumOf { it.last - (it.first - 1) }
        return "Number of possible fresh ingredients: $possibleFreshIngredients"
    }
}
