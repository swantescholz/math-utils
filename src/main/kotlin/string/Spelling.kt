package string


fun spellNumber9(n: Int): String {
	when (n) {
		0 -> return ""
		1 -> return "one"
		2 -> return "two"
		3 -> return "three"
		4 -> return "four"
		5 -> return "five"
		6 -> return "six"
		7 -> return "seven"
		8 -> return "eight"
		9 -> return "nine"
	}
	return "<error>"
}

fun spellNumber19(n: Int): String {
	if (n < 10)
		return spellNumber9(n)
	when (n) {
		10 -> return "ten"
		11 -> return "eleven"
		12 -> return "twelve"
		13 -> return "thirteen"
		14 -> return "fourteen"
		15 -> return "fifteen"
		16 -> return "sixteen"
		17 -> return "seventeen"
		18 -> return "eighteen"
		19 -> return "nineteen"
		else -> return "error"
	}
}

fun spellNumber20_90(n: Int): String {
	when (n) {
		2 -> return "twenty"
		3 -> return "thirty"
		4 -> return "forty"
		5 -> return "fifty"
		6 -> return "sixty"
		7 -> return "seventy"
		8 -> return "eighty"
		9 -> return "ninety"
		else -> return "<error>"
	}
}

fun spellNumber99(n: Int): String {
	if (n < 20)
		return spellNumber19(n)
	return spellNumber20_90(n / 10) + spellNumber9(n % 10)
}

fun spellNumber999(n: Int): String {
	if (n < 100)
		return spellNumber99(n % 100)
	var s = spellNumber9(n / 100) + "hundred"
	var sb = spellNumber99(n % 100)
	if (sb.length > 0) {
		s += "and" + sb
	}
	return s
}
