package main

import (
	. "fmt"
	"time"
	"math"
)

func mymain() {
	var diff, diff_delta, res int64 = 3, 4, 0
	var N int64 = 5000 * 1000 * 1000 * 1000 * 1000
	N = 1000000000000
	sqrt := int(math.Sqrt(float64(N)))
	makePrimes(sqrt)
	Println(len(PRIMES))
	var candidates = make([]int64, 0)
	for true {
		var p = 2 * diff - 1
		if p >= N {
			break
		}
		candidates = append(candidates, p)
		diff += diff_delta
		diff_delta += 2
	}
	Println("#candidates", len(candidates))
	for _, prime := range PRIMES {
		var p = int64(prime)
		printlnEveryNTimes(10000, p)
		newCandidates := make([]int64, 0)
		for _, candidate := range candidates {
			if candidate <= p {
				res++
			} else if candidate % p != 0 {
				newCandidates = append(newCandidates, candidate)
			}
		}
		candidates = newCandidates
	}
	res += int64(len(candidates))
	Println(len(candidates))
	Println("Result:", res)
}

func main() {
	Println("starting...")
	startTime := time.Now()
	mymain()
	elapsed := time.Since(startTime).Seconds()
	Printf("Elapsed time: %.5f s\n", elapsed)
	Println("quitting...")
}

var PRIMES = make([]int, 0, 1270606)

func makePrimes(N int) {
	var x, y, n int
	nsqrt := math.Sqrt(float64(N))
	
	is_prime := make([]bool, N+1)
	
	for x = 1; float64(x) <= nsqrt; x++ {
		for y = 1; float64(y) <= nsqrt; y++ {
			n = 4 * (x * x) + y * y
			if n <= N && (n % 12 == 1 || n % 12 == 5) {
				is_prime[n] = !is_prime[n]
			}
			n = 3 * (x * x) + y * y
			if n <= N && n % 12 == 7 {
				is_prime[n] = !is_prime[n]
			}
			n = 3 * (x * x) - y * y
			if x > y && n <= N && n % 12 == 11 {
				is_prime[n] = !is_prime[n]
			}
		}
	}
	
	for n = 5; float64(n) <= nsqrt; n++ {
		if is_prime[n] {
			for y = n * n; y < N; y += n * n {
				is_prime[y] = false
			}
		}
	}
	
	is_prime[2] = true
	is_prime[3] = true
	
	for x = 0; x < len(is_prime) - 1; x++ {
		if is_prime[x] {
			PRIMES = append(PRIMES, x)
		}
	}
}

var _last_time_PrintlnRegularly = time.Now()

func printlnRegularly(deltaTime float64, s ...interface{}) {
	var elapsed = time.Since(_last_time_PrintlnRegularly).Seconds()
	if elapsed > deltaTime {
		Println(s...)
		_last_time_PrintlnRegularly = time.Now()
	}
}

var _counter_printlnEveryNTimes int64 = 0

func printlnEveryNTimes(n int64, s ...interface{}) {
	_counter_printlnEveryNTimes++
	if _counter_printlnEveryNTimes >= n {
		Println(s...)
		_counter_printlnEveryNTimes = 0
	}
}