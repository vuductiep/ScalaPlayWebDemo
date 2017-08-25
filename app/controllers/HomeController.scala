package controllers

import javax.inject.Inject

import models._

import play.api.mvc.{Action, Controller}
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global

import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile
import slick.driver.MySQLDriver.api._


import play.api.Logger
import scala.util.{Try, Success, Failure}


class HomeController @Inject()(dbConfigProvider: DatabaseConfigProvider, environment: play.api.Environment) extends Controller {
  
  val dbConfig = dbConfigProvider.get[JdbcProfile]
  val movies = TableQuery[MovieTableDef]
  val movieDefault : Movie = Movie()
  val comments = TableQuery[MovieCommentTableDef]
  val mvCmtDefault : MovieComment = MovieComment()
  val users = TableQuery[UserTableDef]
  val userDefault : User = User()
  
  // Movie related functions
  def getMoviebyId(id: Int): Future[Option[Movie]] = {
    dbConfig.db.run(movies.filter(_.id === id).result.headOption)
  }
  
  def listAllMovie: Future[Seq[Movie]] = {
    dbConfig.db.run(movies.result)
  }
  
  // Read and write comment
  def listCommentbyMovieId(movieId: Int): Future[Seq[MovieComment]] = {
    dbConfig.db.run(comments.filter(_.mvid === movieId).result)
  }
  
  def listCommentbyUserId(userId: Int): Future[Seq[MovieComment]] = {
    dbConfig.db.run(comments.filter(_.userid === userId).result)
  }
  
  def commentMovie(cmt: MovieComment) : Future[String] = {
    dbConfig.db.run(comments += cmt).map(res => "Comment sucessfully added").recover {
      case ex: Exception => ex.getCause.getMessage
    }
  }

  // User related functions
  def getUserbyId(id: Int): Future[Option[User]] = {
    dbConfig.db.run(users.filter(_.id === id).result.headOption)
  }
  
  def getUserbyUsername(uname: String): Future[Option[User]] = {
    dbConfig.db.run(users.filter(_.username === uname).result.headOption)
  }
  
  def addUser(user: User): Future[String] = {
    dbConfig.db.run{
      
      users += user
    }.map(res => "User successfully added").recover {
      case ex: Exception => ex.getCause.getMessage
    }
  }
  
  def deleteUserbyId(id: Int): Future[Int] = {
    dbConfig.db.run(users.filter(_.id === id).delete)
  }
  
  def updateUser(uid: User): Future[String] = {
    dbConfig.db.run(
        users.filter(_.id === uid.id).update(uid).map(_.toString())
    ).recover{
      case ex: Exception => ex.getCause.getMessage
    }
  }
  
  
  def listAllUser() : Future[Seq[User]] = {
    dbConfig.db.run(users.result)
  }
  
  def listAllUserbyName(alluser: Seq[User], name: String) : Future[Seq[User]] = {
    var filteredUser : Seq[User] = Seq()
    println("reach for list")
    val futureSeq : Future[Seq[User]] = Future(Seq())
    futureSeq map {res => 
      for (u1 <- alluser){
        //Logger.info("u1: " + u1)
        if(u1.fullname.toLowerCase.contains(name)){
          Logger.info("Found a user matched search term: " + u1)
          if (filteredUser == Nil){
            filteredUser = u1 :: Nil
          } else{
            filteredUser = filteredUser :+ u1
            //Logger.info("Filtered user not nil")
          }  
        }
      }
      Logger.info("fiteredUser : " + filteredUser)
      filteredUser
    }
  }
  
  
  def listAllMoviebyName(allmv: Seq[Movie], name: String) : Future[Seq[Movie]] = {
    var filteredMovie : Seq[Movie] = Seq()
    println("reach for list")
    val futureSeq : Future[Seq[Movie]] = Future(Seq())
    Logger.info("Filtered name" + name)
    futureSeq map {res => 
      for (u1 <- allmv){
        //Logger.info("u1: " + u1)
        if(u1.title.toLowerCase.contains(name)){
          Logger.info("Found a movie matched search term: " + u1)
          if (filteredMovie == Nil){
            filteredMovie = u1 :: Nil
          } else{
            filteredMovie = filteredMovie :+ u1
            //Logger.info("Filtered user not nil")
          }  
        }
      }
      Logger.info("fiteredMovie : " + filteredMovie)
      filteredMovie
    }
  }
  
