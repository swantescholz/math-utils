package math.linearalgebra

import extensions.max
import extensions.min
import java.util.*

class Coordi3(val x: Int, val y: Int = x, val z: Int = x) : VectorSpacei<Coordi3>(), Comparable<Coordi3> {
	override fun compareTo(other: Coordi3): Int {
		if (z < other.z)
			return -1
		if (z > other.z)
			return 1
		if (y < other.y)
			return -1
		if (y > other.y)
			return 1
		return x.compareTo(other.x)
	}
	
	override fun unaryMinus(): Coordi3 = Coordi3(-x, -y, -z)
	
	override fun plus(o: Coordi3): Coordi3 = Coordi3(x + o.x, y + o.y, z + o.z)
	
	override fun minus(o: Coordi3): Coordi3 = Coordi3(x - o.x, y - o.y, z - o.z)
	
	override fun times(o: Int): Coordi3 = Coordi3(x * o, y * o, z * o)
	operator fun times(o: Coordi3): Coordi3 = Coordi3(x * o.x, y * o.y, z * o.z)
	
	fun min(o: Coordi3) = Coordi3(x.min(o.x), y.min(o.y), z.min(o.z))
	fun max(o: Coordi3) = Coordi3(x.max(o.x), y.max(o.y), z.max(o.z))
	
	val vec3: Vector3
		get() = Vector3(x, y, z)
	
	override fun toString() = "($x, $y, $z)"
	
	operator fun component1(): Int = x
	operator fun component2(): Int = y
	operator fun component3(): Int = z
	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (other?.javaClass != javaClass) return false
		
		other as Coordi3
		
		if (x != other.x) return false
		if (y != other.y) return false
		if (z != other.z) return false
		
		return true
	}
	
	override fun hashCode(): Int {
		var result = x
		result = 31 * result + y
		result = 31 * result + z
		return result
	}
	
	val neighbors6: ArrayList<Coordi3> // x+,x-,y+,y-,z+,z- in that order
		get() = arrayListOf(
				Coordi3(x + 1, y, z), Coordi3(x - 1, y, z),
				Coordi3(x, y + 1, z), Coordi3(x, y - 1, z),
				Coordi3(x, y, z + 1), Coordi3(x, y, z - 1))
}

fun IntRange.contains(o: Coordi3) = o.x in this && o.y in this && o.z in this