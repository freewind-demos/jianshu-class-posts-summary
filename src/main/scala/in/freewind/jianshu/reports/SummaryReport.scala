package in.freewind.jianshu.reports

import java.io.File

import in.freewind.jianshu.User

object SummaryReport {

  def render(users: List[User], buddyTitle: String, targetFile: File) {
    val content = generateReport(users, buddyTitle)
    utils.applyTemplate(content, targetFile)
  }

  def generateReport(users: List[User], buddyTitle: String) = {
    val table = <table>
      <thead>
        <tr>
          <th>序号</th>
          <th>姓名</th>
          <th>文章数</th>
          <th>阅读量</th>
          <th>收到评论</th>
          <th>收到喜欢</th>
          <th>{buddyTitle}姓名</th>
        </tr>
      </thead>
      <tbody>
        {users.zipWithIndex.map { case (user, index) =>
        <tr>
          <td>{index + 1}</td>
          <td><a href={user.jianshuUrl}>{user.name}</a></td>
          <td>{user.articles.size}</td>
          <td>{user.articles.map(_.reads).sum}</td>
          <td>{user.articles.map(_.comments).sum}</td>
          <td>{user.articles.map(_.likes).sum}</td>
          <td>{user.buddyName}</td>
        </tr>
      }}
      </tbody>
    </table>
    xml.Utility.trim(table).toString
  }

}
