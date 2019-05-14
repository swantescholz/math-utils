ALPHABET_LOWER = "abcdefghijklmnopqrstuvwxyz"
ALPHABET_UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"

if __name__ == "__main__":
	pass

"""
ys = [16.29422029681641, 27.972800681575265, 37.12604391410312, 44.44840996217168, 50.32092578185961 ,54.975587066347344]
h = math.sqrt(5000)
w = (h-50)/2
ys = [0] + ys + [h]
xs = [-w,0,10,20,30,40,50,50+w]
plt.plot(xs, ys)
plotting.myshow()
"""

""""sympy
from sympy import *
from sympy.solvers.diophantine import diop_linear
from sympy import *

x, y, z = symbols('x y z')
w , h = symbols('w h')
a, b, c, d, e, f = symbols('a b c d e f')

g = sqrt(w*w+a*a)/10+\
	sqrt(100+(b-a)**2)/9+\
	sqrt(100+(c-b)**2)/8+\
	sqrt(100+(d-c)**2)/7+\
	sqrt(100+(e-d)**2)/6+\
	sqrt(100+(f-e)**2)/5+\
	sqrt(w*w+(h-f)**2)/10

for it in [a,b,c,d,e,f]:
	print(diff(g, it))
# """
# """
