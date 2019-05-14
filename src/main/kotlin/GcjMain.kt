import datastructures.BestMap
import datastructures.CoordI2
import datastructures.ModLong
import extensions.*
import extensions.sequences.append
import extensions.sequences.ass
import extensions.sequences.groupCount
import extensions.sequences.prepend
import math.modular.modPow
import math.primes.PRIMES
import math.primes.PRIME_SET
import math.primes.seqPrimes
import string.printl
import util.extensions.*
import util.printlnRegularly
import util.quit
import util.readLinesOfFile
import util.writeLinesToFile
import java.io.File
import java.nio.file.Files
import java.util.*

val P_max :Long = 1 shl 10
val impossible = "IMPOSSIBLE"
var inputeName = ""

fun myinit() {
	inputeName = "A-large-practice"
	inputeName = "A-small-practice"
	inputeName = "C-small-attempt0"
	inputeName = "test"
	inputeName = "C-large"
}

val mod = 1000000007

fun solve(gets: () -> ArrayList<String>, tid: Int): String {
	val (min,max,mean,median) = gets().map{it.int}
	if (min > max)
		return impossible
	if (mean !in min..max || median !in min..max)
		return impossible
	if (mean == min && max > min)
		return impossible
	if (mean == max && max > min)
		return impossible
	if (mean <= (min+median)*0.5)
		return impossible
	if (mean >= (max+median)*0.5)
		return impossible
	if (min == max)
		return 1.string
	var res = 2
	var sum = min+max
	while (true) {
	
	}
	return impossible
}

fun preWork() {

}

// ==============================================

fun ftos(value: Double): String {
    return "%.8f".format(value)
}

fun solve_all_test_cases(lines: ArrayList<String>) {
    PRIMES = seqPrimes(P_max.toInt()).map(Int::toLong).toal()
    PRIME_SET = HashSet(PRIMES)
	preWork()
    val q : Queue<ArrayList<String>> = ArrayDeque()
    lines.mapTo(q) { it.split(" ").toal() }
    val T = Integer.parseInt(q.poll()[0])
    for (test_case_id in 1..T) {
        val result = solve({q.poll()}, test_case_id)
        val text = "Case #$test_case_id: $result"
        write(text)
    }
}


val resPath = "../../downloads/"
var currentOutput = ""
var lastOutput:String? = null

fun mymain() {
    val input_lines = readLinesOfFile(resPath + inputeName + ".in").map{it.trim()}.toal()
    solve_all_test_cases(input_lines)
}

fun write(text: String) {
    val trimmed = text.trim()
    println(trimmed)
    currentOutput += trimmed + "\n"
}


fun main(args: Array<String>) {
    myinit()
	val outputPath = resPath + inputeName + ".out"
    printl("solving input file:", "$inputeName.in")
    val start = System.nanoTime()
    mymain()
    val end = System.nanoTime()
    val s = (end - start) / 1000000000.0
    val m = (s / 60).toInt()
    if (Files.isRegularFile(File(outputPath).toPath())) {
        lastOutput = readLinesOfFile(outputPath).joinToString("\n") { it }
    }
	if (currentOutput.endsWith("\n"))
		currentOutput = currentOutput.slice(0..currentOutput.length-2)
	currentOutput.split("\n").asSequence().writeLinesToFile(outputPath)
    println("====================")
    println("elapsed time: %.2fs".format(s) + " = ${m}m ${s.toInt() % 60}s")
    println("results written to: $outputPath")

    if (currentOutput == lastOutput) {
        println("****SAME****")
    } else {
        println("=====CHANGED=====")
    }

}