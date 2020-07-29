package com.amzport.mock

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpRequest, HttpResponse}
import akka.stream.scaladsl.Sink

import scala.concurrent.{ExecutionContextExecutor, Future}

object MockWeb {

  implicit private val system: ActorSystem = MockSystem.system
  implicit private val executionContext: ExecutionContextExecutor = system.dispatcher

  def setupMockWeb(interface: String = "127.0.0.1", port: Int = 9999): Future[Http.ServerBinding] = {
    val serverSource = Http().bind(interface, port)
    val requestHandler: HttpRequest => HttpResponse = { r: HttpRequest =>
      r.discardEntityBytes()
      val uri = r.getUri().asScala()
      val space = uri.path.tail.toString()
      observers.get(space) match {
        case Some(cb) =>
          val params: Map[String, String] = uri.queryString() match {
            case Some(q) =>
              q.split('&').map{ i =>
                val j = i.split('=')
                (j.head, j.last)
              }.toMap
            case None =>
              Map.empty
          }
          cb(space, params)
          HttpResponse(entity = HttpEntity(
            ContentTypes.`text/html(UTF-8)`,
            s"<html><body $bodyStyle>${fetchMockLogs()}</body></html>"
          ))
        case None =>
          HttpResponse(entity = "no space found!")
      }
    }
    serverSource.to(Sink.foreach { connection =>
      connection handleWithSyncHandler requestHandler
    }).run()
  }

  private final val bodyStyle = """style="font-family: monospace;background: black;""""
  private final def spanStyle(color: String) = s"""style="margin:6px;color:$color""""

  private def fetchMockLogs(): String = {
    MockLog.fetchLog().map { i =>
      val color = if (i.contains("INFO")) "green"
      else if (i.contains("DEBUG")) "darkgrey"
      else if (i.contains("WARN")) "yellow"
      else "red"
      s"<div><span ${spanStyle(color)}>$i</span></div>"
    }.mkString
  }

  private var observers: Map[String, (String, Map[String, String]) => Unit] = Map.empty

  def observe(space: String, cb: (String, Map[String, String]) => Unit): Unit = {
    observers = observers + (space -> cb)
  }

}
