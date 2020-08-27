package com.amzport.manager

import com.amzport.mock.MPB.FlowMeta
import com.amzport.mock.{MockAdmin, MockLog}
import com.amzport.mock.space.Mock000
import javafx.event.{ActionEvent, EventHandler}
import javafx.geometry.Insets
import javafx.scene.control.Tab
import pb.admin.{AdminIpInfoRsp, AllRoomInfo, AllRoomInfoListRsp, DispatchMetas, HistoryProfitAndLossListRsp, LiveProfitAndLossListRsp, LogonUserAmount, ManagerTipsInfo, RoomsUserIn, SystemSettingsRsp, UsersInOneRoom}
import scalafx.application.Platform
import scalafx.beans.property.StringProperty
import scalafx.geometry.Pos
import scalafx.scene.Scene
import scalafx.scene.control.TabPane.TabClosingPolicy
import scalafx.scene.control.{Button, Label, ScrollPane, TabPane, TextArea, TextField}
import scalafx.scene.layout.{AnchorPane, GridPane, HBox, VBox}
import scalafx.stage.Stage

 class TabStage {

  val tabStage = new Stage {
    title = "TabStage"
    width = 1920
    height = 960
  }
  val an = new AnchorPane()
  val scene = new Scene(an)

  val tabPane = new TabPane()
//   tabPane.setStyle("-fx-background-color:#FFA07A")
  tabPane.setPrefSize(1920,960)
  val tab1 = new Tab("tab1")
  val tab2 = new Tab("tab2")
  val tab3 = new Tab("tab3")

  /*##################tab1#################*/
  val tab1An = new AnchorPane()
  //查询总用户数量
  val allUserBtn = new Button("查询总用户数量")
  allUserBtn.setOnAction(new EventHandler[ActionEvent] {
   override def handle(event: ActionEvent): Unit = {
    val logonUser = pb.admin.QueryLogonUser(MockAdmin.accountId.toLong)
    Mock000.sendQueryLogonUser(FlowMeta(1002, MockAdmin.accountId, 1, "1", 2),logonUser)
    }
   })
  AnchorPane.setTopAnchor(allUserBtn,50)
  AnchorPane.setLeftAnchor(allUserBtn,50)
  val userContetProperty = StringProperty("")
  val userContet = new TextField{
   this.text <== userContetProperty
  }
  userContet.prefWidth = 100
  userContet.disable = true
  AnchorPane.setTopAnchor(userContet,50)
  AnchorPane.setLeftAnchor(userContet,200)

  //退出登录
  val quitBtn = new Button("退出登录")
  quitBtn.setOnAction(new EventHandler[ActionEvent] {
   override def handle(event: ActionEvent): Unit = {
    val managerQuit = pb.admin.ManagerQuit(MockAdmin.accountId.toLong)
    Mock000.sendManagerQuit(FlowMeta(1002,MockAdmin.accountId,1,"1",2),managerQuit)
   }
  })
  AnchorPane.setTopAnchor(quitBtn,10)
  AnchorPane.setLeftAnchor(quitBtn,1800)

  //查看所有房间信息
  var allRoomInfoSeq = Seq.empty[AllRoomInfo]
  val allRoomInfoBtn = new Button("查看所有房间信息")
  allRoomInfoBtn.setOnAction(new EventHandler[ActionEvent] {
   override def handle(event: ActionEvent): Unit = {
    val checkAllRoomInfo = pb.admin.CheckAllRoomInfo(MockAdmin.accountId.toLong)
    Mock000.sendCheckAllRoomInfo(FlowMeta(1002,MockAdmin.accountId,1,"1",2),checkAllRoomInfo)
   }
  })
  AnchorPane.setTopAnchor(allRoomInfoBtn,110)
  AnchorPane.setLeftAnchor(allRoomInfoBtn,50)
  val allRoomNumLabel = new Label("总房间数量：")
  AnchorPane.setTopAnchor(allRoomNumLabel,115)
  AnchorPane.setLeftAnchor(allRoomNumLabel,230)
  val allRoomNumProperty = StringProperty("")
  val allRoomNum = new TextField {
   this.text <== allRoomNumProperty
  }
  allRoomNum.prefWidth = 100
  allRoomNum.disable = true
  AnchorPane.setLeftAnchor(allRoomNum,330)
  AnchorPane.setTopAnchor(allRoomNum,110)
  /*
  * 搜索
  * */
  val searchRoomInfoText = new TextField()
  searchRoomInfoText.promptText = "请输入要搜索的房间名"
  searchRoomInfoText.prefWidth = 200
  AnchorPane.setLeftAnchor(searchRoomInfoText,50)
  AnchorPane.setTopAnchor(searchRoomInfoText,150)
  val searchRoomInfoBtn = new Button("搜索")
  AnchorPane.setTopAnchor(searchRoomInfoBtn,150)
  AnchorPane.setLeftAnchor(searchRoomInfoBtn,280)
  searchRoomInfoBtn.setOnAction(new EventHandler[ActionEvent] {
   override def handle(event: ActionEvent): Unit = {
    println(allRoomInfoSeq)
    println(searchRoomInfoText.getText)
    var newAllRoomInfoSeq = Seq.empty[AllRoomInfo]
    if(searchRoomInfoText.getText == "") {
     newAllRoomInfoSeq = allRoomInfoSeq
    }else {
     newAllRoomInfoSeq =  allRoomInfoSeq.filter(v => v.roomName == searchRoomInfoText.getText)
    }
    allRoomNumProperty.value  = newAllRoomInfoSeq.length.toString
    allRoomInfoProperty.value = newAllRoomInfoSeq.map(v =>
     s"""roomId:${v.roomId}, meta:${v.meta}, space:${v.space}, roomName:${v.roomName}
        |
        |""".stripMargin
    ).toString().substring(6)
   }
  })
  val allRoomInfoProperty = StringProperty("")
  val allRoomInfo = new TextArea {
   this.text <== allRoomInfoProperty
  }
  allRoomInfo.prefWidth = 500
  allRoomInfo.prefHeight = 500
  allRoomInfo.setEditable(false) //是否可编辑
  allRoomInfo.setWrapText(true) //是否可换行
  AnchorPane.setLeftAnchor(allRoomInfo,50)
  AnchorPane.setTopAnchor(allRoomInfo,200)

  //查看历史盈亏统计
  val checkProfitAndLossBtn = new Button("查看历史盈亏统计")
  checkProfitAndLossBtn.setOnAction(new EventHandler[ActionEvent] {
   override def handle(event: ActionEvent): Unit = {
    val checkProfitAndLossInfo = pb.admin.CheckProfitAndLoss(MockAdmin.accountId.toLong)
    Mock000.sendCheckProfitAndLoss(FlowMeta(1002,MockAdmin.accountId,1,"1",2),checkProfitAndLossInfo)
   }
  })
  AnchorPane.setTopAnchor(checkProfitAndLossBtn,110)
  AnchorPane.setLeftAnchor(checkProfitAndLossBtn,750)
  val checkProfitAndLossProperty = StringProperty("")
  val checkProfitAndLossInfo = new TextArea {
    this.text <== checkProfitAndLossProperty
  }
  checkProfitAndLossInfo.prefHeight = 500
  checkProfitAndLossInfo.prefWidth = 500
  AnchorPane.setLeftAnchor(checkProfitAndLossInfo,750)
  AnchorPane.setTopAnchor(checkProfitAndLossInfo,200)
  tab1An.getChildren().addAll(allUserBtn,userContet,quitBtn,allRoomInfoBtn,allRoomNumLabel,allRoomNum,allRoomInfo,checkProfitAndLossBtn,checkProfitAndLossInfo,searchRoomInfoBtn,searchRoomInfoText)
  tab1.setContent(tab1An)

  /*##################tab2#################*/
  val tab2An = new AnchorPane()
   //请求每个房间下的最大人数和当人数等信息
  val getDispatchMetasBtn = new Button("请求每个房间下的最大人数和当人数等信息")
  getDispatchMetasBtn.setOnAction(new EventHandler[ActionEvent] {
   override def handle(event: ActionEvent): Unit = {
    val getDispatchMetasInfo = pb.admin.GetDispatchMetas(MockAdmin.accountId.toLong)
    Mock000.sendGetDispatchMetas(FlowMeta(1002,MockAdmin.accountId,1,"1",2),getDispatchMetasInfo)
   }
  })
  AnchorPane.setTopAnchor(getDispatchMetasBtn,30)
  AnchorPane.setLeftAnchor(getDispatchMetasBtn,20)
//   val tab2RoomIdText = new TextArea()
//   tab2RoomIdText.prefWidth = 30
//   tab2RoomIdText.prefHeight = 10
//   tab2RoomIdText.promptText = "请输入房间ID"
//   AnchorPane.setLeftAnchor(tab2RoomIdText,500)
//   AnchorPane.setRightAnchor(tab2RoomIdText,30)
//   val tab2RoomIdBtn = new Button("查询")
//   tab2RoomIdBtn.onAction = (ev) => {
//     println("hhh")
//   }
//   AnchorPane.setLeftAnchor(tab2RoomIdBtn,600)
//   AnchorPane.setTopAnchor(tab2RoomIdBtn,30)
  val getDispatchMetasProperty = StringProperty("")
  val tab2Vbox1 = new VBox(10.0)
  tab2Vbox1.setSpacing(10.0)
  val tab2scp1 = new ScrollPane()

  tab2scp1.setContent(tab2Vbox1)
  tab2scp1.setPrefWidth(650.0)
  tab2scp1.setPrefHeight(700.0)
  AnchorPane.setLeftAnchor(tab2scp1,20)
  AnchorPane.setTopAnchor(tab2scp1,80)
//  val getDispatchMetasInfo = new TextArea() {
//   this.text <== getDispatchMetasProperty
//  }
//  getDispatchMetasInfo.prefWidth = 500
//  getDispatchMetasInfo.prefHeight = 500
//  AnchorPane.setLeftAnchor(getDispatchMetasInfo,100)
//  AnchorPane.setTopAnchor(getDispatchMetasInfo,80)
//  getDispatchMetasInfo.setEditable(false)
//  getDispatchMetasInfo.setWrapText(true)

  //查询某个房间内的用户
  val queryRoomIdText = new TextField()
  queryRoomIdText.promptText = "请输入要查询的房间ID"
  queryRoomIdText.prefWidth = 200
  AnchorPane.setTopAnchor(queryRoomIdText,30)
  AnchorPane.setLeftAnchor(queryRoomIdText, 700)
  val queryUsersInOneRoomBtn = new Button("查询某个房间内的用户")
  queryUsersInOneRoomBtn.setOnAction(new EventHandler[ActionEvent] {
   override def handle(event: ActionEvent): Unit = {
    MockLog.info(queryRoomIdText.getText())
    val queryUsersInOneRoomInfo = pb.admin.QueryUsersInOneRoom(queryRoomIdText.getText())
    Mock000.sendQueryUsersInOneRoom(FlowMeta(1002,MockAdmin.accountId,1,"1",2),queryUsersInOneRoomInfo)
   }
  })
  AnchorPane.setTopAnchor(queryUsersInOneRoomBtn,30)
  AnchorPane.setLeftAnchor(queryUsersInOneRoomBtn,920)
  val queryUsersInOneRoomProperty = StringProperty("")
  val queryUsersInOneRoomInfo = new TextArea() {
   this.text <== queryUsersInOneRoomProperty
  }
  queryUsersInOneRoomInfo.prefHeight = 500
  queryUsersInOneRoomInfo.prefWidth = 500
  queryUsersInOneRoomInfo.setEditable(false)
  queryUsersInOneRoomInfo.setWrapText(true)
  AnchorPane.setLeftAnchor(queryUsersInOneRoomInfo,700)
  AnchorPane.setTopAnchor(queryUsersInOneRoomInfo,80)

  //查询用户在哪些游戏房间中
  val userIdText = new TextField()
  userIdText.promptText = "请输入要查询的用户ID"
  userIdText.prefWidth = 200
  AnchorPane.setTopAnchor(userIdText,30)
  AnchorPane.setLeftAnchor(userIdText,1250)
  val queryWhereIsUserBtn = new Button("查询用户在哪些游戏房间中")
  queryWhereIsUserBtn.setOnAction(new EventHandler[ActionEvent] {
   override def handle(event: ActionEvent): Unit = {
    MockLog.info(userIdText.getText)
    val queryWhereIsUserInfo = pb.admin.QueryWhereIsUser(userIdText.getText.toLong)
    Mock000.sendQueryWhereIsUser(FlowMeta(1002,MockAdmin.accountId,1,"1",2),queryWhereIsUserInfo)
   }
  })
  AnchorPane.setTopAnchor(queryWhereIsUserBtn,30)
  AnchorPane.setLeftAnchor(queryWhereIsUserBtn,1490)
  val queryWhereIsUserProperty = StringProperty("")
  val queryWhereIsUserInfo = new TextArea {
   this.text <== queryWhereIsUserProperty
  }
  queryWhereIsUserInfo.prefWidth = 500
  queryWhereIsUserInfo.prefHeight = 500
  queryWhereIsUserInfo.setEditable(false)
  queryWhereIsUserInfo.setWrapText(true)
  AnchorPane.setLeftAnchor(queryWhereIsUserInfo,1250)
  AnchorPane.setTopAnchor(queryWhereIsUserInfo,80)

  tab2An.getChildren().addAll(getDispatchMetasBtn, tab2scp1, queryRoomIdText, queryUsersInOneRoomBtn, queryUsersInOneRoomInfo, userIdText, queryWhereIsUserBtn, queryWhereIsUserInfo)
  tab2.setContent(tab2An)

   /*##################tab3#################*/
   val tab3An = new AnchorPane()
   //管理员向所有房间发送消息
   val sendMsgToAllRoomsSpace = new TextField()
   sendMsgToAllRoomsSpace.promptText = "请输入游戏space"
   sendMsgToAllRoomsSpace.prefWidth = 150
   AnchorPane.setTopAnchor(sendMsgToAllRoomsSpace,30)
   AnchorPane.setLeftAnchor(sendMsgToAllRoomsSpace,30)
   val sendMsgToAllRoomsMeta = new TextField()
   sendMsgToAllRoomsMeta.promptText = "请输入游戏meta"
   sendMsgToAllRoomsMeta.prefWidth = 280
   AnchorPane.setTopAnchor(sendMsgToAllRoomsMeta,30)
   AnchorPane.setLeftAnchor(sendMsgToAllRoomsMeta,195)
   val sendMsgToAllRoomsBtn = new Button("搜索")
   sendMsgToAllRoomsBtn.onAction = (ev) => {
//     val sendMsgToRoomsInfo = pb.admin.SendMsgToRooms(sendMsgToRoomsIdSeq,sendMsgToRoomsContent.getText)
//     Mock000.sendMsgToRooms(FlowMeta(1002,MockAdmin.accountId,1,"1",2),sendMsgToRoomsInfo)
     val sendMsgToAllRoomsInfo = pb.admin.SendMsgToAllRooms(sendMsgToAllRoomsSpace.getText.toInt,sendMsgToAllRoomsMeta.getText,sendMsgToAllRoomsContent.getText)
     Mock000.sendMsgToAllRooms(FlowMeta(1002,MockAdmin.accountId,1,"1",2),sendMsgToAllRoomsInfo)
   }
   AnchorPane.setLeftAnchor(sendMsgToAllRoomsBtn,480)
   AnchorPane.setTopAnchor(sendMsgToAllRoomsBtn,30)
   val sendMsgToAllRoomsContent = new TextArea()
   sendMsgToAllRoomsContent.prefWidth = 500.0
   sendMsgToAllRoomsContent.prefHeight = 500.0
   sendMsgToAllRoomsContent.setWrapText(true)
   AnchorPane.setLeftAnchor(sendMsgToAllRoomsContent,30)
   AnchorPane.setTopAnchor(sendMsgToAllRoomsContent,80)


   //管理员向指定房间发送消息
   val sendMsgToRoomsText = new TextField()
   sendMsgToRoomsText.promptText = "请输入所有待发送消息房间的ID"
   sendMsgToRoomsText.prefWidth = 430
   AnchorPane.setTopAnchor(sendMsgToRoomsText,30)
   AnchorPane.setLeftAnchor(sendMsgToRoomsText,570)
   val sendMsgToRoomsBtn = new Button("发送")
   sendMsgToRoomsBtn.onAction = (ev) => {
     val sendMsgToRoomsIdSeq = sendMsgToRoomsText.getText.split("\\,")
     val sendMsgToRoomsInfo = pb.admin.SendMsgToRooms(sendMsgToRoomsIdSeq,sendMsgToRoomsContent.getText)
     Mock000.sendMsgToRooms(FlowMeta(1002,MockAdmin.accountId,1,"1",2),sendMsgToRoomsInfo)
   }
   AnchorPane.setLeftAnchor(sendMsgToRoomsBtn,1020)
   AnchorPane.setTopAnchor(sendMsgToRoomsBtn,30)
   val sendMsgToRoomsContent = new TextArea()
   sendMsgToRoomsContent.prefWidth = 500.0
   sendMsgToRoomsContent.prefHeight = 500.0
   sendMsgToRoomsContent.setWrapText(true)
   AnchorPane.setTopAnchor(sendMsgToRoomsContent,80)
   AnchorPane.setLeftAnchor(sendMsgToRoomsContent,570)

   //查询ip信息
   val allIpInfoBtn = new Button("查询所有ip信息")
   val commonIpInfoBtn = new Button("查询普通用户ip信息")
   allIpInfoBtn.onAction = (ev) => {
     println(0)
   }
   commonIpInfoBtn.onAction = (ev) => {
     println(1)
   }
   AnchorPane.setTopAnchor(allIpInfoBtn,30)
   AnchorPane.setLeftAnchor(allIpInfoBtn,1210)
   AnchorPane.setTopAnchor(commonIpInfoBtn,30)
   AnchorPane.setLeftAnchor(commonIpInfoBtn,1450)
   val ipInfoContent = new TextArea()
   ipInfoContent.prefWidth = 500.0
   ipInfoContent.prefHeight = 500.0
   ipInfoContent.setWrapText(true)
   AnchorPane.setLeftAnchor(ipInfoContent,1210)
   AnchorPane.setTopAnchor(ipInfoContent,30)


   tab3An.getChildren().addAll(sendMsgToRoomsText, sendMsgToRoomsBtn, sendMsgToRoomsContent, sendMsgToAllRoomsSpace, sendMsgToAllRoomsMeta, sendMsgToAllRoomsBtn, sendMsgToAllRoomsContent, allIpInfoBtn, commonIpInfoBtn)
   tab3.setContent(tab3An)

  tabPane.getTabs().addAll(tab1,tab2,tab3)
  tabPane.getSelectionModel().select(tab1) //设置默认选中
  tabPane.setTabClosingPolicy(TabClosingPolicy.Unavailable)


  an.getChildren().add(tabPane)

  tabStage.setScene(scene)
  tabStage.show()

  var logonUserNum = ""
  var tipInfo = ""

  Mock000.observe((flowMeta,msg) => {
   msg match {
    case x:LogonUserAmount =>
     println(x.userAmount.toString)
     Platform.runLater(() => {
      userContetProperty.value = x.userAmount.toString
     })
     MockLog.debug(s"${x.toString}", 1002)
    case x:ManagerTipsInfo =>
     tipInfo = x.toString
     println(tipInfo)
     MockLog.debug(s"${x.toString}",1002)
    case x:AllRoomInfoListRsp =>
     Platform.runLater(() => {
      allRoomInfoSeq = x.allRoomInfo
      allRoomNumProperty.value = x.allRoomInfo.length.toString
      allRoomInfoProperty.value = x.allRoomInfo.map(v =>
       s"""roomId:${v.roomId}, meta:${v.meta}, space:${v.space}, roomName:${v.roomName}
          |
          |""".stripMargin).toString().substring(6)
     })
     MockLog.debug(s"${x.toString}",1002)
    case x:HistoryProfitAndLossListRsp =>
     checkProfitAndLossProperty.value = x.profitAndLoss.map(v =>
      s"""coin:${v.coin}, inCoin:${v.inCoin}, outCoin:${v.outCoin}, inTime:${v.inTime}, outTime:${v.outTime}
         |
         |""".stripMargin).toString().substring(6)
     MockLog.debug(s"${x.toString}", 1002)
    case x:LiveProfitAndLossListRsp =>
     MockLog.debug(s"${x.toString}", 1002)
    case x:DispatchMetas =>
     Platform.runLater(() => {
      x.dispatchMeta.foreach(v => {
       val tab2HBox1 = new HBox()
       val tab2LabelRoomId = new Label("roomId:")
       val LabelIdContent = new TextField()
       LabelIdContent.setText(v.room)
       LabelIdContent.prefWidth = 75
        LabelIdContent.onMouseClicked = (ev) => {
          queryRoomIdText.setText(LabelIdContent.getText)
        }
       LabelIdContent.setEditable(false)
       val tab2LabelSpace = new Label("space:")
       val tab2SpaceContent = new TextField()
       tab2SpaceContent.setText(v.space.toString)
       tab2SpaceContent.prefWidth = 75
        tab2SpaceContent.onMouseClicked = (ev) => {
          queryRoomIdText.setText(LabelIdContent.getText)
        }
       tab2SpaceContent.setEditable(false)
       val tab2LabelNowUser = new Label("nowUser:")
       val tab2NowUserContent = new TextField()
       tab2NowUserContent.setText(v.nowUser.toString)
       tab2NowUserContent.prefWidth = 75
        tab2NowUserContent.onMouseClicked = (ev) => {
          queryRoomIdText.setText(LabelIdContent.getText)
        }
       tab2NowUserContent.setEditable(false)
       val tab2LabelMaxUser = new Label("maxUser:")
       val tab2MaxUserContent = new TextField()
       tab2MaxUserContent.setText(v.maxUser.toString)
       tab2MaxUserContent.prefWidth = 75
        tab2MaxUserContent.onMouseClicked = (ev) => {
          queryRoomIdText.setText(LabelIdContent.getText)
        }
       tab2MaxUserContent.setEditable(false)
        tab2HBox1.setPadding(new Insets(10))
        tab2HBox1.setSpacing(10)
       tab2HBox1.getChildren().addAll(tab2LabelRoomId,LabelIdContent,tab2LabelSpace,tab2SpaceContent,tab2LabelNowUser,tab2NowUserContent,tab2LabelMaxUser,tab2MaxUserContent)
       tab2Vbox1.getChildren().add(tab2HBox1)
      }
      )
     })
//     getDispatchMetasProperty.value = x.dispatchMeta.map(v =>
//     s"""roomId:${v.room}, space:${v.space}, meta:${v.meta}, address:${v.address}, nowUser:${v.nowUser}, maxUser:${v.maxUser}
//         |
//         |""".stripMargin
//     ).toString().substring(6)
     MockLog.debug(s"${x.toString}",1002)
    case x:UsersInOneRoom =>
     queryUsersInOneRoomProperty.value = x.uuid.toString().substring(6)
     MockLog.debug(s"${x.toString}",1002)
    case x:RoomsUserIn =>
     queryWhereIsUserProperty.value = x.roomId.toString().substring(6)
     MockLog.debug(s"${x.toString}",1002)
    case x:AdminIpInfoRsp =>
      MockLog.debug(s"${x.toString}",1002)
    case x:SystemSettingsRsp =>
       MockLog.debug(s"${x.toString}",1002)
   }
  })

}
