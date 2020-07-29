package com.amzport.mock.test

import com.amzport.mock.{MockUser, MockWeb}
import pb.buyuwrapper.WrapperBuyuMessage

object TestYJ extends App {

  MockUser.setupMockUser("112", "111", "http://127.0.0.1:9007/api/auth/login", "ws://127.0.0.1:9007/wsz")

  MockWeb.setupMockWeb("0.0.0.0")

  MockWeb.observe("333", (_, m) =>
    //URL: http://127.0.0.1:9999/1001?msg=UserIntoChat&userId=?&roomId=?
    m("BuYu") match {
      case "BuYuInv" =>
        val buYuInvitePlayer = pb.buyu.BuYuInvitePlayer(1, 2, 3)
        val buYuInvitePlayerMessage = WrapperBuyuMessage().withBuYuInvitePlayer(buYuInvitePlayer)

      case "BuYuDebug" =>

    }
  )
}
