package com.amzport.mock.test

import akka.http.scaladsl.model.ws.{BinaryMessage, TextMessage}
import com.amzport.mock.{MockHTTP, MockLog, MockSystem, MockWeb}
import play.api.libs.json.Json

import scala.concurrent.duration._

object Test extends App {

  println("Hello World")

  import MockSystem._
  import system.dispatcher

  var count = 1
  val outActor = MockSystem.setupMock("ws://echo.websocket.org",
    (message, out) => {
      message match {
        case msg: BinaryMessage.Strict =>
          // val arrayByte = message.getStrictData.toArray[Byte]
          // val binaryMessage = BinaryMessage(ByteString(arrayByte))
          println(msg)
        case msg: TextMessage.Strict =>
          println("Received " + count + ": " + msg.text)
          if (count < 6) {
            count = count + 1
            out ! TextMessage(s"Hello World $count")
          }
        case others =>
          println("Other Messages: ", others)
      }
    },
    (_, out) => {
      out ! TextMessage(s"Hello World $count")
    },
    _ => println("closed")
  )

  system.scheduler.scheduleOnce(7.seconds) {
    outActor ! TextMessage("TextMessage after 7.seconds")
  }

  /*
  MockHTTP.get("http://www.baidu.com")().map{
    case Left(e) =>
      println(e.getMessage)
    case Right(v) =>
      MockHTTP.rspEntityString(v.entity).map { res =>
        println(res)
      }
  }
  */

  MockHTTP.postJson("https://reqres.in/api/users",
    Json.obj("name" -> "Welcome", "job" -> "Leader")
  )().map {
    case Left(e) =>
      println(e.getMessage)
    case Right(v) =>
      MockHTTP.rspEntityJson(v.entity).map { res =>
        val id = (res \ "id").as[String]
        val time = (res \ "createdAt").as[String]
        println(id, time)
        println(Json.stringify(res))
      }
  }

  MockLog.error("66666666666")
  MockLog.debug("77777777777")
  MockLog.info("88888888888")
  MockLog.warn("99999999999")

  MockWeb.setupMockWeb("0.0.0.0")
  MockWeb.observe("333", (_, m) => println(m))

}
