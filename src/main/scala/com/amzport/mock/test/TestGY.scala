package com.amzport.mock.test

import com.amzport.mock.MPB.FlowMeta
import com.amzport.mock.space.{Mock1000, Mock1001, Mock1011}
import com.amzport.mock.{MPB, MockLog, MockUser, MockWeb}
import pb.chat.Tips
import pb.inoutroomwrapper.WrapperInOutRoomMessage
import pb.laohuji._
import pb.miracle.dispatch._

object TestGY extends App {

  //建立连接---登录
  MockUser.setupMockUser("116", "111", "http://192.168.5.81:9000/api/auth/login", "ws://192.168.5.81:9000/wsz")

  MockUser.observe(999, (space, meta, value) => {
    MockLog.debug(s"$value")
  })

  //监听到聊天返回的消息
  Mock1001.observe((flowMeta, msg) => {
    msg match {
      case x: Tips => //进入房间时返回的消息
      MockLog.debug(s"${x.toString}", 1001)
    }
  })

  //拦截到九线返回的消息
  Mock1011.observe((flowMeta, msg) => {
    msg match {
      case x: SlotMachine =>  //进入房间时返回的消息
        MockLog.debug(s"请求成功---${x.toString}", 1011)
      case x: RotateResult => //返回游戏结果
        MockLog.debug(s"请求成功---${x.toString}", 1011)
      case x: SlotError => //返回错误信息
        MockLog.debug(s"请求成功---${x.toString}", 1011)
      case x: JackpotMoney => //返回奖池信息
        MockLog.debug(s"请求成功---${x.toString}", 1011)
      case x: ReturnSlotVideo => //返回奖励排行榜信息
        MockLog.debug(s"请求成功---${x.toString}", 1011)
      case x: PlayerChatOut => //返回聊天内容
        MockLog.debug(s"请求成功---${x.toString}", 1011)
      case x: FriendsList => //返回好友列表
        MockLog.debug(s"请求成功---${x.toString}", 1011)
      case x: ReturnRankResult => //返回录像结果
        MockLog.debug(s"请求成功---${x.toString}", 1011)
      case x: ChatRecord => //返回聊天记录
        MockLog.debug(s"请求成功---${x.toString}", 1011)
      case x: OtherInOutRoom => //有人退出房间时向所有人更新房间信息
        MockLog.debug(s"请求成功---${x.toString}", 1011)
      case x: OtherState => //有人断线或者重连是更新房间信息
        MockLog.debug(s"请求成功---${x.toString}", 1011)

    }
  })

  Mock1000.observe((flowMeta, msg) => {
    msg match {
      case x: LiveRoom =>
        MockLog.debug(s"请求成功---${x.toString}", 1000)
      case x: InviteRoom =>
        MockLog.debug(s"请求成功---${x.toString}", 1000)
      case x: DispatchResult =>
        MockLog.debug(s"请求成功---${x.toString}", 1000)
    }
  })

  //建立web连接
  MockWeb.setupMockWeb("0.0.0.0")

  //监听到传入聊天室的消息---1001
  MockWeb.observe("1001", (_, m) =>
    m("msg") match {
      //用户进入聊天室
      case "UserIntoChat" =>  //URL: http://127.0.0.1:9999/1001?msg=UserIntoChat&userId=?&roomId=?
        val userId = m("userId")
        val roomId = m("roomId")
        val userIntoChat = pb.chat.UserIntoChat(userId.toLong, roomId.toInt)
        Mock1001.sendChatRoom(FlowMeta(1001, userId, 1, roomId, 2), userIntoChat)

      case "b" =>
        MockLog.debug(s"${m("code2")}", 1001)
    }
  )

