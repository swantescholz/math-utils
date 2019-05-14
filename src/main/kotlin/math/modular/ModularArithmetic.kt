@file:Suppress("VARIABLE_WITH_REDUNDANT_INITIALIZER")

package math.modular

import math.BIG2
import java.math.BigInteger

/**
 * Modular Arithmetic
 * This program calculates the GCD, Extended GCD Inverse Modulus and sqare root mod p
 * (a^(-1) mod b) for long integers.
 *
 *
 * Classes used: Tools, LabMenu
 * @author Moshe
 * *
 * @author Denis Berger Edited Feb 2006
 * *
 * @version 0.1  Feb. 26, 2002
 */
object ModularArithmetic {

	/**
	 * Calculates the GCD of two integers.
	 * @param a the first integer
	 * *
	 * @param b the second integer
	 * *
	 * @return the greatest common divisor of a & b
	 */
	fun GCD(a: BigInteger, b: BigInteger): BigInteger {
		val zero = BigInteger.valueOf(0)
		if (a.multiply(b).compareTo(zero) == 0)
			return a.add(b)  //EXIT condition
		else
			return GCD(b, a.mod(b))  //the recursive call
	}

	/**
	 * Calculates the extended GCD.
	 * @param aux the helper array
	 * *
	 * @param a the first integer
	 * *
	 * @param b the second integer
	 * *
	 * @return the greatest common divisor of the a & b. GCD(a,b) = m*a + n*b. Stores m and n in the helper array.
	 */
	fun extGCD(aux: Array<BigInteger>, a: BigInteger, b: BigInteger): BigInteger {
		val tempo: BigInteger
		val zero = BigInteger.valueOf(0)
		if (a.multiply(b).compareTo(zero) == 0) {    //EXIT condition
			tempo = a.add(b)
		} else {
			tempo = extGCD(aux, b, a.mod(b))
			val temp = aux[0]
			aux[0] = aux[1]
			aux[1] = temp.subtract(aux[1].multiply(a.divide(b)))
		}
		return tempo
	}

	/**
	 * Calculates the inverse modulus: a^(-1) mod b
	 * @param aux the helper array
	 * *
	 * @param a the base
	 * *
	 * @param b the the modulus
	 * *
	 * @return a^(-1) mod b, or -1 if the inverse does not exist.
	 */
	fun inverseMod(aux: Array<BigInteger>, a: BigInteger, b: BigInteger): BigInteger? {
		val zero = BigInteger.valueOf(0)
		val one = BigInteger.valueOf(1)
		if (GCD(a, b).compareTo(one) == 1)
		//gcd a,b > 1
			return null
		else {
//			val tempo = extGCD(aux, a, b)
			//aux[0] > 0
			return if (aux[0].compareTo(zero) == 1) aux[0].mod(b) else aux[0].mod(b).add(b)
		}
	}

	/**
	 * Calculates power of a^exp % m.
	 * @param m the mod
	 * *
	 * @param exp the exponent
	 * *
	 * @param a   the base
	 * *
	 * @return a^exp % m.
	 */
	fun modPower(m: BigInteger, exp: BigInteger, a: BigInteger): BigInteger {
		val zero = BigInteger.valueOf(0)
		val one = BigInteger.valueOf(1)
		val two = BigInteger.valueOf(2)
		if (exp.compareTo(one) == 0) return a.mod(m)
		if (exp.compareTo(zero) == 0) return one
		if (exp.mod(two).compareTo(zero) == 0) {
			val x = modPower(m, exp.divide(two), a)
			return x.multiply(x).mod(m)
		} else {
			val x = modPower(m, exp.subtract(one).divide(two), a)
			return x.multiply(x).multiply(a).mod(m)
		}
	}

	/**
	 * Calculates square root of res mod p.
	 * @param res the residue
	 * *
	 * @param p the prime number
	 * *
	 * @return square root of res mod p or null if none can be found
	 */
	fun sqrtP(res: BigInteger, p: BigInteger): BigInteger? {
		if (p == BIG2) {
			if (res < BIG2)
				return res
			return null
		}
		val zero = BigInteger.valueOf(0)
		val one = BigInteger.valueOf(1)
		val two = BigInteger.valueOf(2)
//		val three = BigInteger.valueOf(3)
//		val four = BigInteger.valueOf(4)
		if (p.mod(two).compareTo(zero) == 0) return null //p not prime odd prime
		var q = p.subtract(one).divide(two)
		//make sure res is a residue mod p by checking that res^q mod p == 1
		if (modPower(p, q, res).compareTo(one) != 0) return null

		while (q.mod(two).compareTo(zero) == 0) {
			q = q.divide(two)
			//if res^q mod p != 1 run the complicated root find
			if (modPower(p, q, res).compareTo(one) != 0) {
				return complexSqrtP(res, q, p)
			}

		}
		//Code gets here if res^q mod p were all 1's and now q is odd
		//then root = res^((q+1)/2) mod p
		q = q.add(one).divide(two)
		return modPower(p, q, res)
	}

