<?php

class Pair {
	public $a;
	public $b;
	
	public function __construct($a, $b) {
		$this->a = $a;
		$this->b = $b;
	}
}

function reverse_complement($dna) {
	$dna = strrev($dna);
	$dna = str_replace("A", "t", $dna);
	$dna = str_replace("T", "a", $dna);
	$dna = str_replace("G", "c", $dna);
	$dna = str_replace("C", "g", $dna);
	return strtoupper($dna);
}

?>