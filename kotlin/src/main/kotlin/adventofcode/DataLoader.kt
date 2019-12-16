package adventofcode

import java.lang.Exception
import java.lang.IllegalArgumentException
import java.nio.file.Files
import java.nio.file.Paths

object DataLoader {
    /**
     * Reads the lines from the given resource specified by [from]
     */
    fun readLinesFromFor(from: String): List<String> {
        // Using this here is very important
        val uri = try {
            this::class.java.getResource(from).toURI()
        } catch (e: Exception) {
            throw IllegalArgumentException("The data file at: '$from' could not be found!", e)
        }
        return Files.readAllLines(Paths.get(uri))
    }

    /** @return result of [readLinesFromFor] removing blanks */
    fun readNonBlankLinesFrom(from: String): List<String> =
        readLinesFromFor(from)
            .filter { it.isNotBlank() } // Removing any trailing blank lines
}
