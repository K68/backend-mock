package com.amzport.mock.space

import com.amzport.mock.MPB.FlowMeta
import com.amzport.mock.{MPB, MockAdmin, MockLog, MockUser}
import com.google.protobuf.ByteString
import pb.admin.WrapperAdminMessage.Msg
import pb.admin._
import scalapb.GeneratedMessage

object Mock000 {

  def sendQueryLogonUser(flowMeta: FlowMeta, m:QueryLogonUser):Unit = {
    MockLog.info(s"查询注册过的用户数量:", flowMeta.space)
    MockAdmin.send(MPB.toByte(flowMeta,WrapperAdminMessage().withQueryLogonUser(m).toByteString))
  }

  def sendManagerQuit(flowMeta: FlowMeta, m:ManagerQuit):Unit = {
    MockLog.info(s"管理员退出:", flowMeta.space)
    MockAdmin.send(MPB.toByte(flowMeta,WrapperAdminMessage().withManagerQuit(m).toByteString))
  }

  def sendCheckAllRoomInfo(flowMeta: FlowMeta, m:CheckAllRoomInfo):Unit = {
    MockLog.info(s"查看所有房间信息:", flowMeta.space)
    MockAdmin.send(MPB.toByte(flowMeta,WrapperAdminMessage().withCheckAllRoomInfo(m).toByteString))
  }

  def sendCheckProfitAndLoss(flowMeta: FlowMeta, m:CheckProfitAndLoss):Unit = {
    MockLog.info(s"查看过多或过少的盈亏统计:", flowMeta.space)
    MockAdmin.send(MPB.toByte(flowMeta,WrapperAdminMessage().withCheckProfitAndLoss(m).toByteString))
  }

  private def receiveMessage(space:Int, flowMeta: FlowMeta, value:ByteString ):Unit = {
    MPB.parseWrap(WrapperAdminMessage,value) match {
      case Some(wrapper) =>
        wrapper.msg match {
          case Msg.LogonUserAmount(value) =>
            println(111)
            callback(flowMeta, value)
          case Msg.ManagerTipsInfo(info) =>
            callback(flowMeta, info)
          case Msg.AllRoomInfoListRsp(infoListRsp) =>
            callback(flowMeta, infoListRsp)
          case Msg.HistoryProfitAndLossListRsp(info) =>
            callback(flowMeta, info)
          case Msg.LiveProfitAndLossListRsp(info) =>
            callback(flowMeta,info)
        }
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
    MockAdmin.observe(1002, receiveMessage)
  }


}