  println("Start Home Controller")
  val movieSeq = listAllMovie
  var mvSeq : Seq[Movie] = Seq()
  var readMVdb = movieSeq.map(mv => mvSeq = mv)
  //Await.ready(readMVdb, Duration.Inf)
  
  def index = Action.async { implicit request =>
    request.session.get("connected").map {
      userName =>
        {
          val userInfoFuture = getUserbyUsername(userName)
          var userInfo = User()
          userInfoFuture.map{
            extractUSERinfo => 
            userInfo = extractUSERinfo.getOrElse(userDefault)
            //Logger.info("Extracting movie information")
          }
          listAllMovie map { imovies =>
            Ok(views.html.index(userInfo, imovies))
          }
        }
    }.getOrElse(listAllMovie map { imovies => Ok(views.html.index(userDefault, imovies)) })
  }
  
  def LoginUser() = Action.async { implicit request =>
    UserLoginForm.form.bindFromRequest.fold(
      // if any error in submitted data
      //errorForm => movieSeq.map{im => Ok(views.html.index(im))},
      errorForm => movieSeq.map{im => Redirect(routes.HomeController.index())},
      data => {
        //val newUser = User(0, data.firstName, data.lastName, data.mobile, data.email)
        //val futureString = scala.concurrent.Future {""}        
        val userInfo = getUserbyUsername(data.username)
        userInfo.map{u1 =>
          val u1name = u1.getOrElse(userDefault).username
          val u1pass = u1.getOrElse(userDefault).passwd
          val u1user = u1.getOrElse(userDefault)
          if ( (u1name == data.username) && (u1pass == data.passwd) ){
            Logger.info("User " + data.username + " logged in ")
            Redirect(routes.HomeController.index()).withSession("connected" -> u1name)
          } else {
            Logger.info("Wrong username or password: " + data.username)
            //Ok("No such user: " + data.username)
            Redirect(routes.HomeController.LoginFailed())
          }}
      })
  }
  
  def LogoutUser() = Action.async { implicit request =>
    val futureString = scala.concurrent.Future {""}
        futureString.map(str => Redirect(routes.HomeController.index()).withNewSession)
  }
  
  def LoginFailed() = Action.async { implicit request =>
    request.session.get("connected").map {
      userName =>
        {
          val userInfoFuture = getUserbyUsername(userName)
          var userInfo = User()
          userInfoFuture.map{
            extractUSERinfo => 
            userInfo = extractUSERinfo.getOrElse(userDefault)
            //Logger.info("Extracting movie information")
          }
          listAllMovie map { imovies =>
            Ok(views.html.loginfailed(userInfo, imovies))
          }
        }
    }.getOrElse(listAllMovie map { imovies => Ok(views.html.loginfailed(userDefault, imovies)) })
  }
  
  def SignupUser() = Action.async { implicit request =>
    UserForm.form.bindFromRequest.fold(
      // if any error in submitted data
      //errorForm => movieSeq.map{im => Ok(views.html.index(im))},
      errorForm => movieSeq.map{im => Redirect(routes.HomeController.index())},
      data => {
        val newUser = User(0, data.username, data.passwd, data.fullname, data.mobile, data.email, data.dob)
        val res = addUser(newUser).map(restr => Logger.info(restr))
        Logger.info("Add new user successfully: " + data.username)
        val futureString = scala.concurrent.Future {""}
        futureString.map(str => Redirect(routes.HomeController.index()).withSession("connected" -> data.username) )
      })
  }
  
