import unittest, "../myutil", rosautils, constants, matrices
import alignments, sets, algorithm, tables

echo "========================="

proc listFromDiffs(diffs: seq[int]): seq[int] =
  var diffs = diffs
  diffs.sort(system.cmp)
  var res = initSet[int]()
  res.incl(0)
  res.incl(diffs[^1])
  let maxDiff = diffs[^1]
  diffs = diffs[0..^2]
  var counts = newTable[int,int]()
  for it in diffs:
    if not (it in counts):
      counts[it] = 0
    inc counts[it]
  proc ff(index:int)
  proc gg(p,index: int) =
    var good = true
    for it in res:
      let d = abs(p-it)
      if d in counts:
        dec counts[d]
      if (not (d in counts)) or counts[d] < 0:
        good = false
    if good:
      res.incl(p)
      ff(index-1)
      res.excl(p)
    for it in res:
      let d = abs(p-it)
      if d in counts:
        inc counts[d]
  proc ff(index: int)=
    var p = diffs[index]
    echo p, res
    if len(res)*(len(res)+1) div 2 == len(diffs)+1:
      var s = newSeq[int]()
      for it in res:
        s.add(it)
      s.sort(cmp)
      echo s
      quit()
    gg(maxDiff-p, index)
    gg(p, index)
  ff(len(diffs)-1)

test "rosalind tmp test":
  var orig = @[0,3,4,9,15,44,22,465,8,2,685,89465,546]
  var diffs = newSeq[int]()
  for a in 0..<orig.len:
    for b in a+1..<orig.len:
      diffs.add(orig[b]-orig[a])
  diffs.sort(system.cmp)
  echo orig
  let got = listFromDiffs(diffs)
  check(got == orig)
