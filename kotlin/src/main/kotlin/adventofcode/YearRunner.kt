package adventofcode

interface YearRunner {
    fun year(): Int

    fun run(
        all: Boolean,
        day: Int
    )
}
