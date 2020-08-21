package com.amzport.manager


import com.amzport.manager.model.UserInfo
import com.amzport.mock.MPB.FlowMeta
import javafx.event.{ActionEvent, EventHandler}
import javafx.scene.input.KeyEvent
import scalafx.application.JFXApp
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.Scene
import scalafx.scene.control.{Button, Label, PasswordField, TextField}
import scalafx.scene.layout.{GridPane, HBox}
import scalafx.scene.text.{Font, FontWeight, Text}
import com.amzport.mock.MockHTTP.{AuthMeta, postJson, rspEntityJson}
import com.amzport.mock.space.Mock000
import com.amzport.mock.{MockAdmin, MockHTTP, MockLog, MockUser}
import com.amzport.mock.test.AdminTest._
import pb.admin.{AllRoomInfoListRsp, HistoryProfitAndLossListRsp, LiveProfitAndLossListRsp, LogonUserAmount, ManagerTipsInfo}
import play.api.libs.json.Json

import scala.concurrent.ExecutionContext



object HelloStageDemo extends JFXApp {

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

  val loginStage = new JFXApp.PrimaryStage {
    title.value = "ScalaFX Welcome"
    width = 1280
    height = 750
  }
  val grid = new GridPane()
  grid.setAlignment(Pos.Center)
  grid.setHgap(10)
  grid.setVgap(10)
  grid.setPadding(Insets(25))
  val scenetitle = new Text("Welcome")
  scenetitle.setFont(Font.font("Tahoma",FontWeight.Normal,20))
  grid.add(scenetitle,0,0,2,1)

  val userName = new Label("User Name:")
  grid.add(userName,0,1)
  val userTextField = new TextField()
  grid.add(userTextField,1,1)

  val pw = new Label("Password:")
  grid.add(pw,0,2)
  val pwBox = new PasswordField()
  grid.add(pwBox,1,2)

  val loginLabel = new Label("loginURL")
  grid.add(loginLabel,0,3)
  val loginContent = new TextField()
  grid.add(loginContent,1,3)

  val socketLabel = new Label("socketURL")
  grid.add(socketLabel,0,4)
  val socketContent = new TextField()
  grid.add(socketContent,1,4)

  var userNameContent = ""
  var passwordContent = ""
  var loginUrlContent = ""
  var socketUrlContent = ""
  userTextField.text.onChange { (_,_,newValue) =>
    userNameContent = newValue
  }
  pwBox.text.onChange { (_,_,newValue) =>
    passwordContent = newValue
  }
  loginContent.text.onChange { (_,_,newValue) =>
    loginUrlContent = newValue
  }
  socketContent.text.onChange { (_,_,newValue) =>
    socketUrlContent = newValue
  }

  val btn = new Button("Sign in")
  grid.add(btn,1,5)
  btn.setOnAction(new EventHandler[ActionEvent] {
    override def handle(event: ActionEvent): Unit = {
  //      http://192.168.5.29:9000/api/auth/login
//      ws://192.168.5.29:9000/wsz
      println(userNameContent,passwordContent,loginUrlContent,socketUrlContent)
      MockAdmin.setupMockAdmin(userNameContent,passwordContent,loginUrlContent,socketUrlContent)
    }
  })
  pwBox.setOnKeyPressed(new EventHandler[KeyEvent] {
    override def handle(event: KeyEvent): Unit = {
      if (event.getCode().getName == "Enter") {
        val window = new TabStage
        loginStage.close()
      }
    }
  })

  val btn2 = new Button("查询注册用户数量")
  grid.add(btn2,1,6)
  btn2.setOnAction(new EventHandler[ActionEvent] {
    override def handle(event: ActionEvent): Unit = {
      println(MockAdmin.accountId)
      val logonUser = pb.admin.QueryLogonUser(MockAdmin.accountId.toLong)
      Mock000.sendQueryLogonUser(FlowMeta(1002, MockAdmin.accountId, 1, "1", 2),logonUser)
    }
  })

  val hbBth = new HBox(10)
  hbBth.setAlignment(Pos.BottomRight)
  hbBth.getChildren().add(btn)
  grid.add(hbBth,1,5)

  val actiontarget = new Text()
  grid.add(actiontarget,1,6)


  val scene = new Scene(grid,300,275)
  loginStage.setScene(scene)
  loginStage.show()

}


