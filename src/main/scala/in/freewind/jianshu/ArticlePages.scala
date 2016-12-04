package in.freewind.jianshu

import java.text.SimpleDateFormat

import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.model.Element
import net.ruippeixotog.scalascraper.scraper.ContentExtractors._
import org.apache.commons.lang3.StringUtils

import java.text.SimpleDateFormat

import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL.Parse._
import net.ruippeixotog.scalascraper.model.Element
import net.ruippeixotog.scalascraper.scraper.ContentExtractors.{element => _, elementList => _, _}
import org.apache.commons.lang3.StringUtils

object ArticlePages {

  def getAllArticles(userId: String): List[Article] = {
    val pages = Stream.from(1).map(page => ArticlePages.findArticlesOnPage(userId, page))
      .takeWhile(articlesOnPage => articlesOnPage.nonEmpty)
    pages.toList.flatten
  }

  private def getArticleId(href: String): Option[String] = {
    """/p/(.*)""".r.findFirstMatchIn(href).map(_.group(1))
  }

  private def getCount(footer: Element, key: String): Option[Int] = {
    val Number = s""".*\\s+(\\d+)\\s*""".r
    footer.children.map(_.text).find(text => {
      text.contains(key)
    }).map {
      case Number(reads) => reads.toInt
    }
  }

  def findArticlesOnPage(userId: String, page: Int): List[Article] = {
    val browser = JsoupBrowser()
    val url = s"http://www.jianshu.com/users/$userId/latest_articles?page=$page"
    val doc = browser.get(url)
    val listContainer = doc >> element("#list-container")
    val articleElements = listContainer >> elementList(".article-list li")
    val articles = articleElements.map { article =>
      val articleTitleLink = article >> element(".title a")
      val title = articleTitleLink.text
      val idOpt = getArticleId(articleTitleLink.attr("href"))
      val footer = article >> element(".list-footer")
      val reads = getCount(footer, "阅读").getOrElse(0)
      val comments = getCount(footer, "评论").getOrElse(0)
      val likes = getCount(footer, "喜欢").getOrElse(0)
      val sharedAtTime = (article >> element(".time")).attr("data-shared-at")
      val sharedDate = new SimpleDateFormat("yyyy-MM-dd").parse(StringUtils.substringBefore(sharedAtTime, "T"))
      Article(id = idOpt.get, title = title, reads = reads, comments = comments, likes = likes, sharedDate = sharedDate)
    }
    println(s"Found ${articleElements.size} articles on page: ${url}")
    articles
  }
}
