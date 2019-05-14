import strutils, times, sequtils, deques

type
  Pair*[A,B] = tuple[a:A,b:B]
  Triple*[A,B,C] = tuple[a:A,b:B,c:C]
  Quadruple*[A,B,C,D] = tuple[a:A,b:B,c:C,d:D]
  long* = int64
  mint* = int32
proc `$`*[A,B](self: Pair[A,B]): string =
  $self.a & " " & $self.b
proc `$`*[A,B,C](self: Triple[A,B,C]): string =
  $self.a & " " & $self.b & " " & $self.c
proc `$`*[A,B,C,D](self: Quadruple[A,B,C,D]): string =
  $self.a & " " & $self.b & " " & $self.c & " " & $self.d

proc toSeq*[T](self: Slice[T]): seq[T] =
  result = newSeq[T]()
  for i in self.a..self.b:
    result.add(i)



proc tuple1*[T](s: openArray[T]): tuple[a: T] = tuple(a: s[0])
proc tuple2*[T](s: openArray[T]): tuple[a: T, b: T] = (s[0], s[1])
proc tuple3*[T](s: openArray[T]): tuple[a: T, b: T, c: T, d: T, e: T, f: T] = (s[0], s[1], s[2])
proc tuple4*[T](s: openArray[T]): tuple[a: T, b: T, c: T, d: T, e: T, f: T] = (s[0], s[1], s[2], s[3])
proc tuple5*[T](s: openArray[T]): tuple[a: T, b: T, c: T, d: T, e: T, f: T] = (s[0], s[1], s[2], s[3], s[4])
proc tuple6*[T](s: openArray[T]): tuple[a: T, b: T, c: T, d: T, e: T, f: T] = (s[0], s[1], s[2], s[3], s[4], s[5])

proc push*[T](deque: var Deque[T], newElement: T) = deque.addLast(newElement)
proc pop*[T](deque: var Deque[T]): T = deque.popFirst()
proc popi*(deque: var Deque[seq[string]]): seq[int] = deque.popFirst().map(parseInt)
proc peek*[T](deque: var Deque[T]): T = deque.peekFirst()

proc print*(a: varargs[string, `$`]) =
  echo join(a, " ")

proc fmt*(formatstr: string, a: varargs[string, `$`]): string =
  let newFormatstr = formatstr.replace("$", "$#")
  return strutils.format(newFormatstr, a)

proc `%%`*(formatstr: string, a: varargs[string, `$`]): string =
  let newFormatstr = formatstr.replace("$", "$#")
  return strutils.format(newFormatstr, a)
