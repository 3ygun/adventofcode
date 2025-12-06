package adventofcode.y2025

import adventofcode.DataLoader
import adventofcode.Day
import kotlin.math.min

object Day6 : Day {
    override val day = 6
    override val debug: Boolean get() = false

    internal val STAR1: List<String> get() = DataLoader.readLinesFromFor("/y2025/Day6Star1.txt")

    /**
     * Star1: 4277556
     * Star2: 3263827
     */
    private val EXAMPLE: List<String> get() = let {
        "" +
                "123 328  51 64 \n" +
                " 45 64  387 23 \n" +
                "  6 98  215 314\n" +
                "*   +   *   +  \n" +
                ""
    }.split("\n")

    override fun star1Run(): String {
        val lines = STAR1.filter(String::isNotBlank).reversed()
        val worksheet = mutableListOf<Start1OperationAction>()
        lines.first().let { operations ->
            operations.forEach { operation ->
                when (operation) {
                    '*' -> MultipliedStart1OperationAction().let(worksheet::add)
                    '+' -> AddStart1OperationAction().let(worksheet::add)
                    ' ' -> {}
                    else -> throw IllegalArgumentException("Unexpected operation: $operation")
                }
            }
        }

        val whitespace = Regex("\\W+")
        lines.forEachIndexed { lineIndex, line ->
            if (lineIndex == 0) return@forEachIndexed

            if (debug) println(line.split(whitespace))

            line.trim().split(whitespace).forEachIndexed { index, num ->
                require(num.isNotBlank()) { "Expected non-blank number, got '$line'" }
                val number = num.toLong()
                worksheet[index].apply(number)
            }
        }

        if (debug) {
            worksheet.forEach {
                it.println()
            }
        }

        val result = worksheet.sumOf { it.total }
        return "Grand total: $result"
    }

    interface Start1OperationAction {
        val total: Long
        fun println()
        fun apply(value: Long)
    }

    class AddStart1OperationAction(
        private var _total: Long = 0,
        private var operation: MutableList<Long> = mutableListOf(),
    ) : Start1OperationAction {
        override val total get() = _total
        override fun println() {
            println("${operation.joinToString(" + ")} = $total")
        }
        override fun apply(value: Long) {
            if (debug) operation.add(value)
            _total += value
        }
    }

    class MultipliedStart1OperationAction(
        private var _total: Long = 1,
        private var operation: MutableList<Long> = mutableListOf(),
    ) : Start1OperationAction {
        override val total get() = _total
        override fun println() {
            println("${operation.joinToString(" * ")} = $total")
        }
        override fun apply(value: Long) {
            if (debug) operation.add(value)
            _total *= value
        }
    }

    override fun star2Run(): String {
        val rawlines = STAR1.filter(String::isNotBlank)
        if (debug) {
            println()
            rawlines.forEach { println("\"$it\"") }
        }
        val lines = rawlines.map { it.reversed() }
        if (debug) {
            println()
            lines.forEach { println("\"$it\"") }
        }

        val worksheets = mutableListOf<Start2OperationAction>()
        lines.last().let { operations ->
            var digits = 0
            operations.forEach { operation ->
                when (operation) {
                    '*' -> MultipliedStart2OperationAction(digits + 1).let(worksheets::add).run { digits = -1 }
                    '+' -> AddStart2OperationAction(digits + 1).let(worksheets::add).run { digits = -1}
                    ' ' -> digits++
                    else -> throw IllegalArgumentException("Unexpected operation: $operation")
                }
            }
        }

        lines.forEachIndexed { lineIndex, line ->
            if (line == lines.last()) return@forEachIndexed

            var startingIndex = 0
            worksheets.forEach { worksheet ->
                val end = min(line.length, startingIndex + worksheet.digits)
                val num = line.substring(startingIndex, end)
                worksheet.apply(num)
                startingIndex += worksheet.digits + 1
            }
        }

        if (debug) {
            println()
            worksheets.forEach { it.printlnNumbers() }
        }

        if (debug) println()
        val result = worksheets.sumOf { it.process() }
        if (debug) println()
        return "Grand total: $result"
    }

    interface Start2OperationAction {
        val digits: Int
        fun process(): Long
        fun printlnNumbers()
        fun apply(value: String)
    }

    class AddStart2OperationAction(
        override val digits: Int,
    ) : Start2OperationAction {
        private val numbers: MutableList<String> = mutableListOf()
        override fun printlnNumbers() {
            println("+ with input $numbers")
        }
        override fun process(): Long {
            val trueNumbers = mutableListOf<String>()
            var total = 0L
            (0 until digits).forEach { digit ->
                val num = StringBuilder()
                numbers.forEach {
                    val char = it[digit]
                    if (char != ' ') num.append(char)
                }
                if (debug) trueNumbers.add(num.toString())
                total += num.toString().toLong()
            }

            if (debug) println("${trueNumbers.joinToString(" + ")} = $total")
            return total
        }
        override fun apply(value: String) {
            numbers.add(value)
        }
    }

    class MultipliedStart2OperationAction(
        override val digits: Int,
    ) : Start2OperationAction {
        private val numbers: MutableList<String> = mutableListOf()
        override fun printlnNumbers() {
            println("* with input $numbers")
        }
        override fun process(): Long {
            val trueNumbers = mutableListOf<String>()
            var total = 1L
            (0 until digits).forEach { digit ->
                val num = StringBuilder()
                numbers.forEach {
                    val char = it[digit]
                    if (char != ' ') num.append(char)
                }
                if (debug) trueNumbers.add(num.toString())
                total *= num.toString().toLong()
            }

            if (debug) println("${trueNumbers.joinToString(" * ")} = $total")
            return total
        }
        override fun apply(value: String) {
            numbers.add(value)
        }
    }
}
