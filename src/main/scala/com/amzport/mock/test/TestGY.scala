package com.amzport.mock.test

import com.amzport.mock.{MockLog, MockWeb}

object TestGY extends App {
  MockWeb.setupMockWeb("0.0.0.0")
  MockWeb.observe("1001", (_, m) =>
    m("msg") match {
      //用户进入聊天室
      case "UserIntoChat" =>  //URL: http://127.0.0.1:9999/1001?msg=UserIntoChat&userId=?&roomId=?
        val userId = m("userId").toLong
        val roomId = m("roomId").toInt
        //val userIntoChat =
        MockLog.debug(s"${m("code1")}", 1001)
      case "b" =>
        MockLog.debug(s"${m("code2")}", 1001)
    }
  )

  MockWeb.observe("1011", (_, m) =>
    println(m)
  )
}
