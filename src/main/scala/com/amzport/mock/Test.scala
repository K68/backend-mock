package com.amzport.mock

import akka.http.scaladsl.model.ws.{BinaryMessage, TextMessage}

import scala.concurrent.duration._

object Test extends App {

  println("Hello World")

  import MockSystem._
  import system.dispatcher

  var count = 1
  val outActor = MockSystem.setupMock("ws://127.0.0.1:9000/ws",
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

//  MockHTTP.get("http://www.baidu.com")().map{
//    case Left(e) =>
//      println(e.getMessage)
//    case Right(v) =>
//      MockHTTP.rspEntityString(v.entity).map { res =>
//        println(res)
//      }
//  }
//
//  MockHTTP.postJson("https://reqres.in/api/users",
//    Json.obj("name" -> "Welcome", "job" -> "Leader")
//  )().map {
//    case Left(e) =>
//      println(e.getMessage)
//    case Right(v) =>
//      MockHTTP.rspEntityJson(v.entity).map { res =>
//        val id = (res \ "id").as[String]
//        val time = (res \ "createdAt").as[String]
//        println(id, time)
//        println(Json.stringify(res))
//      }
//  }

}
