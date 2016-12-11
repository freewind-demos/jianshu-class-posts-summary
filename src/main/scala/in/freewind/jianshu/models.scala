package in.freewind.jianshu

import java.util.Date

case class User(name: String, jianshuId: String, buddyName: String, articles: List[Article]) {
  val jianshuUrl = s"http://jianshu.com/users/$jianshuId"
}

case class Article(id: String, title: String, reads: Int, comments: Int, likes: Int, sharedDate: Date) {
  val url = s"http://jianshu.com/p/$id"
}
