package adventofcode.y2018

import adventofcode.DataLoader
import adventofcode.Day

object Day7 : Day {
    val STAR1_DATA = DataLoader.readLinesFromFor("/y2018/Day7Star1.txt")
    val STAR2_DATA = STAR1_DATA

    private val LINE_REGEX = Regex("(?>Step (\\w*) must be finished before step (\\w*) can begin\\.)")

    override val day: Int = 7

    override fun star1Run(): String {
        val result = star1Calc(STAR1_DATA)
        return "Sequence $result"
    }

    override fun star2Run(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun star1Calc(rawInput: List<String>): String {
        val parents: MutableSet<Id> = mutableSetOf()
        val idToNode: MutableMap<Id, Node> = mutableMapOf()

        // Parse everything
        rawInput.forEach { input ->
            val values = LINE_REGEX.matchEntire(input)?.groupValues
                ?.takeIf { it.size == 3 }
                ?: throw IllegalArgumentException("The following line wasn't valid: $input")
            val id = values[1]
            val before = values[2]

            val childNode = (idToNode[before]
                ?.also { parents.remove(it.id) }
                ?: Node(before).also { idToNode[before] = it })
                .also { it.addParentId(id) }
            val idNode = idToNode[id]
                ?.let { it.addChild(childNode) }
                ?: Node(id, mutableSetOf(childNode)).also { parents.add(it.id) }

            idToNode[id] = idNode
        }

        val result = treverse(idToNode, parents.sorted().toMutableList())

        return result
    }
}

//<editor-fold desc="Star 1">

typealias Id = String

private data class Node(
    val id: Id,
    val children: MutableSet<Node> = mutableSetOf(),
    val parents: MutableSet<Id> = mutableSetOf()
) {
    fun addChild(node: Node): Node {
        children.add(node)
        return this
    }

    fun addParentId(id: Id): Node {
        parents.add(id)
        return this
    }
}

private tailrec fun treverse(
    idToNode: Map<Id, Node>,
    travelableIds: MutableList<Id>,
    result: String = "",
    visited: MutableSet<Id> = mutableSetOf()
): String {
    if (travelableIds.isEmpty()) return result

    val id = travelableIds.first()
    travelableIds.remove(id)
    if (visited.contains(id)) {
        return treverse(idToNode, travelableIds, result, visited)
    }

    visited.add(id)
    val nextIds: MutableSet<Id> = idToNode[id]
        ?.let { node ->
            node.children
                .filter { it.parents.subtract(visited).isEmpty() }
                .map { it.id }
                .let { travelableIds.union(it).toMutableSet() }
        }
        ?: throw IllegalArgumentException("Could not find Node with id $id")

    return treverse(idToNode, nextIds.sorted().toMutableList(), result + id, visited)
}

//</editor-fold>
