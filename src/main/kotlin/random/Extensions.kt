package random

import java.util.*

fun <T> ArrayList<T>.sample(sampleSize: Int): HashSet<T> {
	val rnd = Random()
	val res = HashSet<T>(sampleSize);
	val n = this.size;
	for (i in n - sampleSize..n - 1) {
		val pos = rnd.nextInt(i + 1);
		val item = this.get(pos);
		if (res.contains(item))
			res.add(this.get(i));
		else
			res.add(item);
	}
	return res
}
