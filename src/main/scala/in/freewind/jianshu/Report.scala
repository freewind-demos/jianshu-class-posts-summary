package in.freewind.jianshu

object Report {

  def render(users: List[User], buddyTitle: String) {
    val tableHeader = List(
      s"| 序号 | 姓名 | 文章数 | 阅读量 | 收到评论 | 收到喜欢 | ${buddyTitle}姓名 |",
      "| :---: | :---: | :-------: | :-------: | :-----: | :-------: | :-------: |"
    )
    val tableBody = users.zipWithIndex.map { case (user, index) =>
      s"| ${index + 1} " +
        s"| [${user.name}](${user.jianshuUrl}) " +
        s"| ${user.articles.size} " +
        s"| ${user.articles.map(_.reads).sum} " +
        s"| ${user.articles.map(_.comments).sum} " +
        s"| ${user.articles.map(_.likes).sum} " +
        s"| ${user.buddyName} " +
        "|"
    }
    val rows = tableHeader ::: tableBody
    rows.foreach(println)
  }

}
