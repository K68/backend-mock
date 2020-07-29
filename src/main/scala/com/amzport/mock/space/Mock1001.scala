package com.amzport.mock.space

import com.amzport.mock.{MPB, MockLog, MockUser}
import com.amzport.mock.MPB.FlowMeta
import com.google.protobuf.ByteString
import pb.chat._
import pb.chatwrapper.WrapperChatMessage
import pb.chatwrapper.WrapperChatMessage.Msg
import scalapb.GeneratedMessage


object Mock1001 {

  def sendChatRoom(flowMeta: FlowMeta, m: UserIntoChat): Unit = {
    MockLog.info(s"进入聊天室：${m.userID} ${m.roomId}", flowMeta.space)
    MockUser.send(MPB.toByte(flowMeta, WrapperChatMessage().withUserIntoChat(m).toByteString))
  }

  private def receiveMessage(space: Int, flowMeta: FlowMeta, value: ByteString): Unit = {
    MPB.parseWrap(WrapperChatMessage, value)  match {
      case Some(wrapper) =>
        wrapper.msg match {
          case Msg.Tips(into) =>
            callback(flowMeta, into)

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
    MockUser.observe(1001, receiveMessage)
  }

}
