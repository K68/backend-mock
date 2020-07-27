package com.amzport.mock

trait Command

object DataNode {
  def apply(id: String, nodeType: Int): DataNode = nodeType match {
    case 0 =>
      DataSysNode(id)
    case 1 =>
      DataUserNode(id)
    case 2 =>
      DataRoomNode(id)
    case _ =>
      DataUserNode(id)
  }
}
class DataNode(val id: String, val nodeType: Int) {
  def subMatch(): DataNode = DataNode(id, nodeType)
}
case class DataSysNode(override val id: String) extends DataNode(id, nodeType = 0)
case class DataUserNode(override val id: String) extends DataNode(id, nodeType = 1)
case class DataRoomNode(override val id: String) extends DataNode(id, nodeType = 2)
// fromType & toType: 0 System, 1 User, 2 Room
case class DataFlow[T](from: DataNode, to: DataNode, space: Int, data: T) extends Command
