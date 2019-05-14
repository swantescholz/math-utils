from collections import defaultdict
from typing import List


def hex2base64(hex_string: str) -> bytes:
	import base64
	return base64.encodebytes(bytes.fromhex(a)).decode()[:-1]


def bytes_xor(a: bytes, b: bytes) -> bytes:
	return bytes(x ^ y for (x, y) in zip(a, b))

def bytes_xor1(a: bytes, b: int) -> bytes:
	return bytes(x ^ b for x in a)


ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
LETTER_FREQUENCIES = {'E': 0.1249, 'T': 0.0928, 'A': 0.0804, 'O': 0.0764, 'I': 0.0757, 'N': 0.0723, 'S': 0.0651, 'R': 0.0628, 'H': 0.0505,
                      'L': 0.0407, 'D': 0.0382, 'C': 0.0334, 'U': 0.0273, 'M': 0.0251, 'F': 0.0240, 'P': 0.0214, 'G': 0.0187, 'W': 0.0168,
                      'Y': 0.0166, 'B': 0.0148, 'V': 0.0105, 'K': 0.0054, 'X': 0.0023, 'J': 0.0016, 'Q': 0.0012, 'Z': 0.0009}

def score_english_text_simple(english_text: str) -> float:
	diff = 0.0
	freqs = defaultdict(float)
	for letter in english_text.upper():
		freqs[letter] += 1
	for letter in freqs.keys():
		freqs[letter] /= len(english_text)
	for letter in ALPHABET:
		diff += abs(freqs[letter] - LETTER_FREQUENCIES[letter])
	for letter in freqs.keys():
		if letter not in ALPHABET:
			diff += freqs[letter]
	return diff
	
	


def myassert(expected, actual):
	print(expected)
	print(actual)
	assert expected == actual


if __name__ == "__main__":
	a = "1b37373331363f78151b7f2b783431333d78397828372d363c78373e783a393b3736"
	for c in ALPHABET:
		x = bytes_xor1(bytes.fromhex(a), ord(c)).decode()
		print(c, score_english_text_simple(x), x)
