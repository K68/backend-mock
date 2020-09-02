package com.amzport.manager

import javafx.event.{ActionEvent, EventHandler}
import javafx.scene.input.KeyEvent
import scalafx.application.JFXApp
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.Scene
import scalafx.scene.control.{Button, Label, PasswordField, TextField}
import scalafx.scene.layout.{GridPane, HBox}
import scalafx.scene.text.{Font, FontWeight, Text}
import com.amzport.mock.MockAdmin


object HelloStageDemo extends JFXApp {

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

  val loginLabel = new Label("loginURL:")
  grid.add(loginLabel,0,3)
  //      http://121.196.58.176/api/auth/login
  val loginContent = new TextField()
  loginContent.setText("http://121.196.58.176/api/auth/login")
//  loginContent.setText("http://127.0.0.1:9000/api/auth/login")
  grid.add(loginContent,1,3)

  val socketLabel = new Label("socketURL:")
  grid.add(socketLabel,0,4)
  //      ws://121.196.58.176/wsm
  val socketContent = new TextField()
  socketContent.setText("ws://121.196.58.176/wsm")
//  socketContent.setText("ws://127.0.0.1:9000/wsm")
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
      println(userNameContent,passwordContent,loginContent.getText,socketContent.getText)
      MockAdmin.setupMockAdmin(userNameContent,passwordContent,loginContent.getText,socketContent.getText)
    }
  })
  pwBox.setOnKeyPressed(new EventHandler[KeyEvent] {
    override def handle(event: KeyEvent): Unit = {
      if (event.getCode().getName == "Enter") {
        MockAdmin.setupMockAdmin(userNameContent,passwordContent,loginContent.getText,socketContent.getText)
      }
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


