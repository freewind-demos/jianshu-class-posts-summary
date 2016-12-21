package in.freewind.jianshu.reports

import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

import in.freewind.jianshu.User

object RecentArticleReport {

  def render(users: List[User], showEmptyUsers: Boolean, buddyTitle: String, fromDate: Date, targetFile: File) {
    val filteredUsers = users
      .map(user => user.copy(articles = user.articles.filter(_.sharedDate.after(fromDate))))
      .filter(user => user.articles.nonEmpty || (user.articles.isEmpty && showEmptyUsers))
      .sortBy(_.buddyName)
    val content = generateReport(filteredUsers, buddyTitle, fromDate)
    utils.applyTemplate(content, targetFile)
  }

  def generateReport(users: List[User], buddyTitle: String, fromDate: Date): String = {
    val html = <div>
      <h1>从{formatDate(fromDate)}到今天{formatDate(new Date())}所发布的文章</h1>
      <div>
        <ul>{
        users.zipWithIndex.map { case (user, index) =>
          <li>
            <div>
              <span><a href={user.jianshuUrl}>{user.name}</a></span>
              <span> &nbsp; &nbsp; &nbsp; </span>
              <span> ({buddyTitle}: {user.buddyName})</span></div>
              <ol>
                {user.articles match {
                case articles if articles.isEmpty => <li>无</li>
                case articles => articles.map(article => {
                  <li>
                    <a href={article.url}>{article.title}</a>
                    <span>&nbsp; &nbsp;</span>
                    <span>{formatDate(article.sharedDate)}</span>
                  </li>
                })
              }}
              </ol>
          </li>
        }
        }</ul>
      </div>
    </div>
    xml.Utility.trim(html).toString
  }

  def formatDate(date: Date) = {
    new SimpleDateFormat("yyyy-MM-dd").format(date)
  }

}
