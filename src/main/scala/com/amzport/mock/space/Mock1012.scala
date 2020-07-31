package com.amzport.mock.space

import com.amzport.mock.{MPB, MockLog, MockUser}
import com.amzport.mock.MPB.FlowMeta
import com.google.protobuf.ByteString
import pb.baccaratwrapper.WrapperBaccaratMessage
import pb.miracle.dispatch.{DispatchWrapper, RoomDispatch}
import pb.miracle.dispatch.DispatchWrapper.Msg
import scalapb.GeneratedMessage

object Mock1012 {

  def sendRoomDispatch(flowMeta: FlowMeta, m: WrapperBaccaratMessage): Unit = {
    //MockLog.info(s"寻找房间：${m.spaceNew} ${m.meta}", flowMeta.space)
    MockUser.send(MPB.toByte(flowMeta, m.toByteString))
    MockLog.debug(s"${m.toString}", 1012)
  }

  private def receiveMessage(space: Int, flowMeta: FlowMeta, value: ByteString): Unit = {
    MPB.parseWrap(DispatchWrapper, value)  match {
      case Some(wrapper) =>
        wrapper.msg match {
          case Msg.Result(result) =>
            if (result.ok) {
              callback(flowMeta, result)
            } else {
              MockLog.error("", space)
            }

          case Msg.IvtRoom(ivt) =>
            callback(flowMeta, ivt)

          case Msg.LiveRoom(live) =>
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
    MockUser.observe(1012, receiveMessage)
  }

}
