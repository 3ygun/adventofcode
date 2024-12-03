package adventofcode.y2024

import adventofcode.DataLoader
import adventofcode.Day

// https://adventofcode.com/2024/day/1
object Day1 : Day {
    override val day: Int = 1

    internal val STAR1: List<String> get() = DataLoader.readNonBlankLinesFrom("/y2024/Day1Star1.txt")

    override fun star1Run(): String {
        val difference = star1(STAR1)
        return "Difference in the lists: $difference"
    }

    fun star1(inputLines: List<String>): Long {
        val listA = mutableListOf<Long>()
        val listB = mutableListOf<Long>()
        inputLines.forEach { line ->
            if (line.trim() == "") return@forEach

            val elements = line.split(' ').filterNot { it.isBlank() }
            elements.size

            assert(elements.size == 2)
            elements.first().toLongOrNull()?.let(listA::add)
            elements.last().toLongOrNull()?.let(listB::add)
        }

        assert(listA.size == listB.size)
        listA.sort()
        listB.sort()

        return listA.zip(listB).sumOf { (a, b) ->
            if (a >= b) a-b else b-a
        }
    }

    override fun star2Run(): String {
        val score = star2(STAR1)
        return "Similarity score: $score"
    }

    internal fun star2(inputLines: List<String>): Long {
        val listA = mutableListOf<Long>()
        val listB = mutableListOf<Long>()
        inputLines.forEach { line ->
            if (line.trim() == "") return@forEach

            val elements = line.split(' ').filterNot { it.isBlank() }
            elements.size

            assert(elements.size == 2)
            elements.first().toLongOrNull()?.let(listA::add)
            elements.last().toLongOrNull()?.let(listB::add)
        }

        assert(listA.size == listB.size)
        listA.sort()
        listB.sort()

        var lastIndex = 0
        tailrec fun findCount(a: Long, index: Int, count: Long = 0): Long {
            return if (index >= listB.size) count
            else {
                val b = listB[index]
                when {
                    a < b -> count
                    a > b -> {
                        lastIndex = index
                        findCount(a, index + 1)
                    }
                    else -> {
                        if (count == 0L) lastIndex = index
                        findCount(a, index + 1, count + 1)
                    }
                }
            }
        }
        return listA.sumOf { a ->
            a * findCount(a, lastIndex)
        }
    }
}
