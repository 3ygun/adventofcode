package adventofcode.y2018

import adventofcode.DataLoader
import adventofcode.Day
import java.util.*

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
        val (parents, idToNode) = rawInput.parse()
        return treverse(idToNode, parents.sorted().toMutableList())
    }

    fun star2Calc(
        scale: Int,
        workers: Int,
        rawInput: List<String>
    ): Pair<Int, String> {
        val (parents, idToNode) = rawInput.parse()

        val workers = Workers(amount = workers, scale = scale)
        treverse(idToNode, parents.toMutableList(), workers)
        workers.submit(listOf())

        return workers.totalRuntime to workers.result
    }

    private fun List<String>.parse(): Pair<MutableSet<Id> /* parents */, MutableMap<Id, Node> /* idToNode */> {
        val parents: MutableSet<Id> = mutableSetOf()
        val idToNode: MutableMap<Id, Node> = mutableMapOf()

        // Parse everything
        forEach { input ->
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
                ?.addChild(childNode)
                ?: Node(id, mutableSetOf(childNode)).also { parents.add(it.id) }

            idToNode[id] = idNode
        }

        return parents to idToNode
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

//<editor-fold desc="Star 2">

private data class Workers(
    private val amount: Int,
    private val scale: Int
) {
    private val workers: MutableSet<WorkWithId> = mutableSetOf()
    var totalRuntime: Int = 0
        private set
    var result: String = ""
        private set

    tailrec fun submit(values: List<Id>) {
        val available = amount - workers.size
        assert(available >= 0) { "More workers assigned then available" }
        if (available != amount) step()
        when {
            available == 0 -> submit(values)
            values.size <= available -> values.forEach { addValue(it) }
            else -> {
                val sorted = values.sorted()
                sorted.subList(0, available).forEach { addValue(it) }
                return submit(sorted.drop(available))
            }
        }
    }

    private fun addValue(id: Id) {
        val workLeft = scale + (id[0].toInt() - 'A'.toInt())
        workers.add(WorkWithId(workLeft, id))
    }

    private fun step() {
        val worked = workers.minBy { it.workLeft }?.workLeft
            ?: throw IllegalArgumentException("Didn't have any workers")
        val (completed: List<WorkWithId>, _ /* processed */) = workers
            .map { it.completed(worked); it }
            .partition { it.workLeft == 0 }

        totalRuntime += worked
        completed.sorted().forEach { result += it.id }
        workers.removeIf { it.workLeft == 0 }
    }
}

private data class WorkWithId(
    var workLeft: Int,
    val id: Id
): Comparable<WorkWithId> {
    fun completed(work: Int) {
        workLeft -= work
    }

    override fun compareTo(other: WorkWithId): Int = id.compareTo(other.id)
}

private tailrec fun treverse(
    idToNode: Map<Id, Node>,
    travelableIds: MutableList<Id>,
    workers: Workers,
    visited: MutableSet<Id> = mutableSetOf()
) {
    if (travelableIds.isEmpty()) return

    val justVisited = travelableIds.subtract(visited)
        .also { toVisit ->
            workers.submit(toVisit.toList())
            visited.addAll(toVisit)
        }

    val next = justVisited.map { id ->
        idToNode[id]
            ?.let { node ->
                node.children
                    .filter { it.parents.subtract(visited).isEmpty() }
                    .map { it.id }
                    .let { travelableIds.union(it).toMutableSet() }
            }
            ?: throw IllegalArgumentException("Could not find Node with id $id")
    }.flatten().toSet()

    return treverse(idToNode, next.sorted().toMutableList(), workers, visited)
}

data class Computed(
    val work: Int,
    val step: Char
): Comparable<Computed> {
    override fun compareTo(other: Computed): Int = when {
        work < other.work -> -1
        work > other.work -> 1
        else -> step.compareTo(other.step)
    }
}

//</editor-fold>
