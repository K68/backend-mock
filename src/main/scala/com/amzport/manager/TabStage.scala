package com.amzport.manager

import javafx.scene.control.Tab
import scalafx.geometry.Pos
import scalafx.scene.Scene
import scalafx.scene.control.TabPane.TabClosingPolicy
import scalafx.scene.control.{Button, TabPane}
import scalafx.scene.layout.{AnchorPane, HBox, VBox}
import scalafx.stage.Stage

 class TabStage {
  val tabStage = new Stage {
    title = "TabStage"
    width = 1900
    height = 960
  }
  println(111)
  val an = new AnchorPane()
  val scene = new Scene(an)

  val tabPane = new TabPane()
   tabPane.setStyle("-fx-background-color:#FFA07A")
   tabPane.setPrefSize(1900,960)
  val tab1 = new Tab("tab1")
  val tab2 = new Tab("tab2")
  val tab3 = new Tab("tab3")

   val hbox = new HBox(10)
   hbox.setAlignment(Pos.Center)
   hbox.getChildren().addAll(new Button("button1"),new Button("button2"))
   tab1.setContent(hbox)

   val vbox = new VBox(10)
   vbox.setAlignment(Pos.Center)
   vbox.getChildren().addAll(new Button("button3"),new Button("button4"))
   tab2.setContent(vbox)

  tabPane.getTabs().addAll(tab1,tab2,tab3)
   tabPane.getSelectionModel().select(tab1) //设置默认选中
   tabPane.setTabClosingPolicy(TabClosingPolicy.Unavailable)

//   AnchorPane.setTopAnchor(tabPane,100)
//   AnchorPane.setLeftAnchor(tabPane,100)

  an.getChildren().add(tabPane)

   tabStage.setScene(scene)
   tabStage.show()

}