  def movieinfo(mvid : Int) = Action.async { implicit request =>
    val mvinfoFuture = getMoviebyId(mvid)
    var mvinfo = Movie()
    mvinfoFuture.map{
      extractMVinfo => 
      mvinfo = extractMVinfo.getOrElse(movieDefault)
      //Logger.info("Extracting movie information")
    }
    
    val mvCommentFuture = listCommentbyMovieId(mvid)
    var mvComment : Seq[MovieComment] = Seq()
    mvCommentFuture.map{
      extractMVinfo => mvComment = extractMVinfo
      //Logger.info("Extracting movie comment")
    }
    
    request.session.get("connected").map {
      userName =>
        {
          val userInfoFuture = getUserbyUsername(userName)
          var userInfo = User()
          userInfoFuture.map{
            extractUSERinfo => 
            userInfo = extractUSERinfo.getOrElse(userDefault)
            //Logger.info("Extracting user information")
          }
          
          Logger.info("Requesting movie info page")
          listAllMovie map { imovies =>
             Ok(views.html.movieinfo(userInfo, imovies, mvinfo, mvComment))
//            mvinfo.map{extractMVinfo => 
//              Ok(views.html.movieinfo(userName, imovies, extractMVinfo.getOrElse(movieDefault)))
//            }
          }
        }
    }.getOrElse{
        Logger.info("Requesting movie info page - NA user")
        listAllMovie map { imovies =>
          Ok(views.html.movieinfo(userDefault, imovies, mvinfo, mvComment))
//          mvinfo.map{extractMVinfo => 
//            Ok(views.html.movieinfo("NA", imovies, extractMVinfo.getOrElse(movieDefault)))
//          }
        } 
      }
  }
  
  def addMovieComment = Action.async { implicit request =>
      MovieCommentForm.form.bindFromRequest.fold(
      // if any error in submitted data
      // Todo : handling error in form
        errorForm => movieSeq.map{im => Redirect(routes.HomeController.index())},
        data => {
          val newComment = MovieComment(0, data.mvid, data.userid, data.username, data.comment, data.rating)
          val res = commentMovie(newComment).map(restr => Logger.info(restr))
          Logger.info("Add new comment successfully by user " + data.username)
          val futureString = scala.concurrent.Future {""}
          futureString.map(str => Redirect(routes.HomeController.movieinfo(data.mvid)).withSession("connected" -> data.username) )
      })
  }
  
//  def editUserinfo {
//    
//  }
  
  def updateUserInfo = Action.async { implicit request =>
       UserEditForm.form.bindFromRequest.fold(
      // if any error in submitted data
      //errorForm => movieSeq.map{im => Ok(views.html.index(im))},
        errorForm => movieSeq.map{im => Redirect(routes.HomeController.index())},
        data => {
          val newUser = User(data.id, data.username, data.passwd, data.fullname, data.mobile, data.email, data.dob)
          val res = updateUser(newUser).map(restr => Logger.info(restr))
          Logger.info("Update information successfully: " + data.username)
          val futureString = scala.concurrent.Future {""}
          futureString.map(str => Redirect(routes.HomeController.userinfo()))
      })
  }
  
  def userinfo = Action.async { implicit request =>
    request.session.get("connected").map {
      userName =>
        {
          val userInfoFuture = getUserbyUsername(userName)
          var userInfo = User()
          userInfoFuture.map{
            extractUSERinfo => 
            userInfo = extractUSERinfo.getOrElse(userDefault)
            //Logger.info("Extracting movie information")
          }
          listAllMovie map { imovies =>
            Ok(views.html.userinfo(userInfo, imovies))
          }
        }
    }.getOrElse(listAllMovie map { imovies => Ok(views.html.userinfo(userDefault, imovies)) })
  }
  
  def deleteUser (id: Int) = Action.async { implicit request =>
    deleteUserbyId(id)
    val futureString = scala.concurrent.Future {""}
    futureString.map(str => Redirect(routes.HomeController.adminControl()) )
  }
  
