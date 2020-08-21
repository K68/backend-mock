package com.amzport.mock.test

import com.amzport.mock.MPB.FlowMeta
import com.amzport.mock.space.Mock1000
import com.amzport.mock.{MPB, MockLog, MockUser, MockWeb}
import pb.buyu.{BuYuInvitePlayer, BuYuLoginInfo}
import pb.buyuwrapper.WrapperBuyuMessage
import pb.miracle.dispatch.{DispatchWrapper, InviteRoom, RoomDispatch}

object TestYJ extends App {

  MockUser.setupMockUser("111", "111", "http://192.168.5.29:9000/api/auth/login", "ws://192.168.5.29:9000/wsz")
  MockWeb.setupMockWeb("0.0.0.0")
  //邀请好友
  MockWeb.observe("1010", (_, m) =>
    //URL: http://127.0.0.1:9999/1010?BuYu=BuYuInv&theMeta=?theSpace=?&fromId=?&fromType=?&toId=?&toType=?&InvitedId=?&Seat=?
    m("BuYu") match {
      case "BuYuInv" =>
        val flowMeta = FlowMeta(m("theSpace").toInt, m("fromId"), m("fromType").toInt, m("toId"), m("toType").toInt)
        //val inviteRoom = pb.miracle.dispatch.InviteRoom(m("theSpace").toInt, m("theMeta"), m("toId"), m("remark"), m("timestamp").toLong, m("fromUuid"), m("fromName"), m("fromFace"))
        //val buYuIvtRoom = DispatchWrapper().withIvtRoom(inviteRoom)
        val buYuInvitePlayer = WrapperBuyuMessage().withBuYuInvitePlayer(BuYuInvitePlayer(m("fromId").toLong, m("InvitedId").toLong, m("Seat").toInt))
        MockUser.send(MPB.toByte(flowMeta, buYuInvitePlayer))
        MockLog.debug(s"${buYuInvitePlayer}", m("theSpace").toInt)
      case "BuYuDebug" =>
        MockLog.debug(s"${m("code2")}", 1010)

      case "BuYuLogin" =>
      //URL: http://127.0.0.1:9999/1010?BuYu=BuYuLogin&theMeta=?&theSpace=?&fromId=?&fromType=?&toId=?&toType=?&ClientID=?&Seat=?
        val flowMeta = FlowMeta(m("theSpace").toInt, m("fromId"), m("fromType").toInt, m("toId"), m("toType").toInt)
        val buYuLoginInfo = pb.buyu.BuYuLoginInfo(m("ClientID").toLong, m("Seat").toInt)
        val buYuLoginInfoMess = WrapperBuyuMessage().withBuYuLoginInfo(buYuLoginInfo)
        MockUser.send(MPB.toByte(flowMeta, buYuLoginInfoMess))
        MockLog.debug(s"${buYuLoginInfoMess}", m("theSpace").toInt)
      case "BuYuUserLogin" =>
        //URL: http://127.0.0.1:9999/1010?BuYu=BuYuUserLogin&userName=?
      MockUser.setupMockUser("111", "111", "http://127.0.0.1:9007/api/auth/login", "ws://127.0.0.1:9007/wsz")


    }
  )


}
