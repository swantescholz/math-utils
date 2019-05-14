import math
from fractions import Fraction
from typing import List

import sympy
import sympy.abc


def compute_faulhaber_polynomials(p: int) -> List[List[Fraction]]:
	""" e.g. p=2 ->
	[[Fraction(0, 1), Fraction(1, 1)],
	[Fraction(0, 1), Fraction(1, 2), Fraction(1, 2)],
	[Fraction(0, 1), Fraction(1, 6), Fraction(1, 2), Fraction(1, 3)]]
	"""
	
	def nextu(a):
		n = len(a)
		a.append(1)
		for i in range(n - 1, 0, -1):
			a[i] = i * a[i] + a[i - 1]
		return a
	
	def nextv(a):
		n = len(a) - 1
		b = [(1 - n) * x for x in a]
		b.append(1)
		for i in range(n):
			b[i + 1] += a[i]
		return b
	
	u = [0, 1]
	v = [[1], [1, 1]]
	coefficients = [[Fraction(0), Fraction(1)]]
	for i in range(1, p + 1):
		print("coff", i)
		v.append(nextv(v[-1]))
		t = [0] * (i + 2)
		for j, r in enumerate(u):
			r = Fraction(r, j + 1)
			for k, s in enumerate(v[j + 1]):
				t[k] += r * s
		coefficients.append(t)
		u = nextu(u)
	return coefficients


def sum_of_powered_quadratic(a: Fraction, b: Fraction, p: int, n: int) -> Fraction:
	""":returns sum((i**2+a*i+c)**p for i in range(1,n+1) using faulhaber polynomials"""
	pfac = Fraction(math.factorial(p))
	res = Fraction(n, 2)
	f = Fraction(2, n) ** (2 * p) / 2
	for power in range(2 * p + 1):
		coeff = Fraction(0)
		for i in range(power // 2 + 1):
			j = power - 2 * i
			if j + i > p:
				continue
			k = p - i - j
			coeff += pfac / (math.factorial(i) * math.factorial(j) * math.factorial(k)) * a ** j * b ** k
		res += f * coeff * sympy.summation(sympy.abc.i ** power,
		                                   (sympy.abc.i, 1, n))
		print(power, float(res))
	return res
