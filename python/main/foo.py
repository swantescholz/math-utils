
import numpy as np

def mex(s):
    for i in range(len(s)):
        if i not in s:
            return i
    return len(s)


n = 1000000
a = np.zeros(n+1, dtype=np.int16)
a[2] = 1
for i in range(3,n+1):
    k = i//2
    b = a[:k]
    c = a[i-2:i-k-2:-1]
    d = np.bitwise_xor(b,c)
    a[i] = mex(d)
    print(i, a[i])
print(sum(a > 0))
