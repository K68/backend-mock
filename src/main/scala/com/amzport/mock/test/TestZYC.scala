package com.amzport.mock.test

import java.util

import com.amzport.mock.MPB.FlowMeta
import com.amzport.mock.MockSystem.system
import com.amzport.mock.space._
import com.amzport.mock.test.Test.outActor
import com.amzport.mock.{MPB, MockLog, MockUser, MockWeb}
import pb.baccarat._
import pb.baccaratwrapper.WrapperBaccaratMessage
import pb.bullFight.WrapperBullFightMessage
import pb.dice.WrapperDiceMessage
import pb.dragonTigerwrapper.WrapperDragonTigerMessage
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
          val PlaceBetReceive = WrapperBaccaratMessage().withPlaceBetReceive(pb.baccarat.PlaceBetReceive(1,BetArea,500L,0))
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

  Mock1014.observe((flowMeta, msg) => {
    msg match {
      case x: UserSitDownSend =>
        MockLog.debug(s"${x.toString}", 1014)
      case x: ApplyBankerSend =>
        MockLog.debug(s"${x.toString}", 1014)
      case x: ChangeBanker =>
        MockLog.debug(s"${x.toString}", 1014)
      case x: PlaceBetSend =>
        MockLog.debug(s"${x.toString}", 1014)
      case x: UserStateChange =>
        MockLog.debug(s"${x.toString}", 1014)
      case x:UsersItem =>
        MockLog.debug(s"${x.toString}", 1014)
      case x:UserItem=>
        MockLog.debug(s"${x.toString}", 1014)
      case x: TableCardArrayItem =>
        MockLog.debug(s"${x.toString}", 1014)
      case x: CancelBanker =>
        MockLog.debug(s"${x.toString}", 1014)
      case x: PlaceBetFail =>
        MockLog.debug(s"${x.toString}", 1014)
      case x: PlaceAllBetSend =>
        MockLog.debug(s"${x.toString}", 1014)
      case x: StatusChang =>
        MockLog.debug(s"${x.toString}", 1014)
        MockLog.debug(s"${IsInto.toString}", 1014)
        MockLog.debug(s"${x.status.toString}", 1014)
      /*if(IsInto && x.status == 2)
      {
        //system.scheduler.scheduleOnce(7.seconds) {
        MockLog.debug("PlaceBet", 1014)
        val ran1 = (new util.Random).nextInt(32767)
        val BetArea =ran1 % (7)
        val PlaceBetReceive = WrapperBaccaratMessage().withPlaceBetReceive(pb.baccarat.PlaceBetReceive(0L,BetArea,500L,1))
        Mock1014.sendRoomDispatch(FlowMeta(1014, "6", 1, "32", 2), PlaceBetReceive)
        //}
      }*/
      case x:GameStart =>
        MockLog.debug(s"${x.toString}", 1014)
      case x:GameEnd=>
        MockLog.debug(s"${x.toString}", 1014)
      case x: TableCardArray =>
        MockLog.debug(s"${x.toString}", 1014)
      case x: PlayScore =>
        MockLog.debug(s"${x.toString}", 1014)
      case x: RecordRoadArrayInt =>
        MockLog.debug(s"${x.toString}", 1014)
      case x: RecordRoadInt =>
        MockLog.debug(s"${x.toString}", 1014)
      case x: GameRecordSend =>
        MockLog.debug(s"${x.toString}", 1014)
      case x:RecordRoadArrayString =>
        MockLog.debug(s"${x.toString}", 1014)
      case x:RecordRoadString=>
        MockLog.debug(s"${x.toString}", 1014)
      case x:UserStateChange =>
        MockLog.debug(s"${x.toString}", 1014)
      case x:TipsMessage=>
        MockLog.debug(s"${x.toString}", 1014)
    }
  })

  Mock1016.observe((flowMeta, msg) => {
    msg match {
      case x: UserSitDownSend =>
        MockLog.debug(s"${x.toString}", 1016)
      case x: ApplyBankerSend =>
        MockLog.debug(s"${x.toString}", 1016)
      case x: ChangeBanker =>
        MockLog.debug(s"${x.toString}", 1016)
      case x: PlaceBetSend =>
        MockLog.debug(s"${x.toString}", 1016)
      case x: UserStateChange =>
        MockLog.debug(s"${x.toString}", 1016)
      case x:UsersItem =>
        MockLog.debug(s"${x.toString}", 1016)
      case x:UserItem=>
        MockLog.debug(s"${x.toString}", 1016)
      case x: TableCardArrayItem =>
        MockLog.debug(s"${x.toString}", 1016)
      case x: CancelBanker =>
        MockLog.debug(s"${x.toString}", 1016)
      case x: PlaceBetFail =>
        MockLog.debug(s"${x.toString}", 1016)
      case x: PlaceAllBetSend =>
        MockLog.debug(s"${x.toString}", 1016)
      case x: StatusChang =>
        MockLog.debug(s"${x.toString}", 1016)
        MockLog.debug(s"${IsInto.toString}", 1016)
        MockLog.debug(s"${x.status.toString}", 1016)
      /*if(IsInto && x.status == 2)
      {
        //system.scheduler.scheduleOnce(7.seconds) {
        MockLog.debug("PlaceBet", 1016)
        val ran1 = (new util.Random).nextInt(32767)
        val BetArea =ran1 % (7)
        val PlaceBetReceive = WrapperBaccaratMessage().withPlaceBetReceive(pb.baccarat.PlaceBetReceive(0L,BetArea,500L,1))
        Mock1016.sendRoomDispatch(FlowMeta(1016, "6", 1, "32", 2), PlaceBetReceive)
        //}
      }*/
      case x:GameStart =>
        MockLog.debug(s"${x.toString}", 1016)
      case x:GameEnd=>
        MockLog.debug(s"${x.toString}", 1016)
      case x: TableCardArray =>
        MockLog.debug(s"${x.toString}", 1016)
      case x: PlayScore =>
        MockLog.debug(s"${x.toString}", 1016)
      case x: RecordRoadArrayInt =>
        MockLog.debug(s"${x.toString}", 1016)
      case x: RecordRoadInt =>
        MockLog.debug(s"${x.toString}", 1016)
      case x: GameRecordSend =>
        MockLog.debug(s"${x.toString}", 1016)
      case x:RecordRoadArrayString =>
        MockLog.debug(s"${x.toString}", 1016)
      case x:RecordRoadString=>
        MockLog.debug(s"${x.toString}", 1016)
      case x:UserStateChange =>
        MockLog.debug(s"${x.toString}", 1016)
      case x:TipsMessage=>
        MockLog.debug(s"${x.toString}", 1016)
    }
  })

  Mock1020.observe((flowMeta, msg) => {
    msg match {
      case x: UserSitDownSend =>
        MockLog.debug(s"${x.toString}", 1020)
      case x: ApplyBankerSend =>
        MockLog.debug(s"${x.toString}", 1020)
      case x: ChangeBanker =>
        MockLog.debug(s"${x.toString}", 1020)
      case x: PlaceBetSend =>
        MockLog.debug(s"${x.toString}", 1020)
      case x: UserStateChange =>
        MockLog.debug(s"${x.toString}", 1020)
      case x:UsersItem =>
        MockLog.debug(s"${x.toString}", 1020)
      case x:UserItem=>
        MockLog.debug(s"${x.toString}", 1020)
      case x: TableCardArrayItem =>
        MockLog.debug(s"${x.toString}", 1020)
      case x: CancelBanker =>
        MockLog.debug(s"${x.toString}", 1020)
      case x: PlaceBetFail =>
        MockLog.debug(s"${x.toString}", 1020)
      case x: PlaceAllBetSend =>
        MockLog.debug(s"${x.toString}", 1020)
      case x: StatusChang =>
        MockLog.debug(s"${x.toString}", 1020)
        MockLog.debug(s"${IsInto.toString}", 1020)
        MockLog.debug(s"${x.status.toString}", 1020)
      /*if(IsInto && x.status == 2)
      {
        //system.scheduler.scheduleOnce(7.seconds) {
        MockLog.debug("PlaceBet", 1020)
        val ran1 = (new util.Random).nextInt(32767)
        val BetArea =ran1 % (7)
        val PlaceBetReceive = WrapperBaccaratMessage().withPlaceBetReceive(pb.baccarat.PlaceBetReceive(0L,BetArea,500L,1))
        Mock1020.sendRoomDispatch(FlowMeta(1020, "6", 1, "32", 2), PlaceBetReceive)
        //}
      }*/
      case x:GameStart =>
        MockLog.debug(s"${x.toString}", 1020)
      case x:GameEnd=>
        MockLog.debug(s"${x.toString}", 1020)
      case x: TableCardArray =>
        MockLog.debug(s"${x.toString}", 1020)
      case x: PlayScore =>
        MockLog.debug(s"${x.toString}", 1020)
      case x: RecordRoadArrayInt =>
        MockLog.debug(s"${x.toString}", 1020)
      case x: RecordRoadInt =>
        MockLog.debug(s"${x.toString}", 1020)
      case x: GameRecordSend =>
        MockLog.debug(s"${x.toString}", 1020)
      case x:RecordRoadArrayString =>
        MockLog.debug(s"${x.toString}", 1020)
      case x:RecordRoadString=>
        MockLog.debug(s"${x.toString}", 1020)
      case x:UserStateChange =>
        MockLog.debug(s"${x.toString}", 1020)
      case x:TipsMessage=>
        MockLog.debug(s"${x.toString}", 1020)
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
      case "CancelBanker" =>  //URL: http://127.0.0.1:9999/1012?msg=CancelBanker&userId=?&roomId=?&ChairID=?&ListApplyCount=?
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
        val PlaceBetReceive = WrapperBaccaratMessage().withPlaceBetReceive(pb.baccarat.PlaceBetReceive(ChairID.toInt,BetArea.toInt,BetScore.toLong,AndroidUser.toInt))
        Mock1012.sendRoomDispatch(FlowMeta(1012, userId, 1, roomId, 2), PlaceBetReceive)
      //用户退出百家乐
      case "UserStandUp" =>  //URL: http://127.0.0.1:9999/1012?msg=UserStandUp&userId=?&roomId=?&ChairID=?
        MockLog.debug(s"${m.toString}", 1012)
        val ChairID = m("ChairID")
        val userId = m("userId")
        val roomId = m("roomId")
        val UserStandUp = WrapperBaccaratMessage().withUserStandUp(pb.baccarat.UserStandUp(ChairID.toInt))
        Mock1012.sendRoomDispatch(FlowMeta(1012, userId, 1, roomId, 2), UserStandUp)
        MockLog.debug(s"${UserStandUp.toString}", 1012)
      case "GameRecordReceive" => //URL: http://127.0.0.1:9999/1012?msg=GameRecordReceive&userId=?&roomId=?
        MockLog.debug(s"${m.toString}", 1012)
        val userId = m("userId")
        val roomId = m("roomId")
        val UserStandUp = WrapperBaccaratMessage().withGameRecordReceive(pb.baccarat.GameRecordReceive(userId.toLong))
        Mock1012.sendRoomDispatch(FlowMeta(1012, userId, 1, roomId, 2), UserStandUp)
        MockLog.debug(s"${UserStandUp.toString}", 1012)
      case "b" =>
        MockLog.debug(s"${m("code2")}", 1012)
    }
  )

  MockWeb.observe("1014", (_, m) =>
    m("msg") match {
      //用户进入龙虎斗
      case "DragonTigerUserSitDownReceive" =>  //URL: http://127.0.0.1:9999/1014?msg=DragonTigerUserSitDownReceive&userId=?&roomId=?
        val userId = m("userId")
        val roomId = m("roomId")
        MockLog.debug(s"${userId.toString}", 1014)
        if(userId.toInt == 6) {
          IsInto = true
          MockLog.debug(s"${IsInto.toString}", 1014)
        }
        val UserSitDownReceive = WrapperDragonTigerMessage().withUserSitDownReceive(pb.dragonTiger.DragonTigerUserSitDownReceive(userId.toLong, roomId.toInt))
        Mock1014.sendRoomDispatch(FlowMeta(1014, userId, 1, roomId, 2), UserSitDownReceive)
      //用户申请庄家
      case "DragonTigerApplyBankerReceive" =>  //URL: http://127.0.0.1:9999/1014?msg=DragonTigerApplyBankerReceive&userId=?&roomId=?
        val userId = m("userId")
        val roomId = m("roomId")
        val ApplyBankerReceive = WrapperDragonTigerMessage().withApplyBankerReceive(pb.dragonTiger.DragonTigerApplyBankerReceive(userId.toLong))
        Mock1014.sendRoomDispatch(FlowMeta(1014, userId, 1, roomId, 2), ApplyBankerReceive)
      //用户下庄
      case "DragonTigerCancelBanker" =>  //URL: http://127.0.0.1:9999/1014?msg=DragonTigerCancelBanker&userId=?&roomId=?&ChairID=?&ListApplyCount=?
        val userId = m("userId")
        val roomId = m("roomId")
        val CancelBanker = WrapperDragonTigerMessage().withCancelBanker(pb.dragonTiger.DragonTigerCancelBanker(userId.toLong))
        Mock1014.sendRoomDispatch(FlowMeta(1014, userId, 1, roomId, 2), CancelBanker)
      //用户下注
      case "DragonTigerPlaceBetReceive" =>  //URL: http://127.0.0.1:9999/1014?msg=DragonTigerPlaceBetReceive&userId=?&roomId=?&ChairID=?&BetArea=?&BetScore=?&AndroidUser=?
        val userId = m("userId")
        val roomId = m("roomId")
        val ChairID = m("ChairID")
        val BetArea = m("BetArea")
        val BetScore = m("BetScore")
        val AndroidUser = m("AndroidUser")
        val PlaceBetReceive = WrapperDragonTigerMessage().withPlaceBetReceive(pb.dragonTiger.DragonTigerPlaceBetReceive(ChairID.toInt,BetArea.toInt,BetScore.toLong,AndroidUser.toInt))
        Mock1014.sendRoomDispatch(FlowMeta(1014, userId, 1, roomId, 2), PlaceBetReceive)
      //用户退出百家乐
      case "DragonTigerUserStandUp" =>  //URL: http://127.0.0.1:9999/1014?msg=DragonTigerUserStandUp&userId=?&roomId=?&ChairID=?
        MockLog.debug(s"${m.toString}", 1014)
        val ChairID = m("ChairID")
        val userId = m("userId")
        val roomId = m("roomId")
        val UserStandUp = WrapperDragonTigerMessage().withUserStandUp(pb.dragonTiger.DragonTigerUserStandUp(ChairID.toInt))
        MockUser.send(MPB.toByte(FlowMeta(1014, userId, 1, roomId, 2), UserStandUp))
        MockLog.debug(s"${UserStandUp.toString}", 1014)
      case "b" =>
        MockLog.debug(s"${m("code2")}", 1014)
    }
  )

  MockWeb.observe("1016", (_, m) =>
    m("msg") match {
      //用户进入龙虎斗
      case "DiceUserSitDownReceive" =>  //URL: http://127.0.0.1:9999/1016?msg=DiceUserSitDownReceive&userId=?&roomId=?
        val userId = m("userId")
        val roomId = m("roomId")
        MockLog.debug(s"${userId.toString}", 1016)
        if(userId.toInt == 6) {
          IsInto = true
          MockLog.debug(s"${IsInto.toString}", 1016)
        }
        val UserSitDownReceive = WrapperDiceMessage().withUserSitDownReceive(pb.dice.DiceUserSitDownReceive(userId.toLong, roomId.toInt))
        Mock1016.sendRoomDispatch(FlowMeta(1016, userId, 1, roomId, 2), UserSitDownReceive)
      //用户申请庄家
      case "DiceApplyBankerReceive" =>  //URL: http://127.0.0.1:9999/1016?msg=DiceApplyBankerReceive&userId=?&roomId=?
        val userId = m("userId")
        val roomId = m("roomId")
        val ApplyBankerReceive = WrapperDiceMessage().withApplyBankerReceive(pb.dice.DiceApplyBankerReceive(userId.toLong))
        Mock1016.sendRoomDispatch(FlowMeta(1016, userId, 1, roomId, 2), ApplyBankerReceive)
      //用户下庄
      case "DiceCancelBanker" =>  //URL: http://127.0.0.1:9999/1016?msg=DiceCancelBanker&userId=?&roomId=?&ChairID=?&ListApplyCount=?
        val userId = m("userId")
        val roomId = m("roomId")
        val CancelBanker = WrapperDiceMessage().withCancelBanker(pb.dice.DiceCancelBanker(userId.toLong))
        Mock1016.sendRoomDispatch(FlowMeta(1016, userId, 1, roomId, 2), CancelBanker)
      //用户下注
      case "DicePlaceBetReceive" =>  //URL: http://127.0.0.1:9999/1016?msg=DicePlaceBetReceive&userId=?&roomId=?&ChairID=?&BetArea=?&BetScore=?&AndroidUser=?
        val userId = m("userId")
        val roomId = m("roomId")
        val ChairID = m("ChairID")
        val BetArea = m("BetArea")
        val BetScore = m("BetScore")
        val AndroidUser = m("AndroidUser")
        val PlaceBetReceive = WrapperDiceMessage().withPlaceBetReceive(pb.dice.DicePlaceBetReceive(ChairID.toInt,BetArea.toInt,BetScore.toLong,AndroidUser.toInt))
        Mock1016.sendRoomDispatch(FlowMeta(1016, userId, 1, roomId, 2), PlaceBetReceive)
      //用户退出百家乐
      case "DiceUserStandUp" =>  //URL: http://127.0.0.1:9999/1016?msg=DiceUserStandUp&userId=?&roomId=?&ChairID=?
        MockLog.debug(s"${m.toString}", 1016)
        val ChairID = m("ChairID")
        val userId = m("userId")
        val roomId = m("roomId")
        val UserStandUp = WrapperDiceMessage().withUserStandUp(pb.dice.DiceUserStandUp(ChairID.toInt))
        MockUser.send(MPB.toByte(FlowMeta(1016, userId, 1, roomId, 2), UserStandUp))
        MockLog.debug(s"${UserStandUp.toString}", 1016)
      case "b" =>
        MockLog.debug(s"${m("code2")}", 1016)
    }
  )

  MockWeb.observe("1020", (_, m) =>
    m("msg") match {
      //用户进入龙虎斗
      case "BullFightUserSitDownReceive" =>  //URL: http://127.0.0.1:9999/1020?msg=BullFightUserSitDownReceive&userId=?&roomId=?
        val userId = m("userId")
        val roomId = m("roomId")
        MockLog.debug(s"${userId.toString}", 1020)
        if(userId.toInt == 6) {
          IsInto = true
          MockLog.debug(s"${IsInto.toString}", 1020)
        }
        val UserSitDownReceive = WrapperBullFightMessage().withUserSitDownReceive(pb.bullFight.BullFightUserSitDownReceive(userId.toLong, roomId.toInt))
        Mock1020.sendRoomDispatch(FlowMeta(1020, userId, 1, roomId, 2), UserSitDownReceive)
      //用户申请庄家
      case "BullFightApplyBankerReceive" =>  //URL: http://127.0.0.1:9999/1020?msg=BullFightApplyBankerReceive&userId=?&roomId=?
        val userId = m("userId")
        val roomId = m("roomId")
        val ApplyBankerReceive = WrapperBullFightMessage().withApplyBankerReceive(pb.bullFight.BullFightApplyBankerReceive(userId.toLong))
        Mock1020.sendRoomDispatch(FlowMeta(1020, userId, 1, roomId, 2), ApplyBankerReceive)
      //用户下庄
      case "BullFightCancelBanker" =>  //URL: http://127.0.0.1:9999/1020?msg=BullFightCancelBanker&userId=?&roomId=?&ChairID=?&ListApplyCount=?
        val userId = m("userId")
        val roomId = m("roomId")
        val CancelBanker = WrapperBullFightMessage().withCancelBanker(pb.bullFight.BullFightCancelBanker(userId.toLong))
        Mock1020.sendRoomDispatch(FlowMeta(1020, userId, 1, roomId, 2), CancelBanker)
      //用户下注
      case "BullFightPlaceBetReceive" =>  //URL: http://127.0.0.1:9999/1020?msg=BullFightPlaceBetReceive&userId=?&roomId=?&ChairID=?&BetArea=?&BetScore=?&AndroidUser=?
        val userId = m("userId")
        val roomId = m("roomId")
        val ChairID = m("ChairID")
        val BetArea = m("BetArea")
        val BetScore = m("BetScore")
        val AndroidUser = m("AndroidUser")
        val PlaceBetReceive = WrapperBullFightMessage().withPlaceBetReceive(pb.bullFight.BullFightPlaceBetReceive(ChairID.toInt,BetArea.toInt,BetScore.toLong,AndroidUser.toInt))
        Mock1020.sendRoomDispatch(FlowMeta(1020, userId, 1, roomId, 2), PlaceBetReceive)
      //用户退出百家乐
      case "BullFightUserStandUp" =>  //URL: http://127.0.0.1:9999/1020?msg=BullFightUserStandUp&userId=?&roomId=?&ChairID=?
        MockLog.debug(s"${m.toString}", 1020)
        val ChairID = m("ChairID")
        val userId = m("userId")
        val roomId = m("roomId")
        val UserStandUp = WrapperBullFightMessage().withUserStandUp(pb.bullFight.BullFightUserStandUp(ChairID.toInt))
        MockUser.send(MPB.toByte(FlowMeta(1020, userId, 1, roomId, 2), UserStandUp))
        MockLog.debug(s"${UserStandUp.toString}", 1020)
      case "b" =>
        MockLog.debug(s"${m("code2")}", 1020)
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
