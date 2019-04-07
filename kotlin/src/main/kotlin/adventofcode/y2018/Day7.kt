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
        return "Took ${result.first} time units to compute ${result.second} sequence"
    }

    fun star1Calc(rawInput: List<String>): String {
        return star2Calc(
            scale = 0,
            numWorkers = 1,
            rawInput = rawInput
        ).second
    }

    fun star2Calc(
        scale: Int,
        numWorkers: Int,
        rawInput: List<String>
    ): Pair<Int, String> {
        val (parents, idToNode) = rawInput.parse()

        val workers = Workers(scale, numWorkers)
        val result = traverse(
            workers = workers,
            canVisit = parents,
            visited = setOf(),
            idToNode = idToNode,
            result = ""
        )

        return workers.timeElasped to result
    }

    private fun List<String>.parse(): Pair<Set<Id> /* parents */, Map<Id, Node> /* idToNode */> {
        val parents: MutableSet<Id> = mutableSetOf()
        val idToNode: MutableMap<Id, Node> = mutableMapOf()

        // Parse everything
        forEach { input ->
            val values = LINE_REGEX.matchEntire(input)?.groupValues
                ?.takeIf { it.size == 3 }
                ?: throw IllegalArgumentException("The following line wasn't valid: $input")
            val id: Char = values[1][0]
            val before: Char = values[2][0]

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

//<editor-fold desc="Star completion">

private typealias Id = Char

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

private data class UnitOfWork(
    val workRemaining: Int,
    val id: Id
) {
    constructor(id: Id, scale: Int) : this(
        workRemaining = id.toInt() - A_ASCII + scale,
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

private tailrec fun traverse(
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
            return traverse(workers, remainingCanVisit, visited, idToNode, result)
        }
    }

    val justCompleted = workers.step()
    // If no work was completed then next!
    if (justCompleted.isEmpty()) return traverse(workers, canVisit, visited, idToNode, result)

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

    return traverse(workers, canNowVisit, nowVisited, idToNode, newResult)
}

//</editor-fold>
