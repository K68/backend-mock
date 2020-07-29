package com.amzport.mock.test

import com.amzport.mock.MPB.FlowMeta
import com.amzport.mock.space.{Mock1001, Mock1011}
import com.amzport.mock.{MPB, MockLog, MockUser, MockWeb}
import pb.chat.Tips
import pb.inoutroomwrapper.WrapperInOutRoomMessage
import pb.laohuji._

object TestGY extends App {

  MockUser.setupMockUser("111", "111", "http://127.0.0.1:9000/api/auth/login", "ws://127.0.0.1:9000/wsz")
  MockUser.observe(999, (space, meta, value) => {
    MockLog.debug(s"$value")
  })

  //聊天
  Mock1001.observe((flowMeta, msg) => {
    msg match {
      case x: Tips =>
      MockLog.debug(s"${x.toString}", 1001)
    }
  })

  //九线
  Mock1011.observe((flowMeta, msg) => {
    MockLog.debug(msg.isInstanceOf[SlotMachine].toString)
    msg match {
      case x: SlotMachine =>
        MockLog.debug(s"${x.toString}", 1011)
    }
  })

  MockWeb.setupMockWeb("0.0.0.0")
  MockWeb.observe("1001", (_, m) =>
    m("msg") match {
      //用户进入聊天室
      case "UserIntoChat" =>  //URL: http://127.0.0.1:9999/1001?msg=UserIntoChat&userId=?&roomId=?
        val userId = m("userId")
        val roomId = m("roomId")
        val userIntoChat = pb.chat.UserIntoChat(userId.toLong, roomId.toInt)
        Mock1001.sendChatRoom(FlowMeta(1001, userId, 1, roomId, 2), userIntoChat)
        //MockLog.debug(s"${userIntoChat.toString}", 1001)
      case "b" =>
        MockLog.debug(s"${m("code2")}", 1001)
    }
  )

  MockWeb.observe("1011", (_, m) =>
    m("msg") match {
      //用户进入聊天室
      case "IntoClientRoom" =>  //URL: http://127.0.0.1:9999/1011?msg=IntoClientRoom&userId=?&seat=?
        val userId = m("userId")
        val seat = m("seat")
        val intoClientRoom = pb.laohuji.IntoClientRoom(userId.toLong, seat.toInt)
        Mock1011.sendLabaRoom(FlowMeta(1011, userId, 1, "2", 2), intoClientRoom)

      case "b" =>
        MockLog.debug(s"${m("code2")}", 1011)
    }
  )

  MockWeb.observe("999", (_, m) =>
    m("msg") match {
      //查询房间信息
      case "RoomInfoGet" => //URL: http://127.0.0.1:9999/999?msg=RoomInfoGet&userId=?&space=?
        val userId = m("userId")
        val space = m("space")
        val roomInfoGet = WrapperInOutRoomMessage().withRoomInfoGet(pb.inoutroom.RoomInfoGet(userId.toLong, space))
        MockUser.send(MPB.toByte(FlowMeta(999, userId, 1, "0"), roomInfoGet))
        MockLog.debug(s"${roomInfoGet.toString}")
    }
  )
}
