package adventofcode.y2025

import adventofcode.DataLoader
import adventofcode.Day

// https://adventofcode.com/2025/day/2
object Day3 : Day {
    override val day: Int = 3

    internal val STAR1: List<String> get() = DataLoader.readNonBlankLinesFrom("/y2025/Day3Star1.txt")

    /**
     * Star1: 357
     * Star2: 3121910778619
     */
    private val EXAMPLE get() = """
        987654321111111
        811111111111119
        234234234234278
        818181911112111
    """.trimIndent().lines()

    override fun star1Run(): String {
        var totalJoltage = 0L

        STAR1.forEach { sBank ->
            var currentMax = 0L
            val bank = sBank.map { it.toString().toInt() }.toIntArray()
            for (i in bank.indices) {
                val iJoltage = bank[i] * 10

                // optimization
                if (currentMax >= (iJoltage+10)) {
                    // This means that nothing this joltage can "combine with" will make it greater then the max joltage
                    continue
                }

                for (j in (i+1) until bank.size) {
                    val jJoltage = bank[j]
                    val joltage = iJoltage + jJoltage
                    if (currentMax < joltage) currentMax = joltage + 0L
                }
            }

//            println("Max joltage for $sBank is $currentMax")
            totalJoltage += currentMax
        }

        return "Total Joltage: $totalJoltage"
    }

    private tailrec fun calculateNextJoltageLevel(
        bank: LongArray,
        digit: Int,
        startIndex: Int,
        currentStrength: Long,
    ): Long {
        if (digit == 0) return currentStrength

        var battery = 0L
        var batteryIndex = 0
        for (i in startIndex until (bank.size - digit + 1)) {
            val purposedBattery = bank[i]
            if (battery < purposedBattery) {
                battery = purposedBattery
                batteryIndex = i
                if (battery == 9L) break // We already have the best battery
            }
        }

        val stength = (1 until digit).fold(battery) { sum, _ -> sum * 10L }
        return calculateNextJoltageLevel(bank, digit - 1, batteryIndex + 1, currentStrength + stength)
    }

    override fun star2Run(): String {
        var totalJoltage = 0L

        STAR1.forEach { sBank ->
            val bank = sBank.map { it.toString().toLong() }.toLongArray()

            val currentMax = calculateNextJoltageLevel(bank, 12, 0, 0L)

//            println("Max joltage for $sBank is $currentMax")
            totalJoltage += currentMax
        }

        return "Total Joltage (of 12 batteries): $totalJoltage"
    }
}
