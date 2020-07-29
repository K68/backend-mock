package com.amzport.mock

import com.amzport.mock.MockHTTP.postJson
import MockHTTP._
import akka.http.scaladsl.model.ws.{BinaryMessage, TextMessage}
import akka.util.ByteString
import com.amzport.mock.LongConnection.{count, outActor}
import com.amzport.mock.MPB.FlowMeta
import com.amzport.mock.MockSystem.system
import pb.miracle.auth.Wrapper
import play.api.libs.json.Json

object MockUser  {
  var authenticationToken: String = ""
  var authenticationRefreshToken: String = ""
  var accountId = ""

  def getToken(url: String, userName: String, password: String) = {
    postJson(url,
      Json.obj("phoneNumber"->userName,"loginPassword"->password,"logoutOthers"->true)
    )().map {
      case Left(e) =>
        println(111)
        println(e.getMessage)
      case Right(v) =>
        rspEntityJson(v.entity).map { res =>
          val authMeta = (res \ "authMeta").as[AuthMeta]
          accountId = authMeta.accountId.toString
          authenticationToken = (res \ "Authentication-Token").as[String]
          authenticationRefreshToken = (res \ "Authentication-Refresh-Token").as[String]
          println(accountId)
          println(authenticationToken)
          println(authenticationRefreshToken)
        }
    }
  }

    def longConnection(wsUrl: String, accountId: String, token: String, space: Int, fromId: String, fromType: Int, toId: String, toType:Int): Unit = {
      var count = 1
      val outActor = MockSystem.setupMock(wsUrl,
        (message, out) => {
          message match {
            case msg: BinaryMessage.Strict =>
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
          val auth = pb.miracle.auth.TokenLogin(accountId, token)
          val sendAuth = MPB.toByte(FlowMeta(space, fromId, fromType, toId, toType), Wrapper().withTokenLogin(auth))
          out ! BinaryMessage(ByteString(sendAuth))
        },
        _ => println("closed")
      )

      system.scheduler.scheduleOnce(7.seconds){
        val sendPing = MPB.toByte(FlowMeta(space, fromId, fromType, toId, toType), pb.miracle.pp.PingPong(1))
        outActor ! BinaryMessage(ByteString(sendPing))
      }
    }

}
