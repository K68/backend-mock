package com.amzport.mock.test

import java.util

import com.amzport.mock.MPB.FlowMeta
import com.amzport.mock.MockSystem.system
import com.amzport.mock.space.{Mock1000, Mock1012}
import com.amzport.mock.test.Test.outActor
import com.amzport.mock.{MPB, MockLog, MockUser, MockWeb}
import pb.baccarat._
import pb.baccaratwrapper.WrapperBaccaratMessage
import pb.inoutroomwrapper.WrapperInOutRoomMessage

import scala.concurrent.duration._

object TestZYC extends App {

  var IsInto = false
  MockUser.setupMockUser("116", "111", "http://127.0.0.1:9002/api/auth/login", "ws://127.0.0.1:9002/wsz")
  MockUser.observe(999, (space, meta, value) => {
    // TODO

  })

  // MockSpaceXX 的参考用法
  Mock1012.observe((flowMeta, msg) => {
    msg match {
      case x: UserSitDownSend =>
        MockLog.debug(s"${x.toString}", 1012)
      case x: ApplyBankerSend =>
        MockLog.debug(s"${x.toString}", 1012)
      case x: ChangeBanker =>
        MockLog.debug(s"${x.toString}", 1012)
      case x: PlaceBetSend =>
        MockLog.debug(s"${x.toString}", 1012)
      case x: UserStateChange =>
        MockLog.debug(s"${x.toString}", 1012)
      case x:UsersItem =>
        MockLog.debug(s"${x.toString}", 1012)
      case x:UserItem=>
        MockLog.debug(s"${x.toString}", 1012)
      case x: TableCardArrayItem =>
        MockLog.debug(s"${x.toString}", 1012)
      case x: CancelBanker =>
        MockLog.debug(s"${x.toString}", 1012)
      case x: PlaceBetFail =>
        MockLog.debug(s"${x.toString}", 1012)
      case x: PlaceAllBetSend =>
        MockLog.debug(s"${x.toString}", 1012)
      case x: StatusChang =>
        MockLog.debug(s"${x.toString}", 1012)
        MockLog.debug(s"${IsInto.toString}", 1012)
        MockLog.debug(s"${x.status.toString}", 1012)
        if(IsInto && x.status == 2)
        {
          //system.scheduler.scheduleOnce(7.seconds) {
          MockLog.debug("PlaceBet", 1012)
          val ran1 = (new util.Random).nextInt(32767)
          val BetArea =ran1 % (7)
          val PlaceBetReceive = WrapperBaccaratMessage().withPlaceBetReceive(pb.baccarat.PlaceBetReceive(0L,BetArea,500L,1))
          Mock1012.sendRoomDispatch(FlowMeta(1012, "6", 1, "32", 2), PlaceBetReceive)
          //}

        }
      case x:GameStart =>
        MockLog.debug(s"${x.toString}", 1012)
      case x:GameEnd=>
        MockLog.debug(s"${x.toString}", 1012)
      case x: TableCardArray =>
        MockLog.debug(s"${x.toString}", 1012)
      case x: PlayScore =>
        MockLog.debug(s"${x.toString}", 1012)
      case x: RecordRoadArrayInt =>
        MockLog.debug(s"${x.toString}", 1012)
      case x: RecordRoadInt =>
        MockLog.debug(s"${x.toString}", 1012)
      case x: GameRecordSend =>
        MockLog.debug(s"${x.toString}", 1012)
      case x:RecordRoadArrayString =>
        MockLog.debug(s"${x.toString}", 1012)
      case x:RecordRoadString=>
        MockLog.debug(s"${x.toString}", 1012)
      case x:UserStateChange =>
        MockLog.debug(s"${x.toString}", 1012)
      case x:TipsMessage=>
        MockLog.debug(s"${x.toString}", 1012)
    }
  })

  MockWeb.setupMockWeb("0.0.0.0")
  MockWeb.observe("333", (_, m) => println(m))

  MockWeb.observe("1012", (_, m) =>
    m("msg") match {
      //用户进入百家乐
      case "UserSitDownReceive" =>  //URL: http://127.0.0.1:9999/1012?msg=UserSitDownReceive&userId=?&roomId=?
        val userId = m("userId")
        val roomId = m("roomId")
        MockLog.debug(s"${userId.toString}", 1012)
        if(userId.toInt == 6) {
          IsInto = true
          MockLog.debug(s"${IsInto.toString}", 1012)
        }
        val UserSitDownReceive = WrapperBaccaratMessage().withUserSitDownReceive(pb.baccarat.UserSitDownReceive(userId.toLong, roomId.toInt))
        Mock1012.sendRoomDispatch(FlowMeta(1012, userId, 1, roomId, 2), UserSitDownReceive)
      //用户申请庄家
      case "ApplyBankerReceive" =>  //URL: http://127.0.0.1:9999/1012?msg=ApplyBankerReceive&userId=?&roomId=?
        val userId = m("userId")
        val roomId = m("roomId")
        val ApplyBankerReceive = WrapperBaccaratMessage().withApplyBankerReceive(pb.baccarat.ApplyBankerReceive(userId.toLong))
        Mock1012.sendRoomDispatch(FlowMeta(1012, userId, 1, roomId, 2), ApplyBankerReceive)
      //用户下庄
      case "CancelBanker" =>  //URL: http://127.0.0.1:9999/1012?msg=ApplyBankerReceive&userId=?&roomId=?&ChairID=?&ListApplyCount=?
        val userId = m("userId")
        val roomId = m("roomId")
        val CancelBanker = WrapperBaccaratMessage().withCancelBanker(pb.baccarat.CancelBanker(userId.toLong))
        Mock1012.sendRoomDispatch(FlowMeta(1012, userId, 1, roomId, 2), CancelBanker)
      //用户下注
      case "PlaceBetReceive" =>  //URL: http://127.0.0.1:9999/1012?msg=PlaceBetReceive&userId=?&roomId=?&ChairID=?&BetArea=?&BetScore=?&AndroidUser=?
        val userId = m("userId")
        val roomId = m("roomId")
        val ChairID = m("ChairID")
        val BetArea = m("BetArea")
        val BetScore = m("BetScore")
        val AndroidUser = m("AndroidUser")
        val PlaceBetReceive = WrapperBaccaratMessage().withPlaceBetReceive(pb.baccarat.PlaceBetReceive(ChairID.toLong,BetArea.toInt,BetScore.toLong,AndroidUser.toInt))
        Mock1012.sendRoomDispatch(FlowMeta(1012, userId, 1, roomId, 2), PlaceBetReceive)
      //用户退出百家乐
      case "UserStandUp" =>  //URL: http://127.0.0.1:9999/1012?msg=UserStandUp&userId=?&roomId=?&ChairID=?
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
