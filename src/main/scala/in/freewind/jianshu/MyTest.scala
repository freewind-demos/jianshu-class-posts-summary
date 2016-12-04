package in.freewind.jianshu

import java.text.SimpleDateFormat

object MyTest extends App {

  //  val s = 1 #:: 2 #:: Stream.empty

  val s = Stream.from(1).map(x => x * x).takeWhile(p => p < 100)
  println(s.toList)


}
