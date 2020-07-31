package com.amzport.mock.space

import com.amzport.mock.{MPB, MockLog, MockUser}
import com.amzport.mock.MPB.FlowMeta
import com.google.protobuf.ByteString
import pb.chat._
import pb.chatwrapper.WrapperChatMessage
import pb.chatwrapper.WrapperChatMessage.Msg
import scalapb.GeneratedMessage


object Mock1001 {

  def sendChatRoom(flowMeta: FlowMeta, m: UserIntoChat): Unit = {
    MockLog.info(s"进入聊天室", flowMeta.space)
    MockUser.send(MPB.toByte(flowMeta, WrapperChatMessage().withUserIntoChat(m).toByteString))
  }

  def sendOpenFriends(flowMeta: FlowMeta, m: OpenMyFriends): Unit = {
    MockLog.info(s"打开好友列表", flowMeta.space)
    MockUser.send(MPB.toByte(flowMeta, WrapperChatMessage().withOpenMyFriends(m).toByteString))
  }

  def sendDeleteFriends(flowMeta: FlowMeta, m: DeleteFriend): Unit = {
    MockLog.info(s"删除好友", flowMeta.space)
    MockUser.send(MPB.toByte(flowMeta, WrapperChatMessage().withDeleteFriend(m).toByteString))
  }

  def sendFriendSearch(flowMeta: FlowMeta, m: FriendSearch): Unit = {
    MockLog.info(s"搜索好友", flowMeta.space)
    MockUser.send(MPB.toByte(flowMeta, WrapperChatMessage().withFriendSearch(m).toByteString))
  }

  def sendUserChatReceive(flowMeta: FlowMeta, m: UserChatReceive): Unit = {
    MockLog.info(s"开始聊天", flowMeta.space)
    MockUser.send(MPB.toByte(flowMeta, WrapperChatMessage().withUserChatReceive(m).toByteString))
  }

  def sendPressSend(flowMeta: FlowMeta, m: PressSend): Unit = {
    MockLog.info(s"发送信息", flowMeta.space)
    MockUser.send(MPB.toByte(flowMeta, WrapperChatMessage().withPressSend(m).toByteString))
  }

  def sendUserLeave(flowMeta: FlowMeta, m: UserLeave): Unit = {
    MockLog.info(s"用户离开", flowMeta.space)
    MockUser.send(MPB.toByte(flowMeta, WrapperChatMessage().withUserLeave(m).toByteString))
  }

  def sendOpenApplyFriend(flowMeta: FlowMeta, m: OpenApplyFriend): Unit = {
    MockLog.info(s"打开好友申请", flowMeta.space)
    MockUser.send(MPB.toByte(flowMeta, WrapperChatMessage().withOpenApplyFriend(m).toByteString))
  }

  def sendAddFriend(flowMeta: FlowMeta, m: AddFriend): Unit = {
    MockLog.info(s"同意好友申请", flowMeta.space)
    MockUser.send(MPB.toByte(flowMeta, WrapperChatMessage().withAddFriend(m).toByteString))
  }

  def sendRefuseApply(flowMeta: FlowMeta, m: RefuseApply): Unit = {
    MockLog.info(s"拒绝好友申请", flowMeta.space)
    MockUser.send(MPB.toByte(flowMeta, WrapperChatMessage().withRefuseApply(m).toByteString))
  }

  def sendApplyAddFriend(flowMeta: FlowMeta, m: ApplyAddFriend): Unit = {
    MockLog.info(s"申请好友", flowMeta.space)
    MockUser.send(MPB.toByte(flowMeta, WrapperChatMessage().withApplyAddFriend(m).toByteString))
  }

  def sendSearchPeople(flowMeta: FlowMeta, m: SearchPeople): Unit = {
    MockLog.info(s"搜索陌生人", flowMeta.space)
    MockUser.send(MPB.toByte(flowMeta, WrapperChatMessage().withSearchPeople(m).toByteString))
  }

  def sendTransfer(flowMeta: FlowMeta, m: Transfer): Unit = {
    MockLog.info(s"转账", flowMeta.space)
    MockUser.send(MPB.toByte(flowMeta, WrapperChatMessage().withTransfer(m).toByteString))
  }

  def sendCycleQuery(flowMeta: FlowMeta, m: CycleQuery): Unit = {
    MockLog.info(s"查询世界聊天记录", flowMeta.space)
    MockUser.send(MPB.toByte(flowMeta, WrapperChatMessage().withCycleQuery(m).toByteString))
  }



  private def receiveMessage(space: Int, flowMeta: FlowMeta, value: ByteString): Unit = {
    MPB.parseWrap(WrapperChatMessage, value)  match {
      case Some(wrapper) =>
        wrapper.msg match {
          case Msg.Tips(into) =>
            callback(flowMeta, into)
          case Msg.ShowMyFriends(info) =>
            callback(flowMeta, info)
          case Msg.SendMessage(info) =>
            callback(flowMeta, info)
          case Msg.HistoryChatArray(info) =>
            callback(flowMeta, info)
          case Msg.ShowApplyFriend(info) =>
            callback(flowMeta, info)
          case Msg.WorldHistoryListRsp(info) =>
            callback(flowMeta, info)
          case Msg.WorldChatRsp(info) =>
            callback(flowMeta, info)
          case Msg.ShowRecentPeople(info) =>
            callback(flowMeta, info)
          case Msg.AddFriendRsp(info) =>
            callback(flowMeta, info)
          case Msg.ChatError(info) =>
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
    MockUser.observe(1001, receiveMessage)
  }

}
