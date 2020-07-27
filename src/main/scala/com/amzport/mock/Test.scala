package com.amzport.mock

import akka.http.scaladsl.model.ws.{BinaryMessage, TextMessage}
import akka.util.ByteString

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

}
