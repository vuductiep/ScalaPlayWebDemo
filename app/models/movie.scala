package models

import play.api.Play
import play.api.db.slick.DatabaseConfigProvider
import scala.concurrent.Future
import slick.driver.JdbcProfile
import slick.driver.MySQLDriver.api._
import scala.concurrent.ExecutionContext.Implicits.global

import play.api.data.Form
import play.api.data.Forms._
import play.api.data.format.Formats._

case class Movie (id: Int = 0, title: String ="", release: Int = 1900, genre: String = "", rating: Int =0)

//class MovieTableDef(tag: Tag) extends Table[Movie](tag, "movie") {
//
//  def id = column[Int]("id", O.PrimaryKey,O.AutoInc)
//  def firstName = column[String]("title")
//  def lastName = column[Int]("release")
//  def mobile = column[String]("genre")
//  def email = column[String]("comment")
//
//  override def * =
//    (id, firstName, lastName, mobile, email) <>(User.tupled, User.unapply)
//}

class MovieTableDef(tag: Tag) extends Table[Movie](tag, "movieinfo") {

  def id = column[Int]("id", O.PrimaryKey,O.AutoInc)
  def title = column[String]("title")
  def release = column[Int]("release")
  def genre= column[String]("genre")
  def comment = column[Int]("rating")

  override def * =
    (id, title, release, genre, comment) <>(Movie.tupled, Movie.unapply)
}

case class MovieComment(id: Int=0, mvid: Int=0, userid: Int=0, username: String = "", comment: String ="", rating: Int=5)

case class MovieCommentFormData(mvid: Int=0, userid: Int, username: String = "", comment: String ="", rating: Int=5)


class MovieCommentTableDef(tag: Tag) extends Table[MovieComment](tag, "moviecomment"){
  def id = column[Int]("id", O.PrimaryKey,O.AutoInc)
  def mvid = column[Int]("mvid")
  def userid = column[Int]("userid")
  def username = column[String]("username")
  def comment = column[String]("comment")
  def rating = column[Int]("rating")
  
  override def * =
    (id, mvid, userid, username, comment, rating) <> (MovieComment.tupled, MovieComment.unapply)
}

object MovieCommentForm {
  val form = Form(
      mapping(
          "mvid" -> of[Int],
          "userid" -> of[Int],
          "username" -> of[String],
          "comment" -> of[String],
          "rating" -> of[Int]
    )(MovieCommentFormData.apply)(MovieCommentFormData.unapply)
  )
}

//object Movies  {
//
//  val dbConfig = DatabaseConfigProvider.get[JdbcProfile]
//
//  val movies = TableQuery[MovieTableDef]
//
////  def add(user: User): Future[String] = {
////    dbConfig.db.run(users += user).map(res => "User successfully added").recover {
////      case ex: Exception => ex.getCause.getMessage
////    }
////  }
////
////  def delete(id: Long): Future[Int] = {
////    dbConfig.db.run(users.filter(_.id === id).delete)
////  }
//
//  def get(, id: Int): Future[Option[Movie]] = {
//    dbConfig.db.run(movies.filter(_.id === id).result.headOption)
//  }
//
//  def listAll: Future[Seq[Movie]] = {
//    dbConfig.db.run(movies.result)
//  }
//
//}
