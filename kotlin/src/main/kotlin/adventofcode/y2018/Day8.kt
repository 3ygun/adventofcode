package adventofcode.y2018

import adventofcode.DataLoader
import adventofcode.Day

object Day8 : Day {
    val STAR1_DATA = DataLoader.readLinesFromFor("/y2018/Day8Star1.txt").first()
    val STAR2_DATA = STAR1_DATA

    override val day: Int = 8

    override fun star1Run(): String {
        val result = star1Calc(STAR1_DATA)
        return "Metadata sum is $result"
    }

    override fun star2Run(): String {
        val result = star2Calc(STAR2_DATA)
        return "Root node is $result"
    }

    fun star1Calc(rawInput: String): Int {
        return parse(rawInput).metadataSum()
    }

    fun star2Calc(rawInput: String): Int {
        return parse(rawInput).star2Sum
    }

    //<editor-fold desc="work">

    private fun parse(rawInput: String): Node {
        val input = rawInput
            .split(" ")
            .map { Integer.parseInt(it) }

        return parseInputToNodes(input).second
    }

    private fun parseInputToNodes(
        inputs: List<Int>,
        index: Int = 0
    ): Pair<Int /* index */, Node /* nodes */> {
        if (inputs.isEmpty()) return index to Day8.Node.EMPTY
        val children = mutableListOf<Node>()
        val metadata = mutableListOf<Int>()

        val numChildren = inputs[index]
        val numMetadata = inputs[index + 1]
        var newIndex = index + 2

        // Children
        (0 until numChildren)
            .forEach { _ ->
                val (endIndex, endResult) = parseInputToNodes(inputs, newIndex)
                newIndex = endIndex
                children.add(endResult)
            }

        // Metadata
        (0 until numMetadata)
            .forEach { i -> metadata.add(inputs[newIndex + i]) }

        return (newIndex + numMetadata) to Node(children, metadata)
    }

    private data class Node(
        val children: List<Node>,
        val metadata: List<Int>
    ) {
        fun metadataSum(): Int {
            if (children.isEmpty()) return metadata.sum()

            return children
                .map { it.metadataSum() }
                .sum() + metadata.sum()
        }

        val star2Sum: Int by lazy {
            if (children.isEmpty()) return@lazy metadata.sum()

            metadata
                .map { it - 1 } // Index to element 1 is at 0
                .map { when {
                    it < 0 -> 0
                    it < children.size -> children[it].star2Sum
                    else -> 0
                } }
                .sum()
        }

        companion object {
            val EMPTY = Node(emptyList(), emptyList())
        }
    }

    //</editor-fold>
}
