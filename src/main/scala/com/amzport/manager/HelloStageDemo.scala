package com.amzport.manager

import java.awt.event.ActionEvent
import java.beans.EventHandler

import scalafx.Includes._
import scalafx.application
import scalafx.application.JFXApp
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.{Group, Scene}
import scalafx.scene.control.{Button, Label, PasswordField, TextField}
import scalafx.scene.layout.{FlowPane, GridPane, HBox, Priority, VBox}
import scalafx.scene.paint.Color
import scalafx.scene.shape.Rectangle
import scalafx.scene.text.{Font, FontWeight, Text}

object HelloStageDemo extends JFXApp {

//  val root = new FlowPane()
//
//  stage = new JFXApp.PrimaryStage {
//    title.value = "Hello Stage"
//    width = 1280
//    height = 720
//    scene = new Scene(root)
//  }
//
//  root.setHgap(10)
//  root.setVgap(20)
//  root.setPadding(Insets(15))

//  val myTextField = new TextField()
//  val hbox = new HBox()
//  hbox.getChildren().add(myTextField)
//  HBox.setHgrow(myTextField, Priority.Always)
//  val scene = new Scene(hbox, 320, 112, Color.rgb(0,0,0,0))
//  stage.setScene


//  val button1 = new Button("按钮1")
//  root.children.add(button1)
//
//  val button2 = new Button("按钮2")
//  button2.setPrefSize(100, 100)
//  root.children.add(button2)
//
//  val username = new TextField() {
//    promptText = "用户名"
//  }
//  val password = new PasswordField() {
//    promptText = "密码"
//  }

  //  val grid = new GridPane() {
  //    hgap = 10
  //    vgap = 10
  //    padding = Insets(20, 100, 10, 10)
  //
  //    add(new Label("用户名："), 0, 0)
  //    add(username, 1, 0)
  //    add(new Label("密码："), 0, 1)
  //    add(password, 1, 1)
  //  }
  //
  //  root.children.add(grid)



//  val hbox = new HBox()
//  val myTextField = new TextField()
//  hbox.getChildren().add(myTextField)
//  HBox.setHgrow(myTextField, Priority.Always)
//  val myTextField2 = new TextField()
//  hbox.getChildren().add(myTextField2)
//  HBox.setHgrow(myTextField2, Priority.Always)
//  val stage2 = new JFXApp.PrimaryStage {
//    width = 1280
//    height = 720
//  }
//  val scene2 = new Scene(hbox, 320, 112, Color.rgb(0,0,0,0))
//  stage2.setScene(scene2)
//  stage2.show()

//  val root = new Group()
//  val scene = new Scene(root,300,250,Color.White)
//  val hbox = new HBox()
//  val button1 = new Button("Add          ")
//  val button2 = new Button("Remove   ")
//  HBox.setHgrow(button1, Priority.Always)
//  HBox.setHgrow(button2,Priority.Always)
//  button1.setMaxWidth(Double.MaxValue)
//  button2.setMaxWidth(Double.MaxValue)
//  hbox.getChildren().addAll(button1,button2)
//  root.getChildren().add(hbox)
//  val stage3 = new JFXApp.PrimaryStage()
//  stage3.setScene(scene)
//  stage3.show()

//  val stage4 = new JFXApp.PrimaryStage{
//    title.value = "hello stage"
//  }
//  val root = new Group()
//  val scene = new Scene(root,1280,750,Color.White)
//
//  val hbox = new HBox(8)
////  hbox.setPadding(new Insets(15,12,15,12))
//
//  val button1 = new Button("Option1")
//  val button2 = new Button("Option2")
//  val button3 = new Button("Option3")
//  HBox.setHgrow(button1,Priority.Always)
//  HBox.setHgrow(button2,Priority.Always)
//  button1.setMinWidth(200)
//  button2.setMinWidth(100)
//  button3.setMinWidth(100)
//  hbox.getChildren().addAll(button1,button2,button3)
//
//  hbox.setPrefWidth(400)
//
//  root.getChildren().add(hbox)
//  stage4.setScene(scene)
//  stage4.show()
  val stage5 = new JFXApp.PrimaryStage {
    title.value = "ScalaFX Welcome"
    width = 1280
    height = 750
  }
  val grid = new GridPane()
  grid.setAlignment(Pos.CENTER)
  grid.setHgap(10)
  grid.setVgap(10)
//  grid.setPadding(new Insets(25))
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

  val btn = new Button("Sign in")
  val hbBth = new HBox(10)
  hbBth.setAlignment(Pos.BOTTOM_RIGHT)
  hbBth.getChildren().add(btn)
  grid.add(hbBth,1,4)

  val actiontarget = new Text()
  grid.add(actiontarget,1,6)
//  btn.setOnAction(value:EventHandler[ActionEvent])
  val scene = new Scene(grid,300,275)
  stage5.setScene(scene)
  stage5.show()


}
