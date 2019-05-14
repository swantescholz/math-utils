<?php

require_once "misc.php";
define("nl", "\n");
$input_name = "test";
$input_name = "rosalind_grep";
//$input_name .= " (1)";
$res_root = "../../../downloads";
$input_path = "$res_root/$input_name.txt";
$input_file = fopen($input_path, "r") or die("Unable to open file!");
$lines = fread($input_file, filesize($input_path));
$output_file = fopen("$res_root/$input_name.out", "w") or die("Unable to open file!");
fclose($input_file);
$lines = explode("\n", $lines);
while (empty($lines[count($lines)-1])) {
	array_pop($lines);
}
$orig_lines = $lines;
function write($txt) {
	global $output_file;
	echo $txt;
	fwrite($output_file, $txt);
}
// =============================================
//$lines = array_unique($lines);
$k = strlen($lines[0])-1;
$num_edges = count($lines);
$dna_len = $num_edges;
$edges = array();
foreach ($lines as $line) {
	$a = substr($line, 0, $k);
	$b = substr($line, 1, $k);
	if (!array_key_exists($a, $edges))
		$edges[$a] = array();
	if (!array_key_exists($b, $edges[$a]))
		$edges[$a][$b] = 0;
	$edges[$a][$b] += 1;
}

function f($node, $prefix) {
	global $dna_len, $k, $edges;
	if (strlen($prefix) == $dna_len) {
		write($prefix . nl);
		return;
	}
	foreach ($edges[$node] as $dest => $done) {
		if ($done == 0)
			continue;
		$edges[$node][$dest] -= 1;
		f($dest, $prefix . $node[0]);
		$edges[$node][$dest] += 1;
	}
}

$edges[substr($lines[0], 0, $k)][substr($lines[0], 1, $k)] -= 1;
f(substr($lines[0], 1, $k), $lines[0][0]);
// =============================================
fclose($output_file);
echo "Done." . nl;

?>