package math.sequences

class SpiralNumbers() : Sequence<Long> {
	override fun iterator(): Iterator<Long> {
		return InfSequence(0L) { index, _x ->
			val w = (index - 1) / 4L * 2 + 3
			w * w - (3 - (index - 1) % 4) * (w - 1)
		}
	}
}







