package adventofcode

import java.nio.file.Files
import java.nio.file.Path

object DataLoader {
    /**
     * Reads the lines from the given resource specified by [from]
     */
    fun readLinesFromFor(from: String): List<String> {
        // Using this here is very important
        val uri = this::class.java.getResource(from).toURI()
        return Files.readAllLines(Path.of(uri))
    }
}
