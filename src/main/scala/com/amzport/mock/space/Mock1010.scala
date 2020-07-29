package com.amzport.mock.space

import akka.actor.{ActorRefFactory, ActorSystem, ClassicActorSystemProvider}
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.model.ws.TextMessage
import com.amzport.mock.MockSystem.system
import com.amzport.mock.test.Test.outActor
import pb.buyuwrapper.WrapperBuyuMessage
object Mock1010 {

  case class BuYuCass(order: Int, buYuMess: WrapperBuyuMessage)
  //捕鱼邀请消息
  val buYuInvitePlayer = pb.buyu.BuYuInvitePlayer(1, 2, 3)
  val buYuInvitePlayerMessage = WrapperBuyuMessage().withBuYuInvitePlayer(buYuInvitePlayer)



}
