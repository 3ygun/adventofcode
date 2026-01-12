package adventofcode.y2025

import adventofcode.DataLoader
import adventofcode.Day

object Day10 : Day {
    override val day: Int = 10
    override val debug: Boolean get() = true

    internal val STAR1 get() = DataLoader.readNonBlankLinesFrom("/y2025/Day10Star1.txt")

    private val EXAMPLE
        get() = """
        [.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}
        [...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}
        [.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}
    """.trimIndent().lines()

    override fun star1Run(): String {
        // 2 + 3 + 2 = 7
        val lines = EXAMPLE

        val machines = lines.map { Star1Machine.from(it) }
        if (debug) println("machines: [\n${machines.joinToString(",\n")}\n]")

        val minimums = machines.map { it.calculateButtonMinimum() }

        return "Minimum button presses: ${minimums.sum()} (from $minimums)"
    }

    class Star1Machine(
        val size: Int,
        val desiredIndicatorLight: Int,
        val wiringButtons: List<Int>,
        val joltageRequirements: List<Int>,
    ) {
        companion object {
            fun from(line: String): Star1Machine {
                val sDesiredIndicatorLight = "\\[([.#]*)\\].*".toRegex().matchEntire(line)!!.groups[1]!!.value
                val sWiringButtons = ".*\\] (.*) \\{.*".toRegex().matchEntire(line)!!.groups[1]!!.value
                val sJoltageRequirements = ".*\\{((\\d+,?)*)\\}".toRegex().matchEntire(line)!!.groups[1]!!.value
                if (debug) println("sDesiredIndicatorLight: $sDesiredIndicatorLight, sWiringButtons: $sWiringButtons, sJoltageRequirements: $sJoltageRequirements")

                val desiredIndicatorLight = sDesiredIndicatorLight.fold(0) { last, c ->
                    when (c) {
                        '.' -> last shl 1
                        '#' -> last shl 1 or 1
                        else -> throw IllegalArgumentException("Illegal character for desired indicator light: $c of line: $line")
                    }
                }
                val size = sDesiredIndicatorLight.length

                val wiringButtons: List<Int> = sWiringButtons.split(" ").map { sWiringButton ->
                    require(sWiringButton.startsWith("(")) { "Invalid wiring button: $sWiringButton want start with '(' of line: $line" }
                    require(sWiringButton.endsWith(")")) { "Invalid wiring button: $sWiringButton want end with ')' of line: $line" }

                    val nums = sWiringButton.substring(1).substringBefore(')').split(",").map { it.toInt() }
                    var i = 0
                    var result = nums.fold(0) { acc, num ->
                        var result = acc
                        while (i < num) {
                            result = result shl 1
                            i++
                        }
                        result or 1
                    }
                    while (i < (size - 1)) {
                        result = result shl 1
                        i++
                    }
                    result
                }

                val joltageRequirements = sJoltageRequirements.split(",").map { it.toInt() }

                return Star1Machine(
                    size = size,
                    desiredIndicatorLight = desiredIndicatorLight,
                    wiringButtons = wiringButtons,
                    joltageRequirements = joltageRequirements,
                )
            }
        }

        private fun Int.toBinary(): String = this.toString(radix = 2).padStart(size, '0')

        override fun toString(): String {
            val wb = wiringButtons.joinToString(",") { it.toBinary() }
            return "Star1Machine(desiredIndicatorLight = $desiredIndicatorLight = ${desiredIndicatorLight.toBinary()}, wiringButtons = $wb, joltageRequirements = $joltageRequirements, wiringButtonsAsNumbers = $wiringButtons)"
        }

        private var checksDone = 0

        private fun decendAndCheck(
            maxDepth: Int,
            currentDepth: Int,
            indicatorLight: Int,
            skipButton: Int = 0,
        ): MutableList<Int>? {
            for (button in wiringButtons) {
                if (button == skipButton) {
                    // Would just be reversing previous operation
                    // Mainly doing to "tree skip" if depth > 2
                    continue
                }

                checksDone++
                val newIndicatorLight = indicatorLight xor button
                if (currentDepth < maxDepth) {
                    val result = decendAndCheck(
                        maxDepth = maxDepth,
                        currentDepth = currentDepth + 1,
                        indicatorLight = newIndicatorLight,
                        skipButton = button,
                    ) ?: continue
                    result.add(button)
                    return result
                } else if (newIndicatorLight == desiredIndicatorLight) {
                    return mutableListOf(button)
                }
                // look at the next option
            }
            return null
        }

        fun calculateButtonMinimum(): Int {
            require (0 != desiredIndicatorLight) { "Unset desired indicator lights are not supported." }

            checksDone = 0
            var solution = emptyList<Int>()
            var depth = 1
            while (depth <= 100) {
                val result = decendAndCheck(
                    maxDepth = depth,
                    currentDepth = 1,
                    indicatorLight = 0,
                    skipButton = 0,
                )
                if (result != null) {
                    solution = result.toList().reversed()
                    break
                }
                depth++
            }

            require(solution.isNotEmpty()) { "No solution found after $depth checking $checksDone combinations for machine: $this" }

            if (debug) println("For machine: $this pressing ${solution.size} buttons solves it (specifically: ${solution.map { it.toBinary() }})")
            return solution.size
        }
    }

    override fun star2Run(): String {
        return ""
    }
}