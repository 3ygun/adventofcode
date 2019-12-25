package adventofcode.y2019

import adventofcode.DataLoader
import adventofcode.Day

object Day2 : Day {
    internal val STAR1 = DataLoader.readNonBlankLinesFrom("/y2019/Day2Star1.txt")
        .first()
        .parseInputLineStar1()
        .applyNounVerb(noun = 12, verb = 2)
        .joinToString(separator = ",")
    internal val STAR2 = DataLoader.readNonBlankLinesFrom("/y2019/Day2Star1.txt")
        .first()
        // Will apply the nouns and verbs later

    override val day = 2
    override val debug = false

    // <editor-fold desc="Star 1">

    override fun star1Run(): String {
        val result = star1Calc(STAR1)
        return "Result of the Intcode computer is: $result"
    }

    internal fun star1Calc(rawInput: String): Int {
        val input = rawInput.parseInputLineStar1()
        debug { "Given input: $input" }
        return intcodeComputer(input)
    }

    private fun String.parseInputLineStar1(): MutableList<Int> = this
        .split(",")
        .map { it.toInt() }
        .toMutableList()

    // </editor-fold>
    // <editor-fold desc="Star 2">

    override fun star2Run(): String {
        val forValue = 19690720
        val result = star2Calc(STAR2, forValue)
        return "For intcode to compute value $forValue the result of `(100 * noun) + verb` was: $result"
    }

    internal fun star2Calc(
        rawInput: String,
        expectedResult: Int
    ): Int {
        var i = 0
        val input = rawInput.parseInputLineStar1().toList()
        noun@for (noun in 0..99) {
            verb@for (verb in 99 downTo 0) {
                i ++
                val result = star2Operation(input, noun, verb)
                debug { "Computing for noun: $noun, verb: $verb, got result: $result" }
                if (expectedResult == result){
                    debug { "Interations to find was: $i" }
                    return (100 * noun) + verb
                }

                // Optimizations to remove search cases
                //
                // Why can we do this?
                //   Because the intcode computer is always addition or multiplication without
                //   negative numbers we know that a larger noun and verb will always produce
                //   a larger number if they are larger. Therefore find the upper bound noun 1st!
                //
                // Does it work?
                //   For the Star 1 case went from 1203 to 110 iterations for noun=12, verb=2
                if (result < expectedResult) continue@noun
            }
        }

        debug { "Iterations searched was: $i" }
        throw IllegalStateException("Could not find the expectedResult: $expectedResult")
    }

    private fun star2Operation(
        input: List<Int>,
        noun: Int,
        verb: Int
    ): Int {
        val inputWithNounVerb = input
            .toMutableList()
            .applyNounVerb(noun, verb)
        return intcodeComputer(inputWithNounVerb)
    }

    // </editor-fold>
    // <editor-fold desc="Common">

    private fun MutableList<Int>.applyNounVerb(noun: Int, verb: Int): MutableList<Int> = apply {
        this[1] = noun
        this[2] = verb
    }

    private tailrec fun intcodeComputer(
        input: MutableList<Int>,
        position: Int = 0
    ): Int {
        val opcode = input[position]
        return when (opcode) {
            1 -> { // addition
                val a = input[position + 1]
                val b = input[position + 2]
                val store = input[position + 3]
                input[store] = input[a] + input[b]
                intcodeComputer(input, position + 4)
            }
            2 -> { // multiplication
                val a = input[position + 1]
                val b = input[position + 2]
                val store = input[position + 3]
                input[store] = input[a] * input[b]
                intcodeComputer(input, position + 4)
            }
            99 -> { // exit
                debug { "Intcode computer result : ${input.toList()}" }
                input.first()
            }
            else -> throw IllegalStateException("Hit opcode: [$opcode] at position: $position of input: $input")
        }
    }

    // </editor-fold>
}
