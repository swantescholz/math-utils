package extensions.sequences

import datastructures.DefaultHashMap
import datastructures.HashCounter
import extensions.*
import math.LOG2
import math.factorization.PrimeFactorization
import org.apache.commons.math3.fraction.BigFraction
import util.extensions.big
import java.math.BigInteger
import java.text.DecimalFormat
import java.util.*

fun <T> Sequence<T>.takeLast(numberOfElementsToTakeFromBackOfSequence: Int): ArrayList<T> {
	val list = LinkedList<T>()
	for (element in this) {
		list.add(element)
		if (list.size > numberOfElementsToTakeFromBackOfSequence)
			list.removeFirst()
	}
	return list.toArrayList()
}
fun <T> Sequence<T>.toTreeSet(): TreeSet<T> {
	return toCollection(TreeSet<T>())
}

fun <K, V> Sequence<Pair<K, V>>.toHashMap(): HashMap<K, V> {
	val hm = HashMap<K, V>()
	hm.putAll(this)
	return hm
}

fun <K, V> Sequence<Pair<K, V>>.toSortedMap(comparator: ((K, K) -> Int)? = null): TreeMap<K, V> {
	val res = TreeMap<K, V>(comparator)
	this.forEach { res.put(it.first, it.second) }
	return res
}


fun <T> Sequence<T>.printlnAll() {
	for (it in this)
		println(it)
}

fun <T> Sequence<T>.printlnAllIndexed() {
	this.withIndex().forEach { print(it.index.toString() + " "); println(it.value) }
}

fun Sequence<Int>.minmax(): Pair<Int, Int> {
	var lo = Int.MAX_VALUE
	var hi = Int.MIN_VALUE
	this.forEach {
		lo = lo.min(it)
		hi = hi.max(it)
	}
	return Pair(lo, hi)
}

fun Sequence<Long>.lminmax(): Pair<Long, Long> {
	var lo = Long.MAX_VALUE
	var hi = Long.MIN_VALUE
	this.forEach {
		lo = lo.min(it)
		hi = hi.max(it)
	}
	return Pair(lo, hi)
}


inline fun <T> Sequence<T>.sumByLong(selector: (T) -> Long): Long {
	var sum: Long = 0
	for (element in this) {
		sum += selector(element)
	}
	return sum
}

fun <T> Sequence<T>.groupCount(): ArrayList<Pair<T, Int>> {
	val hm = HashCounter<T>()
	this.forEach { hm.increase(it) }
	return hm.asSequence().map { Pair(it.key, it.value.toInt()) }.toArrayList()
}

fun Sequence<BigInteger>.sum() = this.fold(0.big, { a, b -> a + b })
fun Sequence<BigInteger>.product() = this.fold(1.big, { a, b -> a * b })
fun Sequence<BigFraction>.sum() = this.fold(0.bigf(), { a, b -> a + b })
fun Sequence<BigFraction>.product() = this.fold(1.bigf(), { a, b -> a * b })
fun Sequence<Int>.product() = this.fold(1, { a, b -> a * b })
fun Sequence<Int>.lproduct() = this.fold(1L, { a, b -> a * b })
fun Sequence<Long>.product() = this.fold(1L, { a, b -> a * b })
fun Sequence<Double>.product() = this.fold(1.0, { a, b -> a * b })

fun Sequence<Long>.lcm(): Long {
	val hm = DefaultHashMap<Long, Long>({ 0 }, false)
	for (e in this) {
		for ((p, c) in PrimeFactorization.factorize(e)) {
			hm[p] = hm[p].max(c.toLong())
		}
	}
	return hm.map { it.key.pow(it.value) }.product()
}

fun Sequence<Number>.printInfo() {
	val seq = this.map { it.toDouble() }.toCollection(arrayListOf<Double>()).asSequence()
	val df = DecimalFormat("#.####");
	fun Sequence<Double>.pln(prefix: String) = this.map {
		if (it.toLong().toDouble() == it)
			return@map it.toLong().toString()
		return@map df.format(it)
	}.joinToString(separator = ", ",
			prefix = prefix.padEnd(15) + ": ").printl()
	seq.pln("normal")
	seq.zip(seq.drop(1)).map { it.second - it.first }.pln("differences")
	seq.zip(seq.drop(1)).map { it.second / it.first.toDouble() }.pln("slope")
	seq.map { Math.log(it.toDouble()) }.pln("log_e")
	seq.map { Math.log(it.toDouble()) / LOG2 }.pln("log_2")
	seq.map { Math.sqrt(it.toDouble()) }.pln("sqrts")
	seq.map { it * it }.pln("squares")
	seq.map { it * it * it }.pln("cubes")
}

