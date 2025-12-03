package adventofcode.y2025

import adventofcode.DataLoader
import adventofcode.Day

// https://adventofcode.com/2025/day/2
object Day2 : Day {
    override val day: Int = 2

    internal val STAR1: String get() = DataLoader.readNonBlankLinesFrom("/y2025/Day2Star1.txt").first()

    /**
     * Star1: 1227775554
     * Star2: 4174379265
     */
    private val EXAMPLE get() = "11-22,95-115,998-1012,1188511880-1188511890,222220-222224,1698522-1698528,446443-446449,38593856-38593862,565653-565659,824824821-824824827,2121212118-2121212124"

    override fun star1Run(): String {
        var sumInvalidIds = 0L

        STAR1.split(",").forEach { range ->
            val (min, max) = range.split("-").map { it.toLong() }
            require(min in 0..max) { "Invalid range: $range" }

            for (id in min..max) {
                val sId = id.toString()
                val digits = sId.length
                if (digits % 2 == 1) continue // Odd number of digits cannot be invalid

                val pairSize = digits / 2
                val isInvalid = sId.subSequence(0, pairSize) == sId.subSequence(pairSize, digits)
                if (isInvalid) {
                    //println("Invalid ID: $id")
                    sumInvalidIds += id
                }
            }
        }

        return "Sum of the invalid IDs: $sumInvalidIds"
    }

    override fun star2Run(): String {
        var sumInvalidIds = 0L

        STAR1.split(",").forEach { range ->
            val (min, max) = range.split("-").map { it.toLong() }
            require(min in 0..max) { "Invalid range: $range" }

            for (id in min..max) {
                val sId = id.toString()
                val digits = sId.length
                if (digits == 1) continue // 1 digit cannot be invalid (it's the only digit)

                val maxPairSize = digits / 2
                val isInvalid = (1..maxPairSize).any { pairSize ->
                    val segments = sId.chunked(pairSize)
                    val firstSegment = segments.first()
                    segments.all { it == firstSegment }
                }
                if (isInvalid) {
                    //println("Invalid ID: $id")
                    sumInvalidIds += id
                }
            }
        }

        return "Sum of the invalid IDs (new rules): $sumInvalidIds"
    }
}
