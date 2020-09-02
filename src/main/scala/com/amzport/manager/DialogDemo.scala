package com.amzport.manager

import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.control.Button
import scalafx.scene.layout.AnchorPane

object DialogDemo extends JFXApp{
  val dailogDemo = new JFXApp.PrimaryStage {
    title = "dialog"
    width = 960
    height = 720
  }
  val an = new AnchorPane()

  val btn = new Button("btn1")
  AnchorPane.setTopAnchor(btn,100) //设置距左边宽度
  AnchorPane.setLeftAnchor(btn,100) //设置距右边宽度
  an.getChildren().add(btn)

  val scene = new Scene(an)
  dailogDemo.setScene(scene)
  dailogDemo.show()
}
