package models

import play.api.db.slick.DatabaseConfigProvider
import scala.concurrent.Future
import slick.driver.JdbcProfile
import slick.driver.MySQLDriver.api._
import scala.concurrent.ExecutionContext.Implicits.global

import play.api.data.Form
import play.api.data.Forms._
import play.api.data.format.Formats._


case class User (id: Int = 0, username: String ="", passwd: String = "", fullname: String = "", 
    mobile: String ="", email : String ="", dob : String = "")
    
case class UserFormData (username: String ="", passwd: String = "", fullname: String = "", 
    mobile: String ="", email : String ="", dob : String = "")
    
case class UserLoginFormData (username: String ="", passwd: String = "")

case class UserPassModifyFormData( userid: Int =0, passwd: String = "")

class UserTableDef(tag: Tag) extends Table[User](tag, "user") {

  def id = column[Int]("id", O.PrimaryKey,O.AutoInc)
  def username = column[String]("username")
  def passwd = column[String]("passwd")
  def fullname= column[String]("fullname")
  def mobile = column[String]("mobile")
  def email = column[String]("email")
  def dob = column[String]("dob")

  override def * =
    (id, username, passwd, fullname, mobile, email, dob) <> (User.tupled, User.unapply)
}

object UserEditForm {

  val form = Form(
    mapping(
      "id" -> of[Int],
      "username" -> nonEmptyText,
      "passwd" -> nonEmptyText,
      "fullname" -> nonEmptyText,
      "mobile" -> nonEmptyText,
      "email" -> nonEmptyText,
      "dob" -> nonEmptyText
    )(User.apply)(User.unapply)
  )
}

object UserForm {

  val form = Form(
    mapping(
      "username" -> nonEmptyText,
      "passwd" -> nonEmptyText,
      "fullname" -> nonEmptyText,
      "mobile" -> nonEmptyText,
      "email" -> nonEmptyText,
      "dob" -> nonEmptyText
    )(UserFormData.apply)(UserFormData.unapply)
  )
}

object UserLoginForm {
  val form = Form(
      mapping(
        "username" -> nonEmptyText,
        "passwd" -> nonEmptyText
    )(UserLoginFormData.apply)(UserLoginFormData.unapply)
  )
}

object UserPassModifyForm{
  val form = Form(
      mapping(
          "userid" -> of[Int],
          "passwd" -> of[String]
      )(UserPassModifyFormData.apply)(UserPassModifyFormData.unapply)
  )
}

case class SearchMovieData(searchdb: String, searchterm: String, searchgenre: String)

object SearchMovieForm{
  val form = Form(
      mapping(
          "searchdb" -> of[String],
          "searchterm" -> of[String],
          "searchgenre" -> of[String]
      )(SearchMovieData.apply)(SearchMovieData.unapply)
  )
}

case class SearchUserData(searchdb: String, searchterm: String)

object SearchUserForm{
  val form = Form(
      mapping(
          "searchdb" -> of[String],
          "searchterm" -> of[String]
      )(SearchUserData.apply)(SearchUserData.unapply)
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
