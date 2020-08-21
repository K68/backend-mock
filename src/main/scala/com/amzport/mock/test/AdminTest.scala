package com.amzport.mock.test

import com.amzport.mock.{MockAdmin, MockLog, MockWeb}
import com.amzport.mock.space.Mock000
import pb.admin.{AllRoomInfoListRsp, HistoryProfitAndLossListRsp, LiveProfitAndLossListRsp, LogonUserAmount, ManagerTipsInfo}

object AdminTest extends App {
  MockAdmin.setupMockAdmin("111","111","http://192.168.5.29:9000/api/auth/login","http://192.168.5.29:9000/wsz")

  /*###############   回复    #################*/
  Mock000.observe((flowMeta,msg) => {
    msg match {
      case x:LogonUserAmount =>
        MockLog.debug(s"${x.toString}", 1002)
      case x:ManagerTipsInfo =>
        MockLog.debug(s"${x.toString}",1002)
      case x:AllRoomInfoListRsp =>
        MockLog.debug(s"${x.toString}",1002)
      case x:HistoryProfitAndLossListRsp =>
        MockLog.debug(s"${x.toString}", 1002)
      case x:LiveProfitAndLossListRsp =>
        MockLog.debug(s"${x.toString}", 1002)
    }
  })

  /*###############   发送    #################*/



}
