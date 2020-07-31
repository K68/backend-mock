package com.amzport.mock.test

import com.amzport.mock.MPB.FlowMeta
import com.amzport.mock.space.{Mock1000, Mock1012}
import com.amzport.mock.{MPB, MockLog, MockUser, MockWeb}
import pb.baccarat._
import pb.baccaratwrapper.WrapperBaccaratMessage
import pb.inoutroomwrapper.WrapperInOutRoomMessage

object TestZYC extends App {

  MockUser.setupMockUser("111", "111", "http://127.0.0.1:9002/api/auth/login", "ws://127.0.0.1:9002/wsz")
  MockUser.observe(999, (space, meta, value) => {
    // TODO

  })

  // MockSpaceXX 的参考用法
  Mock1012.observe((flowMeta, msg) => {
    msg match {
      case x: UserSitDownReceive =>
      case x: UserStandUp =>
    }
  })

  MockWeb.setupMockWeb("0.0.0.0")
  MockWeb.observe("333", (_, m) => println(m))

  MockWeb.observe("1012", (_, m) =>
    m("msg") match {
      //用户进入百家乐
      case "UserSitDownReceive" =>  //URL: http://127.0.0.1:9999/1012?msg=UserSitDownReceive&userId=?&roomId=?
        println(m)
        val userId = m("userId")
        val roomId = m("roomId")
        val UserSitDownReceive = WrapperBaccaratMessage().withUserSitDownReceive(pb.baccarat.UserSitDownReceive(userId.toLong, roomId.toInt))
        Mock1012.sendRoomDispatch(FlowMeta(1012, userId, 1, roomId, 2), UserSitDownReceive)
        //MockUser.send(MPB.toByte(FlowMeta(1012, userId, 1, roomId, 2), UserSitDownReceive))
      //用户进入百家乐
      case "UserStandUp" =>  //URL: http://127.0.0.1:9999/1012?msg=UserStandUp&userId=?&roomId=?&ChairID=?
        println(m)
        val ChairID = m("ChairID")
        val userId = m("userId")
        val roomId = m("roomId")
        val UserStandUp = WrapperBaccaratMessage().withUserStandUp(pb.baccarat.UserStandUp(ChairID.toInt))
        MockUser.send(MPB.toByte(FlowMeta(1012, userId, 1, roomId, 2), UserStandUp))
        MockLog.debug(s"${UserStandUp.toString}", 1012)
      case "b" =>
        MockLog.debug(s"${m("code2")}", 1012)
    }
  )

  MockWeb.observe("999", (_, m) =>
    m("msg") match {
      case "RoomInfoGet" => //URL: http://127.0.0.1:9999/999?msg=RoomInfoGet&userId=?&space=?
        val userId = m("userId")
        val space = m("space")
        val roomInfoGet = WrapperInOutRoomMessage().withRoomInfoGet(pb.inoutroom.RoomInfoGet(userId.toLong, space))
        MockUser.send(MPB.toByte(FlowMeta(999, userId, 1, "0"), roomInfoGet))
        MockLog.debug(s"${roomInfoGet.toString}")
    }
  )
}
