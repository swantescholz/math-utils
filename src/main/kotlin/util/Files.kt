package util

import java.io.*
import java.nio.file.Files
import java.nio.file.Path

fun Sequence<String>.writeLinesToFile(filepath: String) {
	val path = File(filepath).toPath()
	val writer = Files.newBufferedWriter(path)
	for (line in this) {
		writer.write("$line\n")
	}
	writer.close()
}

// reads lines and maps them until map returns null
fun readLinesOfFile(filepath: String): Sequence<String> {
	val reader = Files.newBufferedReader(File(filepath).toPath())
	return generateSequence { reader.readLine() }
}

fun readPrimesFile(max: Int): Sequence<Int> {
	val fin = BufferedInputStream(FileInputStream("res/euler/primes9.bin"), 2 shl 20)
	val dis = DataInputStream(fin)
	return generateSequence {
		try {
			val p = dis.readInt()
			if (p > max) {
				dis.close()
				fin.close()
				return@generateSequence null
			}
			p
		} catch (e: EOFException) {
			dis.close()
			fin.close()
			return@generateSequence null
		}
	}
}