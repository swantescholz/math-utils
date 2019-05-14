package util

import math.NANO
import kotlin.system.exitProcess

val HOME = System.getProperty("user.home")


inline fun ignoreExceptions(task: () -> Unit) {
	try {
		task()
	} catch (e: Exception) {
	}
}

fun sleep(seconds: Double) {
	try {
		Thread.sleep((1000 * seconds).toLong())
	} catch(ex: InterruptedException) {
		Thread.currentThread().interrupt()
	}
}

fun quit() {
	exitProcess(-1)
}

fun <R> stage(name: String = "<unknown>", printInfo: Boolean = true, work: () -> R): R {
	if (printInfo) {
		string.printl("stage $name: init")
	}
	val res = work()
	if (printInfo) {
		string.printl("stage $name: done")
	}
	return res
}

private var _print_regularly_last_time = System.nanoTime()
fun printlnRegularly(vararg args: Any, dt: Double = 3.0) {
	val now = System.nanoTime()
	if ((now - _print_regularly_last_time) * NANO > dt) {
		_print_regularly_last_time = now
		var s = ""
		for (it in args) {
			s += it.toString() + " "
		}
		System.out.println(s.removeSuffix(" "))
	}
}

private var _do_regularly_last_time = System.nanoTime()
fun doRegularly(dt: Double = 5.0, function: () -> Unit) {
	val now = System.nanoTime()
	if ((now - _do_regularly_last_time) * NANO > dt) {
		_do_regularly_last_time = now
		function()
	}
}


val MONTH_VALUES = arrayOf(0, 3, 3, 6, 1, 4, 6, 2, 5, 0, 3, 5)
fun computeWeekdayForDate(year: Int, month: Int, dayOfMonth: Int): Int {
	val jh = year / 100
	val jz = year % 100
	val nt = dayOfMonth % 7
	val nm = MONTH_VALUES[month - 1]
	val njz = (jz + jz / 4) % 7
	val njh = (3 - jh % 4) * 2
	val nsj = if (month <= 2 && jz % 4 == 0 && (jz != 0 || jh % 4 == 0)) 6 else 0
	val weekday = (nt + nm + njz + njh + nsj) % 7
	return weekday
}

var _doEveryNTimesCounter = -1L
inline fun doEveryNTimes(n: Long = 10000, function: () -> Unit) {
	if (_doEveryNTimesCounter < 0)
		_doEveryNTimesCounter = n
	_doEveryNTimesCounter--
	if (_doEveryNTimesCounter == 0L) {
		_doEveryNTimesCounter = -1
		function()
	}
}

private var _quitAfterTimesCounter = -1
fun quitAfterTimes(ntimes: Int) {
	if (_quitAfterTimesCounter < 0)
		_quitAfterTimesCounter = ntimes
	_quitAfterTimesCounter--
	if (_quitAfterTimesCounter == 0) {
		println("force quit (quitAfterTimes")
		System.exit(0)
	}
}