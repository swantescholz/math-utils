
class Matrix:
	def __init__(self, width, height):
		self.width = width
		self.height = height
		self.m = [[0] * self.height for _ in range(self.width)]
	

	def __getitem__(self, key):
		x,y = key
		return self.m[x][y]

	def __setitem__(self, key, value):
		self.m[key[0]][key[1]] = value
	
	def __str__(self):
		return "\n".join("\t".join(str(self[x,y]) for x in range(self.width)) for y in range(self.height))
	
	def __mod__(self, mod):
		res = Matrix(self.width, self.height)
		for x in range(res.width):
			for y in range(res.height):
				res.m[x][y] = self.m[x][y] % mod
		return res
	
	def __matmul__(self, other):
		assert self.width == other.height
		res = Matrix(other.width, self.height)
		for x in range(res.width):
			for y in range(res.height):
				sum = 0
				for i in range(self.width):
					sum += self.m[i][y] * other.m[x][i]
				res.m[x][y] = sum
		return res
	
	def __pow__(self, power, modulo=None):
		assert self.width == self.height and modulo is None
		x = Matrix(self.width, self.width)
		for i in range(x.width):
			x[i, i] = 1
		bits = "{0:b}".format(power)
		for i, bit in enumerate(bits):
			if bit == '1':
				x = ((x @ x) @ self)
			elif bit == '0':
				x = (x @ x)
		return x
	
	def modpow(self, exponent, mod):
		assert self.width == self.height
		x = Matrix(self.width, self.width)
		for i in range(x.width):
			x[i, i] = 1
		bits = "{0:b}".format(exponent)
		for i, bit in enumerate(bits):
			if bit == '1':
				x = (((x @ x) % mod) @ self) % mod
			elif bit == '0':
				x = (x @ x) % mod
		return x % mod

def fib(n, mod):
	m = Matrix(2, 2)
	m[0, 0] = 1
	m[1, 0] = 1
	m[0, 1] = 1
	return m.modpow(n, mod)[0,1]

if __name__ == "__main__":
	for i in range(200):
		print(i,fib(i,10**300))
	