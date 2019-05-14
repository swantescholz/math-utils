import strutils, tables, sequtils

const BLOSUM62str = """
   A  C  D  E  F  G  H  I  K  L  M  N  P  Q  R  S  T  V  W  Y
A  4  0 -2 -1 -2  0 -2 -1 -1 -1 -1 -2 -1 -1 -1  1  0  0 -3 -2
C  0  9 -3 -4 -2 -3 -3 -1 -3 -1 -1 -3 -3 -3 -3 -1 -1 -1 -2 -2
D -2 -3  6  2 -3 -1 -1 -3 -1 -4 -3  1 -1  0 -2  0 -1 -3 -4 -3
E -1 -4  2  5 -3 -2  0 -3  1 -3 -2  0 -1  2  0  0 -1 -2 -3 -2
F -2 -2 -3 -3  6 -3 -1  0 -3  0  0 -3 -4 -3 -3 -2 -2 -1  1  3
G  0 -3 -1 -2 -3  6 -2 -4 -2 -4 -3  0 -2 -2 -2  0 -2 -3 -2 -3
H -2 -3 -1  0 -1 -2  8 -3 -1 -3 -2  1 -2  0  0 -1 -2 -3 -2  2
I -1 -1 -3 -3  0 -4 -3  4 -3  2  1 -3 -3 -3 -3 -2 -1  3 -3 -1
K -1 -3 -1  1 -3 -2 -1 -3  5 -2 -1  0 -1  1  2  0 -1 -2 -3 -2
L -1 -1 -4 -3  0 -4 -3  2 -2  4  2 -3 -3 -2 -2 -2 -1  1 -2 -1
M -1 -1 -3 -2  0 -3 -2  1 -1  2  5 -2 -2  0 -1 -1 -1  1 -1 -1
N -2 -3  1  0 -3  0  1 -3  0 -3 -2  6 -2  0  0  1  0 -3 -4 -2
P -1 -3 -1 -1 -4 -2 -2 -3 -1 -3 -2 -2  7 -1 -2 -1 -1 -2 -4 -3
Q -1 -3  0  2 -3 -2  0 -3  1 -2  0  0 -1  5  1  0 -1 -2 -2 -1
R -1 -3 -2  0 -3 -2  0 -3  2 -2 -1  0 -2  1  5 -1 -1 -3 -3 -2
S  1 -1  0  0 -2  0 -1 -2  0 -2 -1  1 -1  0 -1  4  1 -2 -3 -2
T  0 -1 -1 -1 -2 -2 -2 -1 -1 -1 -1  0 -1 -1 -1  1  5  0 -2 -2
V  0 -1 -3 -2 -1 -3 -3  3 -2  1  1 -3 -2 -2 -3 -2  0  4 -3 -1
W -3 -2 -4 -3  1 -2 -2 -3 -3 -2 -1 -4 -4 -2 -3 -3 -2 -3 11  2
Y -2 -2 -3 -2  3 -3  2 -1 -2 -1 -1 -2 -3 -1 -2 -2 -2 -1  2  7"""

proc readTable(s: string): Table[char, Table[char, int]] =
  result = initTable[char, Table[char, int]]()
  var lines = s.strip().split("\n").map(splitWhitespace)
  let keys = lines[0].map(proc(it:string): char = char(it[0]))
  for y, line in lines[1..^1]:
    for x in 0..<len(keys):
      let (kx, ky) = (keys[x], keys[y])
      let value = parseInt(line[x+1])
      if not (kx in result):
        result[kx] = initTable[char, int]()
      result[kx][ky] = value
      # result[kx][ky] = -20 # fake values
      # if kx == ky:
      #   result[kx][ky] = 20

const BLOSUM62* = readTable(BLOSUM62str)



#[
proc maximumAlignment(kk:int,s1, s2: string):
  tuple[score: int, s1aligned: string, s2aligned: string] =
  let (Left,Up,Diag) = (1'i8,2'i8,4'i8)
  let (la, lb) = (len(s1), len(s2))
  var m = newMatrix[int32](la+1, lb+1, 0)
  var parents = newMatrix[int8](la+1, lb+1, 0)
  for x in 1..la:
    m[x,0] = 0
    parents[x,0] = Left
  for y in 1..lb:
    m[0,y] = -int32(y)
    parents[0,y] = Up
  # echo s1
  # echo s2
  for y in 1..lb:
    for x in 1..la:
      var sub = m[x - 1, y - 1] + 1
      if s1[x-1]==s2[y-1]:
        sub -= 1
      var ins = m[x, y - 1] + 1
      var del = m[x - 1, y] + 1
      if y==lb:
        del += 1
      let res = min([sub, ins, del])
      m[x, y] = res
      if res == sub:
        # parents[x,y] = parents[x,y] xor Diag
      elif res == ins:
        # parents[x,y] = parents[x,y] xor Up
      else:
        # parents[x,y] = parents[x,y] xor Left

  # echo m
  # echo parents

  return (int(m[la,lb]), "", "")


]#
