import unittest, "../myutil", rosautils, constants, matrices
import alignments, sets

echo "========================="



test "edit distance":
  let dist = editDistance("kitten", "sitting", matchingCost=0,
  substitutionCost=1, insertionCost=1, deletionCost=1)
  check(dist == 3)

test "matrix tests":
  var m = newMatrix[int](2,3,4)
  check(m[0,1] == 4)
  m[0,1] = 2
  check(m[0,1] == 2)
  expect(IndexError):
    discard m[2,3]

test "misc test":
  check((3..6).toSeq == @[3,4,5,6])

test "foo":
  check(1 == 1)
  let v = @[1, 2, 3]
  checkpoint($v)  # gets printed if following checks fail
  expect(IndexError):
    discard v[4]
