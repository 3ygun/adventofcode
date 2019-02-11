package adventofcode.utils

fun IntArray.maxValueAndIndex(): MaxAndIndex<Int> {
    var max = 0
    var indexOfMax = -1
    this.forEachIndexed { index, i ->
        if (i > max) {
            max = i
            indexOfMax = index
        }
    }
    return MaxAndIndex(
        max = max,
        index = indexOfMax
    )
}

data class MaxAndIndex<T>(
    val max: T,
    val index: Int
)
