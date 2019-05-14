import "../myutil"
import rosautils
import strutils, times, sequtils, deques
import algorithm, os, matrices, constants, tables, sets
import hashes, alignments

const resPath = "../../../downloads"
# const filename = "test"
const filename = "rosalind_laff (4)"

proc solve(q: var Deque[seq[string]], lines: seq[string]): string =
  let (a,b) = tuple2(readFasta(lines))
  maximumAlignment(a,b)


# =====================================
proc solveAll(tokens: seq[seq[string]], lines: seq[string]): string =
  var q = initDeque[seq[string]]()
  for line in tokens:
    q.push(line)
  var output = solve(q, lines)
  # echo output
  return output

proc main() =
  echo "Starting..."
  echo "==================================="
  var startCpuTime = cpuTime()
  var previousOutput = ""
  if existsFile("$/$.out" %% [resPath, filename]):
    previousOutput = readFile("$/$.out" %% [resPath, filename])
  let inputString = readFile("$/$.txt" %% [resPath, filename])
  let inputLines = inputString.split("\n")
  let tokens = inputLines.map(proc(it: string): seq[string] = it.split(" "))
  let output = solveAll(tokens, inputLines)
  writeFile("$/$.out" %% [resPath, filename], output)
  echo "==================================="
  echo "CPU time [s]: $".fmt(formatFloat(cpuTime() - startCpuTime, format = ffDecimal, precision=6))
  if output == previousOutput:
    echo "==== SAME ===="
  else:
    echo "***** CHANGED *****"


main()
