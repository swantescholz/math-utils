import myutil
import strutils, times, sequtils, deques
import algorithm, os

const resPath = "../../../downloads"
# const filename = "test"
# const filename = "B-small-attempt0"
# const filename = "A-large"
# const filename = "B-small-practice"
const filename = "B-large-practice"

proc solve(q: var Deque[seq[string]]): string =
  result = ""
  let (ee,nn) = tuple2(q.popi)
  var ss = q.popi
  ss.sort(cmp)
  var e = ee
  var dd = initDeque[int]()
  for it in ss:
    dd.addLast(it)
  var h = 0
  while len(dd) > 0:
    if dd.peekFirst < e:
      e -= dd.popFirst
      inc h
    elif h > 0 and len(dd) > 1:
      e += dd.popLast
      dec h
    else:
      break
  result = $h

proc solveAll(tokens: seq[seq[string]]): string =
  var q = initDeque[seq[string]]()
  for line in tokens:
    q.push(line)
  var output = ""
  let numTestCases = parseInt(q.pop[0])
  for testCaseId in 1..numTestCases:
    var res = solve(q)
    assert res != nil, "solve returned nil"
    if not res.endsWith("\n"):
      res &= "\n"
    res = "Case #$: $" %% [$testCaseId, res]
    output &= res
    echo res[0..^2]
  return output

proc main() =
  echo "Starting..."
  var startCpuTime = cpuTime()
  var previousOutput = ""
  if existsFile("$/$.out" %% [resPath, filename]):
    previousOutput = readFile("$/$.out" %% [resPath, filename])
  let inputString = readFile("$/$.in" %% [resPath, filename])
  let inputLines = inputString.split("\n")
  let tokens = inputLines.map(proc(it: string): seq[string] = it.split(" "))
  let output = solveAll(tokens)
  writeFile("$/$.out" %% [resPath, filename], output)
  echo "CPU time [s]: $".fmt(formatFloat(cpuTime() - startCpuTime, format = ffDecimal, precision=6))
  if output == previousOutput:
    echo "==== SAME ===="
  else:
    echo "***** CHANGED *****"


main()
