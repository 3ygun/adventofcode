package adventofcode.y2018

import adventofcode.Day
import adventofcode.utils.MaxAndIndex
import adventofcode.utils.maxValueAndIndex

object Day9 : Day {
    const val STAR1_DATA = "466 players; last marble is worth 71436 points"
    const val STAR2_DATA = "466 players; last marble is worth 7143600 points"

    private val LINE_REGEX = Regex("(?>(\\d+) players; last marble is worth (\\d*) points)")

    override val day: Int = 9

    override fun star1Run(): String {
        val result = star1Calc(STAR1_DATA)
        return "highest score: $result"
    }

    override fun star2Run(): String {
        val result = star1Calc(STAR2_DATA)
        return "highest score: $result"
    }

    fun star1Calc(rawInput: String): Long {
        val gameConfig = parse(rawInput)

        return Day9Game.run(gameConfig)
            .highestScore()
            .max
    }

    private fun parse(input: String): Day9GameConfig {
        val (_, players, lastMarble) = LINE_REGEX.matchEntire(input)?.groupValues
            ?.takeIf { it.size == 3 }
            ?: throw IllegalArgumentException("The following line wasn't valid: '$input'")

        return Day9GameConfig(
            players = players.toInt(),
            lastMarble = lastMarble.toInt()
        )
    }

    private data class Day9GameConfig(
        val players: Int,
        val lastMarble: Int
    )

    private class Day9Game private constructor(
        private val playerScores: LongArray
    ) {
        fun highestScore(): MaxAndIndex<Long> = playerScores.maxValueAndIndex()

        companion object {
            @JvmStatic
            fun run(config: Day9GameConfig): Day9Game {
                val maxPlayers = config.players
                val lastMarble = config.lastMarble
                val playerScores = LongArray(maxPlayers)
                // 1st marble, Avoids divide by zero because board size always > 0
                val board = Circle(Node(0))
                debug { board.toString() }

                for (marble in 1..lastMarble) {
                    if (marble % 23 == 0) {
                        val player = marble % maxPlayers
                        val popped = board.counterClockwise(7).pop().value
                        playerScores[player] = playerScores[player] + popped + marble
                    } else {
                        board.clockwise().push(Node(marble))
                    }

                    debug { board.toString() }
                }

                return Day9Game(playerScores)
            }

            private class Circle(
                node: Node // have to have 1 and don't check well
            ) {
                private var first: Node = node // This could be broken later
                private var current: Node = node.linkToSelf()

                var size: Int = 1
                    private set

                fun counterClockwise(amount: Int = 1): Circle {
                    repeat(amount) { current = current.counterClockwise ?: current }
                    return this
                }

                fun clockwise(amount: Int = 1): Circle {
                    repeat(amount) { current = current.clockwise ?: current }
                    return this
                }

                /**
                 * Will make this the current node
                 */
                fun push(node: Node): Circle {
                    node.clockwise = current.clockwise
                    node.counterClockwise = current
                    current.clockwise!!.counterClockwise = node
                    current.clockwise = node

                    current = node
                    size++
                    return this
                }

                /**
                 * Assigns the new current to [current.clockwise]
                 *
                 * @return the [current] value
                 */
                fun pop(): Node {
                    // I didn't care about empty Circles
                    if (current == current.clockwise) IllegalArgumentException("Cannot pop only element")

                    val result = current
                    current.counterClockwise!!.clockwise = current.clockwise
                    current.clockwise!!.counterClockwise = current.counterClockwise
                    current = current.clockwise!!
                    size--

                    // Sanitize
                    result.counterClockwise = null
                    result.clockwise = null
                    return result
                }

                override fun toString(): String {
                    val builder = StringBuilder()
                        .append(first)

                    tailrec fun walk(next: Node): String = when {
                        (next == first) -> builder.toString()
                        else -> {
                            builder.append(' ').append(next)
                            walk(next.clockwise!!)
                        }
                    }

                    return walk(first.clockwise!!)
                }
            }

            private data class Node(
                val value: Int,
                /** Left */
                var counterClockwise: Node? = null,
                /** Right */
                var clockwise: Node? = null
            ) {
                fun linkToSelf(): Node = apply {
                    counterClockwise = this
                    clockwise = this
                }

                override fun toString(): String = value.toString()
            }
        }
    }
}
