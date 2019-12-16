package adventofcode

import kotlin.system.measureNanoTime

interface Day {
    val day: Int

    fun star1Run(): String

    fun star2Run(): String

    fun print() {
        fun runner(
            star: Int,
            func: () -> String
        ) {
            var answer = ""
            val took = measureNanoTime {
                answer = func()
            }
            println("Day $day, Star $star took ${took / 1_000_000.0} ms for answer: $answer")
        }

        runner(1) { star1Run() }
        runner(2) { star2Run() }
    }
}
