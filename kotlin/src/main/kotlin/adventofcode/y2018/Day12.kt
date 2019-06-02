package adventofcode.y2018

import adventofcode.DataLoader
import adventofcode.Day

object Day12 : Day {
    internal val STAR1 = DataLoader.readLinesFromFor("/y2018/Day12Star1.txt")
    internal val STAR2 = STAR1

    override val day = 12

    override fun star1Run(): String {
        val result = star1Calc(20, STAR1)
        return "The sum of all potted plants is $result"
    }

    override fun star2Run(): String {
        val result = star1Calc(50000000000, STAR2)
        return "The sum of all potted plants is $result"
    }

    internal fun star1Calc(
        generations: Long,
        input: List<String>,
        startingIndex: Int = 0
    ): Long {
        val env = input.parseToEnvironment(startingIndex)
        env.nextUntil(generations)
        return env.plantScore()
    }

    private class Environment(
        plantLayout: String,
        private val rules: List<PlantRules>,
        startingIndex: Int = 0
    ) {
        private var env: Env = Env(
            plantLayout = "....$plantLayout....",
            startingIndex = startingIndex - 4L
        )

        /**
         * Iterate through some number of generations.
         * First using a [dynamicNext] method until the pattern repeats then calculate what the
         * final result would be at the remaining generation point.
         */
        fun nextUntil(generations: Long) {
            var itterations = 0L
            while (!env.constant() && itterations < generations) {
                env = dynamicNext(env)
                itterations ++
            }

            if (itterations >= generations) return
            // The pattern repeated

            // Note (dsoller) : this is probably a bit hardcoded but I'm over it...
            // Basically, assumes that the change between the plant layouts is constant which
            //  could be false but isn't for my input puzzle.
            val remaining = generations - itterations
            val indexOfRemainingPlantLayout = (remaining % env.previousPlantLayouts.size).toInt()

            val plantLayout = env.previousPlantLayouts[indexOfRemainingPlantLayout].first
            val startingIndex = env.change * remaining + env.startingIndex
            env = env.copy(
                plantLayout = plantLayout,
                startingIndex = startingIndex
            )
        }

        internal fun dynamicNext(env: Env): Env {
            val plantLayout = env.plantLayout
            var startingIndex = env.startingIndex
            val plantLayoutBuilder = StringBuilder("..")
            for (i in 0..plantLayout.length - 5) {
                val section = plantLayout.subSequence(i, i + 5)
                val value = rules.firstOrNull { it.pattern == section }
                    ?.result
                    ?: '.'
                plantLayoutBuilder.append(value)
            }

            val nextPlantLayout = plantLayoutBuilder.toString()
                .let {
                    val fIndex = it.indexOf('#')
                    val lIndex = it.lastIndexOf('#')
                    when (fIndex) {
                        -1 -> "....." // startingIndex is fine
                        lIndex -> {
                            startingIndex += (fIndex - 4)
                            "....#...."
                        }
                        else -> {
                            startingIndex += (fIndex - 4)
                            "....${it.substring(fIndex, lIndex + 1)}...."
                        }
                    }
                }

            val ppl = env.previousPlantLayouts
            val index = ppl.indexOfFirst { it.first == nextPlantLayout }
            return if (index == -1) {
                ppl.add(nextPlantLayout to startingIndex)
                Env(
                    plantLayout = nextPlantLayout,
                    startingIndex = startingIndex,
                    previousPlantLayouts = ppl
                )
            } else {
                val plans = mutableListOf<Pair<String, Long>>()
                for (i in index until ppl.size) {
                    plans.add(ppl[i])
                }

                val change = startingIndex - ppl[index].second
                Env(
                    plantLayout = nextPlantLayout,
                    startingIndex = startingIndex,
                    previousPlantLayouts = plans,
                    currentIndex = 0,
                    change = change
                )
            }
        }

        fun plantScore(): Long = env.plantScore()

        override fun toString(): String = env.plantLayout
    }

    private data class Env(
        val plantLayout: String,
        val startingIndex: Long,
        val previousPlantLayouts: MutableList<Pair<String, Long>>,
        val currentIndex: Int = -1,
        val change: Long = 0L
    ) {
        constructor(
            plantLayout: String,
            startingIndex: Long
        ) : this(plantLayout, startingIndex, mutableListOf(plantLayout to startingIndex))

        fun constant(): Boolean = currentIndex != -1

        fun plantScore(): Long {
            var score = 0L
            plantLayout.forEachIndexed { index, c ->
                if (c == '#') score += index + startingIndex
            }
            return score
        }
    }

    private data class PlantRules(
        val pattern: String,
        val result: Char
    )

    private fun List<String>.parseToEnvironment(
        startingIndex: Int
    ): Environment {
        val initialState = firstOrNull()
            ?.takeIf { it.startsWith("initial state: ") }
            ?: throw IllegalArgumentException("No initial state")
        val plantLayout = initialState.substring("initial state: ".length)

        val rules = this
            .filter { it.contains(" => ") }
            .map {
                val (pattern, result) = it.split(" => ", limit = 2)
                PlantRules(pattern, result[0])
            }

        return Environment(plantLayout, rules, startingIndex)
    }
}