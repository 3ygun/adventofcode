package adventofcode.codeforces

object KotlinHeroesPractice3 {
    // <editor-fold desc="Problem A - Restoring Three Numbers">

    /*
    fun main() {
        val inputs = readLine()!!
            .split(regex = " ".toRegex())
            .map { it.toLong() }
            .toSet()

        val (a, b, c) = problemA(inputs)
        println("$a $b $c")
    }
     */
    /** https://codeforces.com/contest/1298/problem/A */
    fun problemA(
        x1: Long,
        x2: Long,
        x3: Long,
        x4: Long
    ): List<Long> {
        val inputs = setOf(x1, x2, x3, x4)
        return problemA(inputs)
            .also { println(it) }
            .toList()
    }

    /**
     * Inputs will always have size >= 2 with the max and second highest number being there.
     * Why? Because max will always be at least +1 greater then the second highest because it will
     * be generate from a + b + c which all need to be positive (> 0)
     */
    private fun problemA(inputs: Set<Long>): Triple<Long, Long, Long> {
        val max = inputs.max()!!
        val inputsWithoutMax = inputs.filterNot { it == max }.toSet()
        val secondHighest = inputsWithoutMax.max()!!
        val min = inputsWithoutMax.min()!!

        // a >= b >= c
        val c = max - secondHighest
        val b = min - c
        val a = max - c - b

        return Triple(a, b, c)
    }

    // </editor-fold>
    // <editor-fold desc="Problem B - Remove Duplicates">

    /*
    fun main() {
        val numInputs = readLine()!!
        val inputs = readLine()!!

        val (numResults, result) = problemB(numInputs, inputs)
        println(numResults)
        println(result)
    }
     */
    /** https://codeforces.com/contest/1298/problem/B */
    fun problemB(
        numInputs: String,
        input: String
    ): Pair<String, String> {
        val expectedNum = numInputs.toInt()
        val parsed = input
            .split(regex = " ".toRegex())
            .map(String::toInt)
        require(parsed.size == expectedNum) { "Didn't parse input correctly: '$input' got: $parsed" }

        var x = 0
        val result = parsed
            .associateBy({ it }) { val r = x; x ++; r }
            .toList()
            .sortedBy { it.second }
            .map { it.first }
        val num = result.size
        val printed = result.joinToString(separator = " ")
        return num.toString() to printed
    }

    // </editor-fold>
    // <editor-fold desc="Problem C - File Name">

    /*
    fun main() {
        val numInputs = readLine()!!
        val inputs = readLine()!!

        val result = problemC(inputs)
        println(result)
    }
     */
    /** https://codeforces.com/contest/1298/problem/C */
    fun problemC(input: String): Int {
        var removed = 0
        val array = input.toCharArray()
        for (i in 0 until (array.size - 2)) {
            if ('x' == array[i] &&
                    'x' == array[i+1] &&
                    'x' == array[i+2]) {
                removed ++
            }
        }
        return removed
    }

    // </editor-fold>
}
