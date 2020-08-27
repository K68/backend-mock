package com.amzport.mock

import com.amzport.mock.MockHTTP.postJson
import MockHTTP._
import akka.actor.ActorSystem
import akka.actor.typed.ActorRef
import akka.http.scaladsl.model.ws.{BinaryMessage, Message, TextMessage}
import akka.util.ByteString
import com.amzport.mock.MPB.FlowMeta
import pb.miracle.auth.Wrapper
import play.api.libs.json.Json

import scala.concurrent.ExecutionContextExecutor
import scala.concurrent.duration._

object MockUser {

  implicit private val system: ActorSystem = MockSystem.system
  implicit private val executionContext: ExecutionContextExecutor = system.dispatcher

  var authenticationToken: String = ""
  var authenticationRefreshToken: String = ""
  var accountId = ""
  var outActor: Option[ActorRef[Message]] = None
  

  def setupMockUser(userName: String, password: String, loginURL: String, socketURL: String): Unit = {
    postJson(loginURL, Json.obj("phoneNumber"->userName,"loginPassword"->password,"logoutOthers"->true))().map {
      case Left(e) =>
        MockLog.error(e.getMessage)
      case Right(v) =>
        rspEntityJson(v.entity).map { res =>
          val authMeta = (res \ "authMeta").as[AuthMeta]
          accountId = authMeta.accountId.toString
          authenticationToken = (res \ "Authentication-Token").as[String]
          authenticationRefreshToken = (res \ "Authentication-Refresh-Token").as[String]

          var closed = false
          val _outActor = MockSystem.setupMock(socketURL,
            (message, out) => {
              message match {
                case msg: BinaryMessage.Strict =>
                  val arrayByte = msg.getStrictData.toArray[Byte]
                  MPB.parseBs(arrayByte) match {
                    case Some((flowMeta, byteValue)) =>
                      val space = flowMeta.space
                      if (space == 0) {
                        MPB.parseWrap(Wrapper, byteValue) match {
                          case Some(wrapper) =>
                            wrapper.msg match {
                              case Wrapper.Msg.LoginSuccess(_) =>
                                MockLog.debug("长链接建立成功")
                              case Wrapper.Msg.LoginFailure(_) =>
                                MockLog.error("长链接建立失败")
                              case _ =>
                                MockLog.warn("useless auth message")
                            }
                          case None =>
                            MockLog.warn(s"parse auth message error: $flowMeta")
                        }
                      } else if (space == 1) {
                        // nothing
                      } else {
                        observers.get(space) match {
                          case Some(cb) =>
                            cb(space, flowMeta, byteValue)
                          case None =>
                            MockLog.warn(s"No space found $space")
                        }
                      }

                    case _ =>
                      MockLog.warn("Can't parse binary message")
                  }

                case msg: TextMessage.Strict =>
                  MockLog.warn(msg.text)
                case others =>
                  MockLog.warn(s"Other Messages: $others")
              }
            },
            (_, out) => {
              val auth = pb.miracle.auth.TokenLogin(accountId, authenticationToken)
              val sendAuth = MPB.toByte(FlowMeta(0, accountId), Wrapper().withTokenLogin(auth))
              out ! BinaryMessage(ByteString(sendAuth))
            },
            _ => {
              MockLog.info("closed")
              closed = true
            }
          )

          outActor = Some(_outActor)

          // 周期性Ping & Pong 保持链接活跃
          system.scheduler.scheduleAtFixedRate(3.seconds, 6.seconds) {() =>
            val sendPing = MPB.toByte(FlowMeta(1), pb.miracle.pp.PingPong(1))
            _outActor ! BinaryMessage(ByteString(sendPing))
            if (closed) {
              // TODO 如何关闭当前周期性任务
            }
          }

        }
    }
  }

  private var observers: Map[Int, (Int, FlowMeta, com.google.protobuf.ByteString) => Unit] = Map.empty

  def observe(space: Int, cb: (Int, FlowMeta, com.google.protobuf.ByteString) => Unit): Unit = {
    observers = observers + (space -> cb)
  }

  /**
   * 发送数据给后台
   * @param data
   */
  def send(data: Array[Byte]): Unit = {
    if (outActor.isDefined) {
      val binaryMessage = BinaryMessage(ByteString(data))
      outActor.get ! binaryMessage
    } else {
      MockLog.error("OutActor is None")
    }
  }

}