  def adminControl = Action.async { implicit request =>
    request.session.get("connected").map {
      userName =>
        {
          // Get current logged in user
          val userInfoFuture = getUserbyUsername(userName)
          var userInfo = User()
          userInfoFuture.map{
            extractUSERinfo => 
            userInfo = extractUSERinfo.getOrElse(userDefault)
            //Logger.info("Extracting movie information")
          }
          
          //Get all users
          var allusers : Seq[User] = Seq()
          listAllUser.map(allpeople => allusers = allpeople)
          
          listAllMovie map { imovies =>
            Ok(views.html.admin(userInfo, imovies, allusers))
          }
        }
    }.getOrElse(listAllMovie map { imovies => Ok(views.html.index(userDefault, imovies)) })
  }
  
  
  def searchMovieControl = Action.async { implicit request =>
    Logger.info("Start Search Control")
    var searchdb: String = ""
    var searchterm: String = ""
    var searchgenre: String = ""
    var userName : String = ""
    
    SearchMovieForm.form.bindFromRequest.fold(
      // if any error in submitted data
      // Todo : handling error in form
      errorForm => movieSeq.map{im => Ok("Error")},
      data => {
        searchdb = data.searchdb
        searchterm = data.searchterm
        searchgenre = data.searchgenre
        Logger.info("Search:" + data.searchdb + " " + data.searchterm + " " + data.searchgenre)
      }
    )
    request.session.get("connected").map {
      curUser => userName = curUser
    }.getOrElse(listAllMovie map { imovies => Ok(views.html.index(userDefault, imovies)) })
        
    // Get current logged in user
    val userInfoFuture = getUserbyUsername(userName)
    var userInfo = User()
    userInfoFuture.map{
      extractUSERinfo => 
      userInfo = extractUSERinfo.getOrElse(userDefault)
      //Logger.info("Extracting movie information")
    }
    
    searchdb match {
      case _ => {
        Logger.info("Search Movie Database")
        //Get all movie
//              var allusers : Seq[User] = Seq()
//              var filteredUser : Seq[User] = Seq()
//              val waitDB = listAllUser.map(allpeople => allusers = allpeople)
//              Await.ready(waitDB, Duration.Inf)
        
        listAllMoviebyName(mvSeq, searchterm).map{ tempfilteredMovie =>
          //Logger.info("Filtered Movie: " + filteredMovie)
          if (searchgenre == "none") {
            Ok(views.html.index(userInfo, tempfilteredMovie))
          } else{
            var filteredMovie : Seq[Movie] = Seq()
            for (u1 <- tempfilteredMovie){
              //Logger.info("u1: " + u1)
              if(u1.genre.toLowerCase.contains(searchgenre)){
                Logger.info("Found a movie matched search genre: " + u1)
                if (filteredMovie == Nil){
                  filteredMovie = u1 :: Nil
                } else{
                  filteredMovie = filteredMovie :+ u1
                  //Logger.info("Filtered user not nil")
                }  
              }
            }
            Ok(views.html.index(userInfo, filteredMovie))
          }
        }
        
      }
    }
      
  }

  
  def searchUserControl = Action.async { implicit request =>
    Logger.info("Start Search Control")
    var searchdb: String = ""
    var searchterm: String = ""
    var userName : String = ""
    SearchUserForm.form.bindFromRequest.fold(
      // if any error in submitted data
      // Todo : handling error in form
      errorForm => movieSeq.map{im => Redirect(routes.HomeController.index())},
      data => {
        searchdb = data.searchdb
        searchterm = data.searchterm
      }
    )
    request.session.get("connected").map {
      curUser => userName = curUser
    }.getOrElse(listAllMovie map { imovies => Ok(views.html.index(userDefault, imovies)) })
        
    // Get current logged in user
    val userInfoFuture = getUserbyUsername(userName)
    var userInfo = User()
    userInfoFuture.map{
      extractUSERinfo => 
      userInfo = extractUSERinfo.getOrElse(userDefault)
      //Logger.info("Extracting movie information")
    }
    
    searchdb match {
      case _ => {
        //Get all users
        Logger.info("Search User Database")
        var allusers : Seq[User] = Seq()
        var filteredUser : Seq[User] = Seq()
        val waitDB = listAllUser.map(allpeople => allusers = allpeople)
        Await.ready(waitDB, Duration.Inf)
        
        listAllUserbyName(allusers, searchterm).map{ filteredUser =>
          Logger.info("Filtered User: " + filteredUser)
          Ok(views.html.admin(userInfo, mvSeq, filteredUser))
        }
      }
    } 
  }
  
//  def upload = Action(parse.temporaryFile) { request =>
//    import java.io.File
//    request.body.moveTo(new File("/tmp/picture/uploaded"))
//    Ok("File uploaded")
//  }
  
