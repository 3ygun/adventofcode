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
        val result = star2Calc(scale = 60, numWorkers = 5, rawInput = STAR2_DATA)
        return "Sequence time to sequence:  $result"
    }

    fun star1Calc(rawInput: List<String>): String {
        val (parents, idToNode) = rawInput.parse()
        return treverse(idToNode, parents.sorted().toMutableList())
    }

    fun star2Calc(
        scale: Int,
        numWorkers: Int,
        rawInput: List<String>
    ): Pair<Int, String> {
        val (parents, idToNode) = rawInput.parse()

        val workers = Workers(scale, numWorkers)
        val result = star2Treverse(
            workers = workers,
            canVisit = parents,
            visited = setOf(),
            idToNode = idToNode,
            result = ""
        )

        return workers.timeElasped to result
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

private data class UnitOfWork(
    val workRemaining: Int,
    val id: Id
) {
    constructor(id: Id, scale: Int) : this(
        workRemaining = id[0].toInt() - A_ASCII + scale,
        id = id
    )

    companion object {
         private const val A_ASCII = 'A'.toInt() - 1
    }
}

private class Workers(
    private val scale: Int,
    private val workers: Int
) {
    var timeElasped: Int = 0 // Start before we start counting
        private set
    var work: Set<UnitOfWork> = setOf()
        private set

    fun noWork(): Boolean = work.isEmpty()
    fun noOpenWorkers(): Boolean = !hasOpenWorkers()
    fun hasOpenWorkers(): Boolean = openWorkers() > 0
    private fun openWorkers(): Int = workers - work.size

    /**
     * @return unassigned newWork
     */
    fun assignWork(newWork: Set<Id>): Set<Id> {
        if (noOpenWorkers()) return newWork

        val sortedWork = newWork.sorted()
        val open = openWorkers()
        val numWork = sortedWork.size

        return if (numWork <= open) {
            work = sortedWork
                .map { UnitOfWork(it, scale) }
                .let { work.union(it) }
            setOf()
        } else {
            work = sortedWork.subList(0, open)
                .map { UnitOfWork(it, scale) }
                .let { work.union(it) }
            sortedWork.drop(open).toSet()
        }
    }

    fun step(): Set<Id> {
        timeElasped ++
        if (work.isEmpty()) return setOf()

        val (completed, nextWork) = work
            .map { it.copy(workRemaining = it.workRemaining - 1) }
            .partition { it.workRemaining <= 0 }

        work = nextWork.toSet()
        return completed.map { it.id }.toSet()
    }
}

private tailrec fun star2Treverse(
    workers: Workers,
    canVisit: Set<Id>,
    visited: Set<Id>,
    idToNode: Map<Id, Node>,
    result: String
): String {
    // println("result: $result, currentWork: ${workers.work}")
    when {
        canVisit.isEmpty() -> if (workers.noWork()) return result // Otherwise fall through
        workers.hasOpenWorkers() -> {
            val remainingCanVisit = workers.assignWork(canVisit)
            return star2Treverse(workers, remainingCanVisit, visited, idToNode, result)
        }
    }

    val justCompleted = workers.step()
    // If no work was completed then next!
    if (justCompleted.isEmpty()) return star2Treverse(workers, canVisit, visited, idToNode, result)

    // Work was completed update!
    val nowVisited = visited.union(justCompleted)
    val canNowVisit = justCompleted.map { id ->
        idToNode[id]
            ?.let { node ->
                node.children
                    .filter { it.parents.subtract(nowVisited).isEmpty() }
                    .map { it.id }
            }
            ?: throw IllegalArgumentException("Could not find Node with id $id")
    }.flatten().toSet().union(canVisit) // Affirm you include any existing canVisits
    val newResult = StringBuilder(result)
        .also { r -> justCompleted.sorted().forEach { r.append(it) } }
        .toString()

    return star2Treverse(workers, canNowVisit, nowVisited, idToNode, newResult)
}

//</editor-fold>
