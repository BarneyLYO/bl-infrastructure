package cn.blinfra.boot.system.api.notify;

import cn.blinfra.boot.system.api.notify.dto.NotifySendSingleToUserReqDTO;

import javax.validation.Valid;

/**
 * 发送站内信API
 */
public interface NotifyMessageSendApi {

  /**
   * 发送单条站内信给Admin用户
   *
   * @param reqDTO 发送请求
   * @return 发送消息ID
   */
  Long sendSingleMessageToAdmin(@Valid NotifySendSingleToUserReqDTO reqDTO);

  /**
   * 发送单条站内信给Member用户
   *
   * @param reqDTO 发送请求
   * @return 发送消息ID
   */
  Long sendSingleMessageToMember(@Valid NotifySendSingleToUserReqDTO reqDTO);

}
