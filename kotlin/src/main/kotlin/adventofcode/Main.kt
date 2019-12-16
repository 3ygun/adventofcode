package adventofcode

import adventofcode.y2018.Year2018
import kotlin.IllegalArgumentException

fun main(args: Array<String>) {
    try {
        val config = parseConfig(args)

        // Attempt to launch the year's runner
        years[config.year]
            ?.run(config.all, config.day)
            ?: throw IllegalArgumentException("Invalid year ${config.year} valid years are: [$years]")
    } catch (e: Exception) {
        println("""
            ${e.printStackTrace()}

            Failed with:
                $e

            Proper usage is either:
                ./program -year YEAR -all
                or
                ./program -year YEAR -day DAY
                or
                gradle run --args='-year YEAR -all'
                or
                gradle run --args='-year YEAR -day DAY'
                

            Arguments
                -year YEAR
                    Specify the year to run
                    (ex: '-year y2018')

                -all
                    Specify that all tests within the year should be run

                -day DAY
                    Specify the day within the year to run
                    (ex: '-day 1')
        """.trimIndent())
        return // End in error
    }
}

private val years = mapOf<Int, YearRunner>(
    Year2018.year() to Year2018
)

private data class Config(
    val year: Int,
    val all: Boolean,
    val day: Int
)


private fun parseConfig(args: Array<String>): Config {
    val yearIndex = args.indexOf("-year")
    if (yearIndex == -1 || yearIndex == args.size - 1)
        throw IllegalArgumentException("No valid '-year YEAR' argument specified")

    val year = args[yearIndex + 1].toInt() // Get the year

    val all = args.contains("-all") // All?
    val day = if (all) -1 else getDay(args)

    return Config(year, all, day)
}

private fun getDay(args: Array<String>): Int {
    val index = args.indexOf("-day")
    return if (index == -1 || index == args.size -1) {
        throw IllegalArgumentException("No '-all' or '-day DAY' argument specified")
    } else {
        args[index + 1].toInt()
    }
}
