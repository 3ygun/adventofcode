package adventofcode.y2019

import adventofcode.Day

object Day4 : Day {
    internal val STAR1 = 248345 to 746315
    internal val STAR2 = STAR1

    override val day = 4
    override val debug = true

    // <editor-fold desc="Star 1">

    override fun star1Run(): String {
        val (from, to) = STAR2
        val result = star1Calc(from, to)
        return "The number of different password combinations for $from-$to is: $result"
    }

    internal fun star1Calc(from: Int, to: Int): Int {
        require(from <= to) { "Error: `from > to` with $from > $to" }

        if (to < 11) return 0
        if (from == to) return if (meetsStar1Criteria(from)) 1 else 0

        var count = 0
        for (i in from..to) {
            if (meetsStar1Criteria(i)) count++
        }
        return count
    }

    private fun meetsStar1Criteria(num: Int): Boolean {
        val digitsRO = expandNumber(num)
        if (digitsRO.size < 2) return false

        var consecutiveMatching = false
        for (i in 0 until (digitsRO.size - 1)) {
            val current = digitsRO[i]
            val next = digitsRO[i+1]

            if (current < next) return false // Not always increasing
            if (current == next) consecutiveMatching = true
        }
        return consecutiveMatching
    }

    // </editor-fold>
    // <editor-fold desc="Star 2">

    override fun star2Run(): String {
        val (from, to) = STAR2
        val result = star2Calc(from, to)
        return "The number of different password combinations for $from-$to is: $result"
    }

    internal fun star2Calc(from: Int, to: Int): Int {
        require(from <= to) { "Error: `from > to` with $from > $to" }

        if (to < 11) return 0
        if (from == to) return if (meetsStar2Criteria(from)) 1 else 0

        var count = 0
        for (i in from..to) {
            if (meetsStar2Criteria(i)) count++
        }
        return count
    }

    private fun meetsStar2Criteria(num: Int): Boolean {
        val digitsRO = expandNumber(num)
        if (digitsRO.size < 2) return false

        var consecutiveNumbers: Int = 1
        var consecutiveMatching: Boolean = false
        for (i in 0 until (digitsRO.size - 1)) {
            val current = digitsRO[i]
            val next = digitsRO[i+1]

            if (current < next) return false // Not always increasing
            if (current == next) {
                consecutiveNumbers ++
            } else {
                if (consecutiveNumbers == 2) consecutiveMatching = true
                consecutiveNumbers = 1
            }
        }
        return consecutiveMatching || consecutiveNumbers == 2 // Catch if the final numbers match
    }

    // </editor-fold>
    // <editor-fold desc="Common">

    private tailrec fun expandNumber(
        n: Int,
        expandedReverseOrder: MutableList<Int> = ArrayList(12)
    ): List<Int> {
        if (n <= 0) return expandedReverseOrder

        val digit = n % 10
        val newN = n / 10
        expandedReverseOrder.add(digit)
        return expandNumber(newN, expandedReverseOrder)
    }

    // </editor-fold>
}
