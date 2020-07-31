package com.amzport.mock.space

import com.amzport.mock.MPB.FlowMeta
import com.amzport.mock.{MPB, MockLog, MockUser}
import com.google.protobuf.ByteString
import pb.inoutroom.{IntoGameGet, RoomInfoGet, UserInfoGet}
import pb.inoutroomwrapper.WrapperInOutRoomMessage
import pb.inoutroomwrapper.WrapperInOutRoomMessage.Msg
import scalapb.GeneratedMessage


object Mock999 {
  def sendRoomInfo(flowMeta: FlowMeta, m: RoomInfoGet): Unit = {
    MockLog.info(s"查询房间信息：${m.userId} ${m.space}", flowMeta.space)
    MockUser.send(MPB.toByte(flowMeta, WrapperInOutRoomMessage().withRoomInfoGet(m).toByteString))
  }
  def sendIntoGame(flowMeta: FlowMeta, m: IntoGameGet): Unit = {
    MockLog.info(s"查询游戏信息：${m.userId}", flowMeta.space)
    MockUser.send(MPB.toByte(flowMeta, WrapperInOutRoomMessage().withIntoGameGet(m).toByteString))
  }
  def sendUserInfo(flowMeta: FlowMeta, m: UserInfoGet): Unit = {
    MockLog.info(s"查询用户信息：${m.selfId} ${m.otherId}", flowMeta.space)
    MockUser.send(MPB.toByte(flowMeta, WrapperInOutRoomMessage().withUserInfoGet(m).toByteString))
  }

  private def receiveMessage(space: Int, flowMeta: FlowMeta, value: ByteString): Unit = {
    MPB.parseWrap(WrapperInOutRoomMessage, value)  match {
      case Some(wrapper) =>
        wrapper.msg match {
          case Msg.RoomInfoReturn(result) =>
            callback(flowMeta, result)

          case Msg.IntoGameReturn(ivt) =>
            callback(flowMeta, ivt)

          case Msg.UserInfoReturn(live) =>
            callback(flowMeta, live)

          case _ =>
            MockLog.warn("useless dispatch message", space)
        }
      case None =>
        MockLog.warn(s"parse dispatch message error: $flowMeta", space)
    }
  }
  private def callback(flowMeta: FlowMeta, msg: GeneratedMessage): Unit = {
    if (callbackOpt.isDefined) {
      callbackOpt.get(flowMeta, msg)
    }
  }
  private var callbackOpt: Option[(FlowMeta, GeneratedMessage) => Unit] = None

  def observe(cb: (FlowMeta, GeneratedMessage) => Unit): Unit = {
    callbackOpt = Some(cb)
    MockUser.observe(999, receiveMessage)
  }

}
