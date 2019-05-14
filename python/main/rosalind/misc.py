import urllib.request
from collections import defaultdict

import itertools


def read_text_from_url(url):
	response = urllib.request.urlopen(url)
	data = response.read()
	text = data.decode('utf-8')
	return text


def myjoin(iterable, separator=" "):
	return separator.join(str(it) for it in iterable)

def minkowski_difference(a,b):
	"""a and b are multisets (dicts with ints as values)"""
	res = defaultdict(int)
	for x,y in itertools.product(a,b):
		res[x-y] += a[x]*b[y]
	return res