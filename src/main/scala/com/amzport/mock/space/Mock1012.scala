package com.amzport.mock.space

import com.amzport.mock.{MPB, MockLog, MockUser}
import com.amzport.mock.MPB.FlowMeta
import com.google.protobuf.ByteString
import pb.baccaratwrapper.WrapperBaccaratMessage
import pb.baccaratwrapper.WrapperBaccaratMessage.Msg
import scalapb.GeneratedMessage

object Mock1012 {

  def sendRoomDispatch(flowMeta: FlowMeta, m: WrapperBaccaratMessage): Unit = {
    MockLog.info(s"进入百家乐：${flowMeta.fromId} ${flowMeta.toId}", flowMeta.space)
    MockUser.send(MPB.toByte(flowMeta, m.toByteString))
    MockLog.debug(s"${m.toString}", 1012)
  }

  private def receiveMessage(space: Int, flowMeta: FlowMeta, value: ByteString): Unit = {
    MPB.parseWrap(WrapperBaccaratMessage, value)  match {
      case Some(wrapper) =>
        wrapper.msg match {
          case Msg.UserSitDownSend(result) =>
              callback(flowMeta, result)
          case Msg.ApplyBankerSend(result) =>
            callback(flowMeta, result)
          case Msg.ChangeBanker(result) =>
            callback(flowMeta, result)
          case Msg.PlaceBetSend(result) =>
            callback(flowMeta, result)
          case Msg.UserStateChange(result) =>
            callback(flowMeta, result)
          case Msg.UsersItem(result)=>
            callback(flowMeta, result)
          case Msg.TableCardArrayItem(result)=>
            callback(flowMeta, result)
          case Msg.CancelBanker(result)=>
            callback(flowMeta, result)
          case Msg.PlaceBetFail(result)=>
            callback(flowMeta, result)
          case Msg.PlaceAllBetSend(result)=>
            callback(flowMeta, result)
          case Msg.StatusChang(result)=>
            callback(flowMeta, result)
          case Msg.GameStart(result)=>
            callback(flowMeta, result)
          case Msg.GameEnd(result)=>
            callback(flowMeta, result)
          case Msg.TableCardArray(result)=>
            callback(flowMeta, result)
          case Msg.PlayScore(result)=>
            callback(flowMeta, result)
          case Msg.RecordRoadArrayInt(result)=>
            callback(flowMeta, result)
          case Msg.UserItem(result)=>
            callback(flowMeta, result)
          case Msg.RecordRoadArrayString(result)=>
            callback(flowMeta, result)
          case Msg.RecordRoadString(result)=>
            callback(flowMeta, result)
          case Msg.RecordRoadInt(result)=>
            callback(flowMeta, result)
          case Msg.GameRecordSend(result)=>
            callback(flowMeta, result)
          case Msg.UserStateChange(result)=>
            callback(flowMeta, result)
          case Msg.TipsMessage(result)=>
            callback(flowMeta, result)
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