  //监听到进入九线的消息---1011
  MockWeb.observe("1011", (_, m) =>
    m("msg") match {
      //用户进入九线
      case "IntoClientRoom" =>  //URL: http://127.0.0.1:9999/1011?msg=IntoClientRoom&userId=?&seat=?
        val userId = m("userId")
        val seat = m("seat")
        val intoClientRoom = pb.laohuji.IntoClientRoom(userId.toLong, seat.toInt)
        Mock1011.sendLabaRoom(FlowMeta(1011, MockUser.accountId, 1, "2", 2), intoClientRoom)
      //用户开始游戏
      case "Start" => //URL: http://127.0.0.1:9999/1011?msg=Start&userId=?&lineAmount=?&betAmount=?
        val userId= m("userId")
        val lineAmount = m("lineAmount")
        val betAmount = m("betAmount")
        val start = pb.laohuji.Start(userId.toLong, lineAmount.toInt, betAmount.toInt)
        Mock1011.sendStatLaba(FlowMeta(1011, MockUser.accountId, 1, "2", 2), start)
      //退出九线
      case "OutSlotRoom" => //URL: http://127.0.0.1:9999/1011?msg=OutSlotRoom&userId=?
        val userId = m("userId")
        val outSlotRoom = pb.laohuji.OutSlotRoom(userId.toLong)
        Mock1011.sendOutLaba(FlowMeta(1011, MockUser.accountId, 1, "2", 2), outSlotRoom)
      //请求老虎机录像
      case "AskForSlotVideo" => //URL: http://127.0.0.1:9999/1011?msg=AskForSlotVideo&whichRank=?
        val whichRank = m("whichRank")
        val askForSlotVideo = pb.laohuji.AskForSlotVideo(whichRank.toInt)
        Mock1011.sendSlotRank(FlowMeta(1011, MockUser.accountId, 1, "2", 2), askForSlotVideo)
      //邀请玩家
      case "InvitePlayer" => //URL: http://127.0.0.1:9999/1011?msg=InvitePlayer&userId=?&invitedId=?&seat=?
        val userId = m("userId")
        val invitedId = m("invitedId")
        val seat = m("seat")
        val invitePlayer = pb.laohuji.InvitePlayer(userId.toLong, invitedId.toLong, seat.toInt)
        Mock1011.sendInvitePlayer(FlowMeta(1011, MockUser.accountId, 1, "2", 2), invitePlayer)
      //用户发出聊天消息
      case "PlayerChatIn" => //URL: http://127.0.0.1:9999/1011?msg=PlayerChatIn&userId=?&playerName=?&head=?&chatContent=?
        val userId = m("userId")
        val playerName = m("playerName")
        val head = m("head")
        val chatContent = m("chatContent")
        val playerChatIn = pb.laohuji.PlayerChatIn(userId.toLong, playerName, head, chatContent)
        Mock1011.sendPlayerChat(FlowMeta(1011, MockUser.accountId, 1, "2", 2), playerChatIn)
      //申请好友列表
      case "ApplyFriendList" =>  //URL: http://127.0.0.1:9999/1011?msg=ApplyFriendList&userId=?
        val userId = m("userId")
        val applyFriendList = pb.laohuji.ApplyFriendList(userId.toLong)
        Mock1011.sendFriendList(FlowMeta(1011, MockUser.accountId, 1, "2", 2), applyFriendList)
      //请求排行榜中某榜单的信息
      case "AskForRankResult" => //URL: http://127.0.0.1:9999/1011?msg=AskForRankResult&recordId=?
        val recordId = m("recordId")
        val askForRankResult = pb.laohuji.AskForRankResult(recordId.toLong)
        Mock1011.sendRankResult(FlowMeta(1011, MockUser.accountId, 1, "2", 2), askForRankResult)
      //请求聊天记录
      case "RequestChatRecord"  => //URL: http://127.0.0.1:9999/1011?msg=RequestChatRecord&userId=?
        val userId = m("userId")
        val requestChatRecord = pb.laohuji.RequestChatRecord(userId.toLong)
        Mock1011.sendChatRecord(FlowMeta(1011, MockUser.accountId, 1, "2", 2), requestChatRecord)

    }
  )

  //监听到请求房间信息的消息
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
