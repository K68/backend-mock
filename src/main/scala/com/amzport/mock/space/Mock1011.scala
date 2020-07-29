package com.amzport.mock.space

import com.amzport.mock.MPB.FlowMeta
import com.amzport.mock.{MPB, MockLog, MockUser}
import com.google.protobuf.ByteString
import pb.laohuji.IntoClientRoom
import pb.laohujiwrapper.WrapperLaohujiMessage
import pb.laohujiwrapper.WrapperLaohujiMessage.Msg
import scalapb.GeneratedMessage


object Mock1011 {

  def sendLabaRoom(flowMeta: FlowMeta, m: IntoClientRoom): Unit = {
    MockLog.info(s"进入九线：${m.clientID} ${m.seat}", flowMeta.space)
    MockUser.send(MPB.toByte(flowMeta, WrapperLaohujiMessage().withIntoClientRoom(m).toByteString))
  }

  private def receiveMessage(space: Int, flowMeta: FlowMeta, value: ByteString): Unit = {
    MPB.parseWrap(WrapperLaohujiMessage, value)  match {
      case Some(wrapper) =>
        wrapper.msg match {
          case Msg.SlotMachine(into) =>
            MockLog.debug("flag!!!")
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
    MockUser.observe(1011, receiveMessage)
  }

}
