package com.amzport.manager

import scalafx.stage.Stage
import javafx.beans.value.{ChangeListener, ObservableValue}
import javafx.scene.control.SeparatorMenuItem
import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.control.{CheckMenuItem, Menu, MenuBar, MenuItem, RadioMenuItem, ToggleGroup}
import scalafx.scene.image.Image
import scalafx.scene.layout.BorderPane
import scalafx.scene.paint.Color

case class MainStage() {
//  val mainStage = new Stage {
//    title = "Main"
//    width = 1280
//    height = 720
//  }
//  println(111)
//  mainStage.show()
val menuStage = new Stage {
  title = "MenuStage"
  width = 1280
  height = 760
}
  val root = new BorderPane()
  val scene = new Scene(root, 300, 250, Color.White)
  val menuBar = new MenuBar()
  menuBar.prefWidthProperty().bind(menuStage.widthProperty())
  root.setTop(menuBar)

  val fileMenu = new Menu("File")
  val newMenuItem = new MenuItem("New")
  val saveMenuItem = new MenuItem("Save")
  val exitMenuItem = new MenuItem("Exit")
  exitMenuItem.onAction = { evt =>
    println(evt)
  }
  fileMenu.getItems().addAll(newMenuItem,saveMenuItem,new SeparatorMenuItem(),exitMenuItem)

  val webMenu = new Menu("Web")
  val htmlMenuItem = new CheckMenuItem("HTML")
  htmlMenuItem.setSelected(true)
  webMenu.getItems().add(htmlMenuItem)

  val cssMenuItem = new CheckMenuItem("css")
  htmlMenuItem.setSelected(true)
  webMenu.getItems().add(cssMenuItem)

  val sqlMenu = new Menu("sql")
  val tGroup = new ToggleGroup()
  val mysqlItem = new RadioMenuItem("Mysql")
  mysqlItem.setToggleGroup(tGroup)

  val oracleItem = new RadioMenuItem("Oracle")
  oracleItem.setToggleGroup(tGroup)
  oracleItem.setSelected(true)

  sqlMenu.getItems().addAll(mysqlItem,oracleItem,new SeparatorMenuItem())

  val tutorialManeu = new Menu("Tutorial")
  tutorialManeu.getItems().addAll(
    new CheckMenuItem("Java"),
    new CheckMenuItem("JavaFX"),
    new CheckMenuItem("Scala"))
  sqlMenu.getItems().add(tutorialManeu)

  menuBar.getMenus().addAll(fileMenu,webMenu,sqlMenu)

  menuStage.heightProperty().addListener(new ChangeListener[Number] {  //监听高度的变化
    override def changed(observable: ObservableValue[_ <: Number], oldValue: Number, newValue: Number): Unit = {
      println(oldValue,newValue)
    }
  })
  menuStage.setScene(scene)
  menuStage.show()
}