  def upload = Action(parse.multipartFormData) { request =>
    var userName : String = ""
    
    request.session.get("connected").map {
      curUser => userName = curUser
    }.getOrElse(listAllMovie map { imovies => Ok(views.html.index(userDefault, imovies)) })
    
    request.body.file("picture").map { picture =>
      import java.io.File
      val filename = userName + ".png" //picture.filename
      val contentType = picture.contentType.get
      var appPath = environment.rootPath + """\public\picture\""" + filename
      picture.ref.moveTo(new File(appPath), true)
      //Ok("File uploaded")
      Redirect(routes.HomeController.userinfo)
    }.getOrElse {
      Redirect(routes.HomeController.userinfo).flashing(
        "error" -> "Missing file")
    }
  }
  
}


//class HomeController @Inject()(db: Database) extends Controller {
//
//  def index() = Action {
//    Ok(views.html.index())
//  }
//  
//  def getAllMovieTitle() = Action { 
//    var outString = ""
//    val conn = db.getConnection()
//    var m1 : Movie = Movie(0, "", 1900,  "", "")
//    var m2 : Seq[Movie] = Seq()
//    try {
//      val stmt = conn.createStatement
//      val rs = stmt.executeQuery("SELECT * from movieinfo")
//      
//      while (rs.next()) {
//        var mtemp : Movie = Movie(rs.getInt("id"), rs.getString("title"), rs.getInt("release"),
//              rs.getString("genre"), rs.getString("comment"))
//        outString += mtemp.toString()
//        m2 = m2 :+ mtemp
//      }
//    } finally {
//      conn.close()
//    }
//    Ok(views.html.index(m2))
//  }
//  
//  def getMovieWithId(id : Int) = Action {
//    var outString = "NA"
//    val conn = db.getConnection()
////    var m1 : Movie = Movie(0, "", 1900,  "", "")
////    var mlist : List[Movie] = List(m1)
//    try {
//      val stmt = conn.createStatement
//      val rs = stmt.executeQuery("SELECT * from movieinfo where id=" + id)
//      
//      while (rs.next()) {
//          var mtemp : Movie = Movie(rs.getInt("id"), rs.getString("title"), rs.getInt("release"),
//              rs.getString("genre"), rs.getString("comment"))
//          outString = mtemp.toString()
//      }
//    } finally {
//      conn.close()
//    }
//    Ok("id " + id + " " + outString)
//  }
//  
//  def getAllMovieId() = Action {
//    var outString = ""
//    val conn = db.getConnection()
//    
//    try {
//      val stmt = conn.createStatement
//      val rs = stmt.executeQuery("SELECT * from movieinfo")
//      
//      while (rs.next()) {
//          outString = outString + " " + rs.getInt("id").toString()
//      }
//    } finally {
//      conn.close()
//    }
//    Ok(outString)
//  }
//  
//  def getTitlebyId(id : Int) = Action {
//    var outString = ""
//    val conn = db.getConnection()
//    
//    try {
//      val stmt = conn.createStatement
//      val rs = stmt.executeQuery("SELECT * from movieinfo where id=" + id)
//      
//      while (rs.next()) {
//          outString = rs.getString("title")
//      }
//    } finally {
//      conn.close()
//    }
//    Ok(outString)
//  }
//  
//
//}
