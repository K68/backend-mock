package com.amzport.mock.space

import com.amzport.mock.MPB.FlowMeta
import com.amzport.mock.{MPB, MockAdmin, MockLog, MockUser}
import com.google.protobuf.ByteString
import pb.admin.WrapperAdminMessage.Msg
import pb.admin._
import scalapb.GeneratedMessage

object Mock000 {

  def sendQueryLogonUser(flowMeta: FlowMeta, m:QueryLogonUser): Unit = {
    MockLog.info(s"查询注册过的用户数量:", flowMeta.space)
    MockAdmin.send(MPB.toByte(flowMeta,WrapperAdminMessage().withQueryLogonUser(m).toByteString))
  }

  def sendManagerQuit(flowMeta: FlowMeta, m:ManagerQuit): Unit = {
    MockLog.info(s"管理员退出:", flowMeta.space)
    MockAdmin.send(MPB.toByte(flowMeta,WrapperAdminMessage().withManagerQuit(m).toByteString))
  }

  def sendCheckAllRoomInfo(flowMeta: FlowMeta, m:CheckAllRoomInfo): Unit = {
    MockLog.info(s"查看所有房间信息:", flowMeta.space)
    MockAdmin.send(MPB.toByte(flowMeta,WrapperAdminMessage().withCheckAllRoomInfo(m).toByteString))
  }

  def sendCheckProfitAndLoss(flowMeta: FlowMeta, m:CheckProfitAndLoss): Unit = {
    MockLog.info(s"查看过多或过少的盈亏统计:", flowMeta.space)
    MockAdmin.send(MPB.toByte(flowMeta,WrapperAdminMessage().withCheckProfitAndLoss(m).toByteString))
  }

  def sendGetDispatchMetas(flowMeta: FlowMeta, m:GetDispatchMetas): Unit = {
    MockLog.info("请求每个房间下的最大人数和当人数等信息", flowMeta.space)
    MockAdmin.send(MPB.toByte(flowMeta,WrapperAdminMessage().withGetDispatchMetas(m).toByteString))
  }

  def sendQueryUsersInOneRoom(flowMeta: FlowMeta, m:QueryUsersInOneRoom): Unit = {
    MockLog.info("查询某个房间内的用户", flowMeta.space)
    MockAdmin.send(MPB.toByte(flowMeta,WrapperAdminMessage().withQueryUsersInOneRoom(m).toByteString))
  }

  def sendQueryWhereIsUser(flowMeta: FlowMeta, m:QueryWhereIsUser): Unit = {
    MockLog.info("查询用户在哪些游戏房间中", flowMeta.space)
    MockAdmin.send(MPB.toByte(flowMeta,WrapperAdminMessage().withQueryWhereIsUser(m).toByteString))
  }

  def sendMsgToRooms(flowMeta: FlowMeta, m:SendMsgToRooms): Unit = {
    MockLog.info("管理员向指定房间发送消息", flowMeta.space)
    MockAdmin.send(MPB.toByte(flowMeta,WrapperAdminMessage().withSendMsgToRooms(m).toByteString))
  }

  def sendMsgToAllRooms(flowMeta: FlowMeta, m:SendMsgToAllRooms): Unit = {
    MockLog.info("管理员向所有房间发送消息", flowMeta.space)
    MockAdmin.send(MPB.toByte(flowMeta,WrapperAdminMessage().withSendMsgToAllRooms(m).toByteString))
  }

  def sendQueryAdminIpInfo(flowMeta: FlowMeta, m:QueryAdminIpInfo): Unit = {
    MockLog.info("查询ip信息", flowMeta.space)
    MockAdmin.send(MPB.toByte(flowMeta,WrapperAdminMessage().withQueryAdminIpInfo(m).toByteString))
  }

  def sendQuerySystemSetting(flowMeta: FlowMeta, m:QuerySystemSetting): Unit = {
    MockLog.info("查询系统配置", flowMeta.space)
    MockAdmin.send(MPB.toByte(flowMeta,WrapperAdminMessage().withQuerySystemSetting(m).toByteString))
  }

  def sendUpdateSystemSetting(flowMeta: FlowMeta, m:UpdateSystemSetting): Unit = {
    MockLog.info("修改系统配置", flowMeta.space)
    MockAdmin.send(MPB.toByte(flowMeta,WrapperAdminMessage().withUpdateSystemSetting(m).toByteString))
  }

  private def receiveMessage(space:Int, flowMeta: FlowMeta, value:ByteString ):Unit = {
    MPB.parseWrap(WrapperAdminMessage,value) match {
      case Some(wrapper) =>
        wrapper.msg match {
          case Msg.LogonUserAmount(value) =>
            callback(flowMeta, value)
          case Msg.ManagerTipsInfo(info) =>
            callback(flowMeta, info)
          case Msg.AllRoomInfoListRsp(infoListRsp) =>
            callback(flowMeta, infoListRsp)
          case Msg.HistoryProfitAndLossListRsp(info) =>
            callback(flowMeta, info)
          case Msg.LiveProfitAndLossListRsp(info) =>
            callback(flowMeta, info)
          case Msg.DispatchMetas(info) =>
            callback(flowMeta, info)
          case Msg.UsersInOneRoom(info) =>
            callback(flowMeta, info)
          case Msg.RoomsUserIn(info) =>
            callback(flowMeta, info)
          case Msg.AdminIpInfoRsp(info) =>
            callback(flowMeta, info)
//          case Msg.SystemSettings(info) =>
//            callback(flowMeta, info)
          case Msg.SystemSettingsRsp(info) =>
            callback(flowMeta, info)
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
