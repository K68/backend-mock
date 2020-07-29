package com.amzport.mock.space

import akka.actor.{ActorRefFactory, ActorSystem, ClassicActorSystemProvider}
import com.amzport.mock.MockUser
import pb.buyuwrapper.WrapperBuyuMessage

import scala.concurrent.duration._
import scala.concurrent.ExecutionContext
object Mock1010 extends App {

  case class BuYuCass(order: Int, buYuMess: WrapperBuyuMessage)
  var buYuList = List.empty[BuYuCass]

  //捕鱼邀请消息
  def buYuInvite(): Unit = {
    val buYuInvitePlayer = pb.buyu.BuYuInvitePlayer(1, 2, 3)
    val buYuInvitePlayerMessage = WrapperBuyuMessage().withBuYuInvitePlayer(buYuInvitePlayer)
    buYuList = buYuList ++: List(BuYuCass(0, buYuInvitePlayerMessage))

    val buYuInviteConfirm = pb.buyu.BuYuInviteConfirm(2, 1, true)
    val buYuInviteConfirmMessage = WrapperBuyuMessage().withBuYuInviteConfirm(buYuInviteConfirm)
    buYuList = buYuList ++: List(BuYuCass(1, buYuInviteConfirmMessage))
  }

  //MockUser.send(buYuList)

}

class Mock1010()(actorSystem: ActorSystem) (implicit executionContext: ExecutionContext){
  actorSystem.scheduler.scheduleWithFixedDelay(2.seconds, 2.seconds) { () =>

  }
}