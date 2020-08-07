package com.amzport.mock.test

import scalafx.application.JFXApp
import scalafx.scene.control.{Alert, ButtonType, Dialog, TextInputDialog}
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.ButtonBar.ButtonData
//import scalafx.application.JFXApp.PrimaryStage
//import scalafx.geometry.Insets
//import scalafx.scene.Scene
//import scalafx.scene.effect.DropShadow
//import scalafx.scene.layout.HBox
//import scalafx.scene.paint.Color._
//import scalafx.scene.paint.{Stops, LinearGradient}
//import scalafx.scene.text.Text


object GuiText extends JFXApp{
//  new Alert(AlertType.Information) {
//    initOwner(stage)
//    title = "Information Dialog"
//    headerText = "Look, an Information Dialog."
//    contentText = "I have a great message for you!"
//  }.showAndWait()
  case class Result(username: String, password: String)

  val dialog = new Dialog[Result]() {
    initOwner(stage)
    title = "Login"
    headerText = "Hello"
  }
  val loginButtonType = new ButtonType("Login",ButtonData.OKDone)
  dialog.dialogPane()
  //  dialog.dialogPane().buttonTypes = Seq(loginButtonType,ButtonType.Cancel)
  //  val dialog = new TextInputDialog(defaultValue = "LJH") {
//    initOwner(stage)
//    title = "用户登录"
//    contentText = "请输入用户名"
//  }
//
//  val result = dialog.showAndWait()
//
//  result match {
//    case Some(name) => println("your name:" + name)
//    case None => println("Dialog was canceled.")
//  }



//  stage = new PrimaryStage {
//    title = "Hello Word"
//    scene = new Scene {
//      fill = Black
//      content = new HBox {
//        padding = Insets(20)
//        children = Seq(
//          new Text {
//            text = "Hello"
//            style = "-fx-font-size: 48pt"
//            fill = new LinearGradient(
//              endX = 0,
//              stops = Stops(PaleGreen,SeaGreen))
//          },
//          new Text {
//            text = "Word!!!"
//            style = "-fx-font-size: 48pt"
//            fill = new LinearGradient(
//              endX = 0,
//              stops = Stops(Cyan, DodgerBlue))
//            effect = new DropShadow {
//              color = DodgerBlue
//              radius = 25
//              spread = 0.25
//            }
//          }
//        )
//      }
//    }
//  }
}
