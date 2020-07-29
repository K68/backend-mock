package com.amzport.mock.test

import com.amzport.mock.MPB.FlowMeta
import com.amzport.mock.{MPB, MockLog, MockUser, MockWeb}
import pb.chatwrapper.WrapperChatMessage
import pb.inoutroomwrapper.WrapperInOutRoomMessage

object TestGY extends App {

  MockUser.setupMockUser("111", "111", "http://127.0.0.1:9000/api/auth/login", "ws://127.0.0.1:9000/wsz")

  MockWeb.setupMockWeb("0.0.0.0")
  MockWeb.observe("1001", (_, m) =>
    m("msg") match {
      //用户进入聊天室
      case "UserIntoChat" =>  //URL: http://127.0.0.1:9999/1001?msg=UserIntoChat&userId=?&roomId=?
        val userId = m("userId")
        val roomId = m("roomId")
        val userIntoChat = WrapperChatMessage().withUserIntoChat(pb.chat.UserIntoChat(userId.toLong, roomId.toInt))
        MockUser.send(MPB.toByte(FlowMeta(1001, userId, 1, roomId, 2), userIntoChat))
        MockLog.debug(s"${userIntoChat.toString}", 1001)
      case "b" =>
        MockLog.debug(s"${m("code2")}", 1001)
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
