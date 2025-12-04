package adventofcode.y2025

import adventofcode.DataLoader
import adventofcode.Day

object Day4 : Day {
    override val day: Int = 4

    internal val STAR1: List<String> get() = DataLoader.readNonBlankLinesFrom("/y2025/Day4Star1.txt")

    /**
     * Star1: 13
     * Star2: 43
     */
    private val EXAMPLE get() = """
        ..@@.@@@@.
        @@@.@.@.@@
        @@@@@.@.@@
        @.@@@@..@.
        @@.@@@@.@@
        .@@@@@@@.@
        .@.@.@.@@@
        @.@@@.@@@@
        .@@@@@@@@.
        @.@.@@@.@.
    """.trimIndent().lines()

    private fun printVisual(line: String, numLines: Int) {
        line.also { println() }.chunked(numLines).forEach { println(it) }.run { println() }
    }

    override fun star1Run(): String {
        val input = STAR1
        val numLines = input.size
        input.forEach { assert(it.length == numLines) }
        val matrix = input.joinToString("")
        val visual = StringBuilder()

        fun isRollAtPoint(index: Int, rowDirection: Int, colDirection: Int): Int {
            val maxIndex = (index + (colDirection) + (rowDirection*numLines))
            val colIndex = index.mod(numLines)
            return when {
                maxIndex < 0 -> 0
                maxIndex >= matrix.length -> 0
                (colIndex+colDirection) < 0 -> 0
                (colIndex+colDirection) >= numLines -> 0
                matrix[index+(colDirection)+(rowDirection*numLines)] == '@' -> 1
                else -> 0
            }
        }
        fun forward(index: Int) = isRollAtPoint(index, 0, 1)
        fun backward(index: Int) = isRollAtPoint(index, 0, -1)
        fun top(index: Int) = isRollAtPoint(index, -1, 0)
        fun bottom(index: Int) = isRollAtPoint(index, 1, 0)

        fun diagonal(index: Int, rowDirection: Int, colDirection: Int): Int = isRollAtPoint(index, rowDirection, colDirection)
        fun diagonalUpLeft(index: Int) = diagonal(index, -1, -1)
        fun diagonalUpRight(index: Int) = diagonal(index, -1, 1)
        fun diagonalDownLeft(index: Int) = diagonal(index, 1, -1)
        fun diagonalDownRight(index: Int) = diagonal(index, 1, 1)

        var accessibleRolls = 0
        matrix.forEachIndexed { index, c ->
            if (c != '@') {
                visual.append('.')
                return@forEachIndexed
            }

            var rollsAround = 0
            rollsAround += forward(index)
            rollsAround += backward(index)
            rollsAround += top(index)
            rollsAround += bottom(index)
            rollsAround += diagonalUpLeft(index)
            rollsAround += diagonalUpRight(index)
            rollsAround += diagonalDownLeft(index)
            rollsAround += diagonalDownRight(index)

            if (rollsAround < 4) {
                visual.append('X')
                accessibleRolls++
            } else {
                visual.append('@')
            }
        }

//        printVisual(visual.toString(), numLines)

        return "Number of accessible rolls: $accessibleRolls"
    }

    override fun star2Run(): String {
        val input = STAR1
        val numLines = input.size
        input.forEach { assert(it.length == numLines) }
        val originalMatrix = input.joinToString("").let { line ->
            val result = CharArray(line.length)
            line.forEachIndexed { index, c -> result[index] = c }
            result
        }

        val matrix = originalMatrix.copyOf()
        fun isRollAtPoint(index: Int, rowDirection: Int, colDirection: Int): Int {
            val maxIndex = (index + (colDirection) + (rowDirection*numLines))
            val colIndex = index.mod(numLines)
            return when {
                maxIndex < 0 -> 0
                maxIndex >= matrix.size -> 0
                (colIndex+colDirection) < 0 -> 0
                (colIndex+colDirection) >= numLines -> 0
                matrix[index+(colDirection)+(rowDirection*numLines)] == '@' -> 1
                else -> 0
            }
        }
        fun forward(index: Int) = isRollAtPoint(index, 0, 1)
        fun backward(index: Int) = isRollAtPoint(index, 0, -1)
        fun top(index: Int) = isRollAtPoint(index, -1, 0)
        fun bottom(index: Int) = isRollAtPoint(index, 1, 0)

        fun diagonal(index: Int, rowDirection: Int, colDirection: Int): Int = isRollAtPoint(index, rowDirection, colDirection)
        fun diagonalUpLeft(index: Int) = diagonal(index, -1, -1)
        fun diagonalUpRight(index: Int) = diagonal(index, -1, 1)
        fun diagonalDownLeft(index: Int) = diagonal(index, 1, -1)
        fun diagonalDownRight(index: Int) = diagonal(index, 1, 1)

        var previouslyAccessibleRolls = -1
        var accessibleRolls = 0
        var visual = StringBuilder()
        while (previouslyAccessibleRolls != accessibleRolls) {
            previouslyAccessibleRolls = accessibleRolls
            visual = StringBuilder()
            matrix.forEachIndexed { index, c ->
                if (c != '@') {
                    visual.append('.')
                    return@forEachIndexed
                }

                var rollsAround = 0
                rollsAround += forward(index)
                rollsAround += backward(index)
                rollsAround += top(index)
                rollsAround += bottom(index)
                rollsAround += diagonalUpLeft(index)
                rollsAround += diagonalUpRight(index)
                rollsAround += diagonalDownLeft(index)
                rollsAround += diagonalDownRight(index)

                if (rollsAround < 4) {
                    visual.append('X')
                    accessibleRolls++
                    matrix[index] = '.'
                } else {
                    visual.append('@')
                }
            }

//            printVisual(visual.toString(), numLines)
        }

//        printVisual(String(matrix), numLines)

        return "Number of accessible rolls (with removals): $accessibleRolls"
    }
}

