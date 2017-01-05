package in.freewind.jianshu

import java.text.SimpleDateFormat

import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.model.{Document, Element}
import net.ruippeixotog.scalascraper.scraper.ContentExtractors.{element => _, elementList => _}
import org.apache.commons.lang3.StringUtils
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL.Parse._

object ArticlePages {

  def getAllArticles(userId: String): List[Article] = {
    val pages = Stream.from(1).map(page => ArticlePages.findArticlesOnPage(userId, page))
      .takeWhile(articlesOnPage => articlesOnPage.nonEmpty)
    pages.map(_.getOrElse(List.empty)).toList.flatten
  }

  private def getArticleId(href: String): Option[String] = {
    """/p/(.*)""".r.findFirstMatchIn(href).map(_.group(1))
  }

  private def getCount(meta: Element, keyClass: String): Option[Int] = {
    meta.children
      .find(child => (child >?> element(s"i.$keyClass")).isDefined)
      .map(_.text.trim.toInt)
  }

  def isArticleTabActive(doc: Document): Boolean = {
    (doc >> element(".trigger-menu .active"))
      .exists(li => (li >?> element(".ic-articles")).isDefined)
  }

  def findArticlesOnPage(userId: String, page: Int): Option[List[Article]] = {
    val browser = JsoupBrowser()
    val url = s"http://www.jianshu.com/u/$userId?page=$page"
    val doc = browser.get(url)
    if (isArticleTabActive(doc)) {
      val listContainer = doc >> element("#list-container")
      val articleElements = listContainer >> elementList(".note-list li")
      val articles = articleElements.map { article =>
        val articleTitleLink = article >> element("a.title")
        val title = articleTitleLink.text
        val idOpt = getArticleId(articleTitleLink.attr("href"))
        val footer = article >> element(".meta")
        val reads = getCount(footer, "ic-list-read").getOrElse(0)
        val comments = getCount(footer, "ic-list-comments").getOrElse(0)
        val likes = getCount(footer, "ic-list-like").getOrElse(0)
        val sharedAtTime = (article >> element(".time")).attr("data-shared-at")
        val sharedDate = new SimpleDateFormat("yyyy-MM-dd").parse(StringUtils.substringBefore(sharedAtTime, "T"))
        Article(id = idOpt.get, title = title, reads = reads, comments = comments, likes = likes, sharedDate = sharedDate)
      }
      println(s"Found ${articleElements.size} articles on page: ${url}")
      Some(articles)
    } else None
  }
}

object Main extends App {
  ArticlePages.getAllArticles("6d96978eeefb")
}
