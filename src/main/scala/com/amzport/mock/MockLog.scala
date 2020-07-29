package com.amzport.mock

object MockLog {

  private var logs: List[String] = List.empty[String]

  def info(s: String, space: Int = 0): Unit = {
    logs = logs :+ s"[INFO] - ($space) - $s"
    println(logs.last)
  }
  def warn(s: String, space: Int = 0): Unit = {
    logs = logs :+ s"[WARN] - ($space) - $s"
    println(logs.last)
  }
  def error(s: String, space: Int = 0): Unit = {
    logs = logs :+ s"[ERROR] - ($space) - $s"
    println(logs.last)
  }
  def debug(s: String, space: Int = 0): Unit = {
    logs = logs :+ s"[DEBUG] - ($space) - $s"
    println(logs.last)
  }

  def clearLog(): Unit = logs = List.empty[String]
  def fetchLog(): List[String] = logs
}
