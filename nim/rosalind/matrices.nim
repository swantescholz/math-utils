import strutils


type
  Matrix*[T] = object  # indexed (x,y)
    w*: int
    h*: int
    m*: seq[T]

proc newMatrix*[T](width, height: int, initialValue: T): Matrix[T] =
  ## Initially populated with 0.
  result.w = width
  result.h = height
  newSeq(result.m, width*height)
  for i in 0..<width*height:
    result.m[i] = initialValue

proc index[T](this: Matrix[T], x,y: int): int {.inline.} =
  return y*this.w+x

proc `[]`*[T](this: Matrix[T], x,y: int): T =
  if x < 0 or x >= this.w:
    raise newException(IndexError, "matrix index out of range")
  if y < 0 or y >= this.h:
    raise newException(IndexError, "matrix index out of range")
  return this.m[this.index(x,y)]


proc `[]=`*[T](this: var Matrix[T], x,y: int, newValue: T) =
  if x < 0 or x >= this.w:
    raise newException(IndexError, "matrix index out of range")
  if y < 0 or y >= this.h:
    raise newException(IndexError, "matrix index out of range")
  this.m[this.index(x,y)] = newValue


proc `$`*[T](this: Matrix[T]): string =
  result = ""
  for y in 0..<this.h:
    for x in 0..<this.w:
      result &= $this[x,y]
      if x != this.w-1:
        result &= "\t"
    result &= "\n"
  result.removeSuffix()

when isMainModule:
  echo "matrix main"
  var m = newMatrix[int](2,3)
  assert m[1,2] == 0, "this should not fail"
