package adventofcode

import kotlin.system.measureNanoTime

interface Day {
    val day: Int

    fun star1() = runner(this::star1Run, 1)

    fun star2() = runner(this::star2Run, 2)

    fun star1Run(): String

    fun star2Run(): String

    private fun runner(
        func: () -> String,
        star: Int
    ) {
        var answer = ""
        val took = measureNanoTime {
            answer = func()
        }
        println("Day $day, Star $star took ${took / 1_000_000.0} ms for answer: $answer")
    }
}
