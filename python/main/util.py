import time

_last_print_regularly_time = None
_counter_print_every_n_times = 0


def print_regularly(*args, delta_time=4.0):
	global _last_print_regularly_time
	current_time = time.time()
	if _last_print_regularly_time is None or current_time - _last_print_regularly_time >= delta_time:
		print(args)
		_last_print_regularly_time = current_time


def print_every_n_times(n, *args):
	global _counter_print_every_n_times
	_counter_print_every_n_times += 1
	if _counter_print_every_n_times >= n:
		print(args)
		_counter_print_every_n_times = 0


def assert_equal(a,b):
	if not (a==b):
		raise AssertionError("{} != {}".format(a,b))

def fst(x):
	return x[0]


def snd(x):
	return x[1]


def trd(x):
	return x[2]


def pairwise(l):
	""":returns list of pairs: [(l[0],l[1]),(l[2],l[3])...]"""
	it = iter(l)
	return zip(it, it)


def ast_equals(a, b, msg=""):
	assert a == b, f"{a}!={b}\n{msg}"