	/**
	 * Calculates square root of res mod p using a start exponent q.
	 * @param res the residue
	 * *
	 * @param _q the prime number
	 * *
	 * @param p the prime number
	 * *
	 * @return square root of res mod p or null if none can be found
	 */
	private fun complexSqrtP(res: BigInteger, _q: BigInteger, p: BigInteger): BigInteger? {
		var q = _q
		val a = findNonResidue(p) ?: return null
		val zero = BigInteger.valueOf(0)
		val one = BigInteger.valueOf(1)
		val two = BigInteger.valueOf(2)
		var t = p.subtract(one).divide(two)
		val negativePower = t // a^negativePower mod p = -1 mod p this will be used to get the right power
		//res^q mod p = a^((p-1)/2) mod p

		while (q.mod(two).compareTo(zero) == 0) {
			q = q.divide(two)
			t = t.divide(two)
			//check to make sure that the right power was gonnen
			if (modPower(p, q, res).compareTo(modPower(p, t, a)) != 0) {
				//-(a^t mod p) = a^t*a^negativePower mod p = a^t+(negativePower) mod p
				t = t.add(negativePower)
			}
		}
		val helper = arrayOf(one, one)
		val inverceRes = inverseMod(helper, res, p)
		//	inverceRes^((q-1)/2)
		q = q.subtract(one).divide(two)
		//System.out.println("p:"+p+" q:"+q+"invres: "+inverceRes);
		val partone = modPower(p, q, inverceRes!!)
		//  a^(t/2)
		t = t.divide(two)
		val parttwo = modPower(p, t, a)
		var root: BigInteger
		root = partone.multiply(parttwo)
		root = root.mod(p)
		return root
	}

	/**
	 * Finds the non residue of the prime p
	 * @param q the prime number
	 * *
	 * @return square root of res mod p or null if none can be found
	 */
	private fun findNonResidue(p: BigInteger): BigInteger? {
		val one = BigInteger.valueOf(1)
		val two = BigInteger.valueOf(2)
		//pick numbers till a^((p-1)/2) = -1;
		var a = 2
		val q = p.subtract(one).divide(two)
		while (true) {
			if (modPower(p, q, BigInteger.valueOf(a.toLong())).compareTo(one) != 0) {
				return BigInteger.valueOf(a.toLong())
			}
			//If i tried all the numbers in an int and got nothing somthing is wrong... this is taking too long.
			if (a == 0) return null
			a++
		}

	}

	/**
	 * Calculates square root of res mod pq.
	 * @param roots, array[4] to store the 4 roots
	 * *
	 * @param res the residue
	 * *
	 * @param p first prime number
	 * *
	 * @param q second prime number
	 * *
	 * @return square root of res mod p or null if none can be found
	 */
	fun sqrtPQ(roots: Array<BigInteger>, res: BigInteger, p: BigInteger, q: BigInteger): Boolean {
		val zero = BigInteger.valueOf(0)
		val one = BigInteger.valueOf(1)
		val two = BigInteger.valueOf(2)
		if (p.mod(two).compareTo(zero) == 0) return false
		if (q.mod(two).compareTo(zero) == 0) return false

		val temp = arrayOf(one, one)
		val n = p.multiply(q)
		extGCD(temp, p, q)
		val a = temp[0]
		val b = temp[1]
		val x1 = sqrtP(res, p)
		val x2 = sqrtP(res, q)
		if (x1 == null || x2 == null) return false
		val s1 = x2.multiply(a).multiply(p)
		val s2 = x1.multiply(b).multiply(q)
		roots[0] = s1.add(s2).mod(n)                 //x2*a*p + x1+b*q;
		roots[1] = s1.subtract(s2).mod(n)             //x2*a*p - x1+b*q;
		roots[2] = s2.subtract(s1).mod(n)             //-x2*a*p + x1+b*q;
		roots[3] = s1.negate().subtract(s2).mod(n) //-x2*a*p - x1+b*q;
		return true
	}

	/**
	 * attempts to find the factors of a compiste n = pq where p and q are primes
	 * @param factors, array[2] to store the 2 factors
	 * *
	 * @param n is the composite number
	 * *
	 * @param runLenght is the number of attempts
	 * *
	 * @return true if found factors false if didnt
	 */
	fun pollardRho(factors: Array<BigInteger>, n: BigInteger, runLenght: Int): Boolean {
		val one = BigInteger.valueOf(1)
//		val two = BigInteger.valueOf(2)
//		val five = BigInteger.valueOf(5)

		var a = BigInteger.valueOf(2)
		var b = BigInteger.valueOf(5)
		for (i in 1..runLenght - 1) {
			a = a.multiply(a).add(one).mod(n)
			val btemp = b.multiply(b).add(one)
			b = btemp.multiply(btemp).add(one).mod(n)
			val factor = GCD(a.subtract(b), n)
			if (factor.compareTo(one) == 1) {
				factors[0] = factor
				factors[1] = n.divide(factor)
				return true
			}
		}
		return false
	}

	/**
	 * Checks in an integer is prime
	 * @param inetger to check
	 * *
	 * @param size amount of numbers to check
	 * *
	 * @return true if found factors false if didnt
	 */
	fun millerRabinPrimeCheck(p: BigInteger, size: Int): Boolean {
		val zero = BigInteger.valueOf(0)
		val one = BigInteger.valueOf(1)
		val two = BigInteger.valueOf(2)
		val minusOne = p.subtract(one)
		val q = p.subtract(one)
		var t = q
		for (i in 2..size - 1) {
			//make sure i = p^x because then all powers of i are zero
			if (BigInteger.valueOf(i.toLong()).mod(p).compareTo(zero) != 0) {
				t = p.subtract(one)
				var check = modPower(p, t, BigInteger.valueOf(i.toLong()))
				if (check.compareTo(one) != 0) return false
				while (t.mod(two).compareTo(zero) == 0) {
					t = t.divide(two)
					//bool check = i^q mod p;
					check = modPower(p, t, BigInteger.valueOf(i.toLong()))
					if (check.compareTo(one) != 0 && check.compareTo(minusOne) != 0) {
						return false
					}
					if (check.compareTo(minusOne) == 0) {
						break
					}
				}
			}
		}
		return true
	}
}// private constructor to prevent javadoc generation

