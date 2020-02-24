package adventofcode.codeforces

object KotlinHeroesPractice3 {
    /**
     * https://codeforces.com/contest/1298/problem/A
     */
    fun problemA(
        x1: Long,
        x2: Long,
        x3: Long,
        x4: Long
    ): List<Long> {
        /*
        fun main() {
            val inputs = readLine()!!
                .split(regex = " ".toRegex())
                .map { it.toLong() }
                .toSet()

            val (a, b, c) = KotlinHeroesPractice3.problemA(inputs)
            println("$a $b $c")
        }
         */

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
}
