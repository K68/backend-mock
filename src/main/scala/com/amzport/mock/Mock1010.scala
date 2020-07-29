package com.amzport.mock

import pb.buyuwrapper.WrapperBuyuMessage

object Mock1010 {

  //捕鱼邀请消息
  val buYuInvitePlayer = pb.buyu.BuYuInvitePlayer(1, 2, 3)
  val buYuInvitePlayerMessage = WrapperBuyuMessage().withBuYuInvitePlayer(buYuInvitePlayer)

  //
}
