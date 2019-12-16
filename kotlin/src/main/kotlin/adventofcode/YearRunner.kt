package adventofcode

interface YearRunner {
    val year: Int
    val days: Map<Int, Day>

    fun run(
        all: Boolean,
        day: Int
    ) {
        when {
            all -> {
                days.entries.forEach { it.value.print() }
            }
            else -> {
                requireNotNull(days[day]) { "Invalid day $day given for year $year" }
                    .print()
            }
        }
    }
}
