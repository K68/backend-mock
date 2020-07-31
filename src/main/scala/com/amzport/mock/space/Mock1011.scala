package com.amzport.mock.space

import com.amzport.mock.MPB.FlowMeta
import com.amzport.mock._
import com.google.protobuf.ByteString
import pb.laohuji._
import pb.laohujiwrapper.WrapperLaohujiMessage
import pb.laohujiwrapper.WrapperLaohujiMessage.Msg
import scalapb.GeneratedMessage


object Mock1011 {

  def sendLabaRoom(flowMeta: FlowMeta, m: IntoClientRoom): Unit = {
    MockLog.info(s"进入九线：${m.clientID} ${m.seat}", flowMeta.space)
    MockUser.send(MPB.toByte(flowMeta, WrapperLaohujiMessage().withIntoClientRoom(m).toByteString))
  }

  def sendStatLaba(flowMeta: FlowMeta, m: Start): Unit = {
    MockLog.info(s"开始游戏：${m.clientID} ${m.lineAmount} ${m.betAmount}", flowMeta.space)
    MockUser.send(MPB.toByte(flowMeta, WrapperLaohujiMessage().withStart(m).toByteString))
  }

  def sendOutLaba(flowMeta: FlowMeta, m: OutSlotRoom): Unit = {
    MockLog.info(s"退出九线：${m.clientID}", flowMeta.space)
    MockUser.send(MPB.toByte(flowMeta, WrapperLaohujiMessage().withOutSlotRoom(m).toByteString))
  }

  def sendSlotRank(flowMeta: FlowMeta, m: AskForSlotVideo): Unit = {
    MockLog.info(s"请求九线排行榜：${m.whichRank}", flowMeta.space)
    MockUser.send(MPB.toByte(flowMeta, WrapperLaohujiMessage().withAskForSlotVideo(m).toByteString))
  }

  def sendInvitePlayer(flowMeta: FlowMeta, m: InvitePlayer): Unit = {
    MockLog.info(s"发起邀请好友请求：${m.playerId} ${m.invitedId} ${m.seat}", flowMeta.space)
    MockUser.send(MPB.toByte(flowMeta, WrapperLaohujiMessage().withInvitePlayer(m).toByteString))
  }

  def sendPlayerChat(flowMeta: FlowMeta, m: PlayerChatIn): Unit = {
    MockLog.info(s"发出聊天消息：${m.userId} ${m.playerName} ${m.head} ${m.chatContent}", flowMeta.space)
    MockUser.send(MPB.toByte(flowMeta, WrapperLaohujiMessage().withPlayerChatIn(m).toByteString))
  }

  def sendFriendList(flowMeta: FlowMeta, m: ApplyFriendList): Unit = {
    MockLog.info(s"请求好友列表：${m.userId}", flowMeta.space)
    MockUser.send(MPB.toByte(flowMeta, WrapperLaohujiMessage().withApplyFriendList(m).toByteString))
  }

  def sendRankResult(flowMeta: FlowMeta, m: AskForRankResult): Unit = {
    MockLog.info(s"请求排行榜中某榜单的信息：${m.recordId}", flowMeta.space)
    MockUser.send(MPB.toByte(flowMeta, WrapperLaohujiMessage().withAskForRankResult(m).toByteString))
  }

  def sendChatRecord(flowMeta: FlowMeta, m: RequestChatRecord): Unit = {
    MockLog.info(s"请求聊天记录：${m.userId}", flowMeta.space)
    MockUser.send(MPB.toByte(flowMeta, WrapperLaohujiMessage().withRequestChatRecord(m).toByteString))
  }

  private def receiveMessage(space: Int, flowMeta: FlowMeta, value: ByteString): Unit = {
    MPB.parseWrap(WrapperLaohujiMessage, value)  match {
      case Some(wrapper) =>
        wrapper.msg match {
          case Msg.SlotMachine(into) =>
            callback(flowMeta, into)
          case Msg.RotateResult(info) =>
            callback(flowMeta, info)
          case Msg.SlotError(info) =>
            callback(flowMeta, info)
          case Msg.JackpotMoney(info) =>
            callback(flowMeta, info)
          case Msg.ReturnSlotVideo(info) =>
            callback(flowMeta, info)
          case Msg.PlayerChatOut(info) =>
            callback(flowMeta, info)
          case Msg.FriendsList(info) =>
            callback(flowMeta, info)
          case Msg.ReturnRankResult(info) =>
            callback(flowMeta, info)
          case Msg.ChatRecord(info) =>
            callback(flowMeta, info)
          case Msg.OtherInOutRoom(info) =>
            callback(flowMeta, info)
          case Msg.OtherState(info) =>
            callback(flowMeta, info)

          case _ =>
            MockLog.warn("useless dispatch message", space)
        }
      case None =>
        MockLog.warn(s"parse dispatch message error: $flowMeta", space)
    }
  }

  private def callback(flowMeta: FlowMeta, msg: GeneratedMessage): Unit = {
    if (callbackOpt.isDefined) {
      callbackOpt.get(flowMeta, msg)
    }
  }

  private var callbackOpt: Option[(FlowMeta, GeneratedMessage) => Unit] = None

  def observe(cb: (FlowMeta, GeneratedMessage) => Unit): Unit = {
    callbackOpt = Some(cb)
    MockUser.observe(1011, receiveMessage)
  }

}
