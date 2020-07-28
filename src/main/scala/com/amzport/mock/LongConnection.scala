package com.amzport.mock

import akka.http.scaladsl.model.ws.{BinaryMessage, TextMessage}
import akka.util.ByteString
import com.amzport.mock.MPB.FlowMeta
import pb.miracle.auth.Wrapper
import pb.miracle.wrapper.MiracleWrapperMessage

import scala.concurrent.duration._

object LongConnection extends App {
  import MockSystem._
  import system.dispatcher

  var count = 1
  val outActor = MockSystem.setupMock("ws://192.168.5.30:9000/wsz",
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
      val auth = pb.miracle.auth.TokenLogin("0", "eyJyb2xlcyI6WyJBZG1pbiIsIkFub24iLCJBdXRoIl0sImFjY291bnRJZCI6MCwiZ3JvdXBJZHMiOltdLCJncm91cFRyZWVzIjpbXSwidHMiOiIyMDIwLTA3LTI4VDE5OjI5OjUyLjcwNjg4OTYifQ==.623ca36aa04623b98d86162aa2243939a86579710c2139de0920a4a02f149aba")
      val sendAuth = MPB.toByte(FlowMeta(0, "", 0, "", 0), Wrapper().withTokenLogin(auth))
      out ! BinaryMessage(ByteString(sendAuth))
    },
    _ => println("closed")
  )

  system.scheduler.scheduleOnce(7.seconds){
    val sendPing = MPB.toByte(FlowMeta(1, "", 0, "", 0), pb.miracle.pp.PingPong(1))
    outActor ! BinaryMessage(ByteString(sendPing))
  }
}
