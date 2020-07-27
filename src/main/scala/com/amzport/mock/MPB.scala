package com.amzport.mock

import com.google.protobuf.{ByteString, CodedInputStream}
import pb.miracle.wrapper.MiracleWrapperMessage
import scalapb.{GeneratedMessage, GeneratedMessageCompanion}

import scala.util.{Random, Try}

object MPB {

  case class FlowMeta(space: Int, fromId: String = "", fromType: Int = 0, toId: String = "", toType: Int = 0)

  def metaToDataNode(fm: FlowMeta): (DataNode, DataNode) = {
    (DataNode(fm.fromId, fm.fromType), DataNode(fm.toId, fm.toType))
  }

  def metaToDataFlow[T](fm: FlowMeta, data: T): DataFlow[T] = {
    val f = DataNode(fm.fromId, fm.fromType)
    val t = DataNode(fm.toId, fm.toType)
    DataFlow(f, t, fm.space, data)
  }

  def mwmToMeta(m: MiracleWrapperMessage): FlowMeta = {
    FlowMeta(m.space, m.fromId, m.fromType, m.toId, m.toType)
  }

  def nodeToMeta(space: Int, nf: DataNode, nt: DataNode): FlowMeta = {
    FlowMeta(space, nf.id, nf.nodeType, nt.id, nt.nodeType)
  }

  def parse(source: Array[Byte]): Option[(FlowMeta, CodedInputStream)] = {
    val a = Try(MiracleWrapperMessage.parseFrom(source))
    if (a.isSuccess) {
      Some((mwmToMeta(a.get), a.get.value.newCodedInput()))
    } else {
      None
    }
  }

  def parseBs(source: Array[Byte]): Option[(FlowMeta, ByteString)] = {
    val a = Try(MiracleWrapperMessage.parseFrom(source))
    if (a.isSuccess) {
      Some((mwmToMeta(a.get), a.get.value))
    } else {
      None
    }
  }

  def parse(space: Int, source: Array[Byte]): Option[(FlowMeta, CodedInputStream)] = {
    val a = Try(MiracleWrapperMessage.parseFrom(source))
    if (a.isSuccess) {
      if (a.get.space == space) {
        Some((mwmToMeta(a.get), a.get.value.newCodedInput()))
      } else {
        None
      }
    } else {
      None
    }
  }

  def parseBs(space: Int, source: Array[Byte]): Option[(FlowMeta, ByteString)] = {
    val a = Try(MiracleWrapperMessage.parseFrom(source))
    if (a.isSuccess) {
      if (a.get.space == space) {
        Some((mwmToMeta(a.get), a.get.value))
      } else {
        None
      }
    } else {
      None
    }
  }

  def toByte(meta: FlowMeta, data: GeneratedMessage): Array[Byte] = {
    MiracleWrapperMessage(meta.space, meta.fromId, meta.fromType, meta.toId, meta.toType, data.toByteString).toByteArray
  }

  def toByte(meta: FlowMeta, data: ByteString): Array[Byte] = {
    MiracleWrapperMessage(meta.space, meta.fromId, meta.fromType, meta.toId, meta.toType, data).toByteArray
  }

  def parseWrap[A <: GeneratedMessage](wrapper: GeneratedMessageCompanion[A], source: CodedInputStream): Option[A] = {
    Try(wrapper.parseFrom(source)).toOption
  }

  def parseWrap[A <: GeneratedMessage](wrapper: GeneratedMessageCompanion[A], source: ByteString): Option[A] = {
    Try(wrapper.parseFrom(source.newCodedInput())).toOption
  }

  def randomSID(): String = {
    System.currentTimeMillis().toString + Random.nextDouble().toString
  }

}

