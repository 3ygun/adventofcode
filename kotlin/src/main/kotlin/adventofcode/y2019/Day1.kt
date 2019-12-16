package adventofcode.y2019

import adventofcode.DataLoader
import adventofcode.Day
import kotlin.math.floor

object Day1 : Day {
    internal val STAR1: List<Long> = DataLoader.readNonBlankLinesFrom("/y2019/Day1Star1.txt")
        .map { it.toLong() }
    internal val STAR2 = STAR1

    override val day = 1

    override fun star1Run(): String {
        val answer = star1Calc(STAR1)
        return "The fuel for the summed module masses is: $answer"
    }

    override fun star2Run(): String {
        val answer = star2Calc(STAR2)
        return "The fuel for the summed module masses plus fuel is: $answer"
    }

    fun star1Calc(masses: List<Long>): Long {
        return masses
            .map { massToFuel(it) }
            .sum()
    }

    @Suppress("NOTHING_TO_INLINE")
    private inline fun massToFuel(mass: Long): Long = floor(mass / 3.0).toLong() - 2L

    fun star2Calc(masses: List<Long>): Long {
        // Per module calculations
        return masses
            .map { massToFuel(it) } // Inital Fuel mass for each module
            .map { fuelCalculation(it, it) } // Fuel for fuel on each module
            .sum()
    }

    private tailrec fun fuelCalculation(
        newMass: Long,
        fuelSoFar: Long
    ): Long {
        val fuelForFuel = massToFuel(newMass)
        return if (fuelForFuel < 0) fuelSoFar
        else fuelCalculation(fuelForFuel, fuelSoFar + fuelForFuel)
    }
}
