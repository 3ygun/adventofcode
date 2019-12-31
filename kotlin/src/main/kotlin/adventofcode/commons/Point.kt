@file:Suppress("NOTHING_TO_INLINE")

package adventofcode.commons

// <editor-fold desc="Point"

data class Point(
    val x: Int,
    val y: Int
) {
    override fun toString(): String = "(x, y: $x, $y)"
}

inline infix fun Int.xy(y: Int): Point = Point(x = this, y = y)

// </editor-fold>
