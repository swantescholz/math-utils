
import "../myutil"
import matrices, strutils, algorithm



proc computeAlignmentFromParents*(s1, s2: string, parents: Matrix[char]): tuple[s1aligned: string, s2aligned: string] =
  const alignmentSymbol = '-'
  var (x, y) = (len(s1), len(s2))
  var (aligned1, aligned2) = ("", "")
  while x > 0 or y > 0:
    if parents[x,y] == 'B':
      dec x
      dec y
      aligned1 &= s1[x]
      aligned2 &= s2[y]
    elif parents[x,y] == 'U':
      dec y
      aligned1 &= alignmentSymbol
      aligned2 &= s2[y]
    else:
      assert parents[x,y] == 'L'
      dec x
      aligned1 &= s1[x]
      aligned2 &= alignmentSymbol
  aligned1.reverse()
  aligned2.reverse()
  return (aligned1, aligned2)

proc longestCommonSubsequenceLength*(a, b: string): int =
  let (w,h) = (len(a), len(b))
  var m = newMatrix[int](w+1,h+1, 0)
  for x in 1..w:
    for y in 1..h:
      if a[x] == b[y]:
        m[x,y] = m[x-1,y-1]+1
      else:
        m[x,y] = max(m[x-1,y], m[x,y-1])
  return m[w,h]

proc readFasta*(lines: seq[string]): seq[string] =
  result = newSeq[string]()
  var id = ""
  var dna = ""
  for line in lines:
    if len(line) == 0:
      continue
    if line[0] == '>':
      if len(dna) > 0:
        result.add(dna)
      id = line[1..^1]
      dna = ""
    else:
      dna &= line
  if len(dna) > 0:
    result.add(dna)
