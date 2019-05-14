import "../myutil"
import rosautils
import strutils, times, sequtils, deques
import algorithm, os, matrices, constants, tables, sets
import hashes

proc maximumAlignment*(s1, s2: string) =
  const alignmentSymbol = '-'
  let (la, lb) = (len(s1), len(s2))
  var mbest = newMatrix[int32](la+1, lb+1, 0)
  var ml = newMatrix[int32](la+1, lb+1, 0)
  var mu = newMatrix[int32](la+1, lb+1, 0)
  for x in 1..la:
    ml[x,0] = -10 - int32(x)
    mu[x,0] = -1000000000
  for y in 1..lb:
    mu[0,y] = -10 - int32(y)
    ml[0,y] = -1000000000
  for y in 1..lb:
    if y mod 100 == 0:
      print y
    for x in 1..la:
      var sub = mbest[x - 1, y - 1] + BLOSUM62[s1[x-1]][s2[y-1]]
      var ins = max([mbest[x, y - 1] - 11, mu[x,y-1] - 1])
      var del = max([mbest[x - 1, y] - 11, ml[x-1,y] - 1])
      let best = max([sub, ins, del, 0])
      mbest[x, y] = int32(best)
      mu[x,y] = ins
      ml[x,y] = del
  # echo mbest
  # echo()
  # echo mu
  # echo()
  # echo ml
  # echo()
  var (x, y) = (la, lb)
  for ix in 0..la:
    for iy in 0..lb:
      if mbest[ix,iy] > mbest[x,y]:
        (x,y) = (ix,iy)
  let bestScore = mbest[x, y]
  var (aligned1, aligned2) = ("", "")
  print(bestScore)
  print("=================")
  while mbest[x,y] > 0:
    echo($x & " " & $y)
    if mbest[x,y] == mbest[x - 1, y - 1] + BLOSUM62[s1[x-1]][s2[y-1]]:
      dec x
      dec y
      aligned1 &= s1[x]
      aligned2 &= s2[y]
    elif mbest[x,y] == mbest[x,y-1] - 11: # up
      dec y
      aligned1 &= alignmentSymbol
      aligned2 &= s2[y]
    elif mbest[x,y] == mbest[x-1,y] - 11: # left
      dec x
      aligned1 &= s1[x]
      aligned2 &= alignmentSymbol
    elif mbest[x,y] == mu[x,y-1] - 1: # up
      dec y
      aligned1 &= alignmentSymbol
      aligned2 &= s2[y]
      while mu[x,y] == mu[x,y-1] - 1:
        dec y
        aligned1 &= alignmentSymbol
        aligned2 &= s2[y]
      dec y
      aligned1 &= alignmentSymbol
      aligned2 &= s2[y]
    else:
      if mbest[x,y] != ml[x-1,y] - 1:
        print(x, y, mbest[x,y], ml[x-1,y], mbest[x-1,y])
        raise newException(Exception, "foobar")
      dec x
      aligned1 &= s1[x]
      aligned2 &= alignmentSymbol
      while ml[x,y] == ml[x-1,y] - 1:
        dec x
        aligned1 &= s1[x]
        aligned2 &= alignmentSymbol
      dec x
      aligned1 &= s1[x]
      aligned2 &= alignmentSymbol
  aligned1.reverse()
  aligned2.reverse()
  var score = 0
  var lastOp = "foo"
  for i in 0..<len(aligned1):
    if aligned1[i] == '-':
      if lastOp == "ins":
        score -= 1
      else:
        score -= 11
      lastOp = "ins"
    elif aligned2[i] == '-':
      if lastOp == "del":
        score -= 1
      else:
        score -= 11
      lastOp = "del"
    else:
      score += BLOSUM62[aligned1[i]][aligned2[i]]
      lastOp = "sub"
  print(bestScore, score, bestScore == score)
  print bestScore
  aligned1 = aligned1.replace("-", "")
  aligned2 = aligned2.replace("-", "")
  print aligned1
  print aligned2




proc editDistance*(s1, s2: string, matchingCost = 0, substitutionCost = 1, insertionCost = 1, deletionCost = 1): int =
  var (s1, s2) = if len(s1) <= len(s2): (s1, s2) else: (s2, s1)
  var distances = (0..len(s1)).toSeq
  for y, c2 in s2:
      var newDistances = @[y+1]
      for x, c1 in s1:
          var sub = distances[x]
          if c1 == c2:
              sub += matchingCost
          else:
              sub += substitutionCost
          let ins = distances[x + 1] + insertionCost
          let dels = newDistances[^1] + deletionCost
          let bestCost = min([sub, ins, dels])
          newDistances.add(bestCost)
      distances = newDistances
  return distances[^1]
