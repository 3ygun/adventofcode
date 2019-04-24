package adventofcode.y2018

import adventofcode.Day
import adventofcode.utils.MaxAndIndex
import adventofcode.utils.maxValueAndIndex
import kotlin.collections.ArrayList

object Day9 : Day {
    const val STAR1_DATA = "466 players; last marble is worth 71436 points"
    const val STAR2_DATA = "466 players; last marble is worth 71436000 points"

    private val LINE_REGEX = Regex("(?>(\\d+) players; last marble is worth (\\d*) points)")

    override val day: Int = 9

    override fun star1Run(): String {
        val result = star1Calc(STAR1_DATA)
        return "highest score: $result"
    }

    override fun star2Run(): String {
        // val result = star1Calc(STAR2_DATA)
        // return "highest score: $result"
        return "I'm too slow currently"
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
                val board = ArrayList<Int>(lastMarble)
                    .also { it.add(0) } // 1st marble, Avoids divide by zero because board size always > 0

                tailrec fun calculate(
                    currentMarbleIndex: Int,
                    player: Int,
                    index: Int
                ): Day9Game {
                    if (index >= lastMarble) return Day9Game(playerScores)
                    var nextMarbleIndex = currentMarbleIndex
                    var nextPlayer = player
                    var nextMarble = index

                    for (i in index..Math.min(index + 21, lastMarble)) {
                        nextMarble = i
                        nextMarbleIndex = ((nextMarbleIndex + 1) % board.size) + 1
                        board.add(nextMarbleIndex, nextMarble)

                        // Set next player
                        nextPlayer = (nextPlayer + 1) % maxPlayers
                    }
                    if (nextMarble == lastMarble) return Day9Game(playerScores)

                    var currentScore = playerScores[player]
                    nextMarble++
                    currentScore += nextMarble // Add the marble
                    nextMarble++
                    val poppedIndex = poppedIndex(nextMarbleIndex, board.size)
                    val popped = board.removeAt(poppedIndex)
                    currentScore += popped

                    playerScores[player] = currentScore
                    nextMarbleIndex = poppedIndex // Because this will always be a valid next insert
                    nextPlayer = (nextPlayer + 1) % maxPlayers

                    return calculate(
                        nextMarbleIndex,
                        nextPlayer,
                        nextMarble
                    )
                }

                return calculate(
                    currentMarbleIndex = 1,
                    player = 1,
                    index = 1
                )
            }

            private inline fun poppedIndex(
                currentMarbleIndex: Int,
                size: Int
            ): Int {
                val toRemove = currentMarbleIndex - 7
                return when {
                    toRemove < 0 -> size + toRemove
                    else -> toRemove
                }
            }
        }
    }
}
