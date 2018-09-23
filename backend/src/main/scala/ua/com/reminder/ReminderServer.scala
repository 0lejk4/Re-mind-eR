package ua.com.reminder

//#quick-start-server
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.directives.MethodDirectives._
import akka.http.scaladsl.server.directives.RouteDirectives._
import akka.stream.ActorMaterializer
import com.typesafe.config.Config

import scala.concurrent.Await
import scala.concurrent.duration.Duration

object ReminderServer extends App {

  implicit val system: ActorSystem = ActorSystem("reminderAkkaHttpServer")
  private val config: Config = system.settings.config
  implicit val materializer: ActorMaterializer = ActorMaterializer()

  lazy val routes: Route = get {
    complete("Hello world")
  }

  private val url: String = config.getString("http.url")
  private val port: Int = config.getInt("http.port")
  Http().bindAndHandle(routes,
    url,
    port)

  println(s"Server online at http://$url:$port/")

  Await.result(system.whenTerminated, Duration.Inf)

}