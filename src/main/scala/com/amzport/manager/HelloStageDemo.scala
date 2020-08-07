package com.amzport.manager

import scalafx.Includes._
import scalafx.application
import scalafx.application.JFXApp
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.control.{Button, Label, PasswordField, TextField}
import scalafx.scene.layout.{FlowPane, GridPane}

object HelloStageDemo extends JFXApp {

  val root = new FlowPane()

  stage = new JFXApp.PrimaryStage {
    title.value = "Hello Stage"
    width = 1280
    height = 720
    scene = new Scene(root)
  }

  root.setHgap(10)
  root.setVgap(20)
  root.setPadding(Insets(15))

  val button1 = new Button("按钮1")
  root.children.add(button1)

  val button2 = new Button("按钮2")
  button2.setPrefSize(100, 100)
  root.children.add(button2)

  val username = new TextField() {
    promptText = "用户名"
  }
  val password = new PasswordField() {
    promptText = "密码"
  }

  val grid = new GridPane() {
    hgap = 10
    vgap = 10
    padding = Insets(20, 100, 10, 10)

    add(new Label("用户名："), 0, 0)
    add(username, 1, 0)
    add(new Label("密码："), 0, 1)
    add(password, 1, 1)
  }

  root.children.add(grid)

}
