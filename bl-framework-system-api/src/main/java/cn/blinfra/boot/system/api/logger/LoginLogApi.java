package cn.blinfra.boot.system.api.logger;

import cn.blinfra.boot.system.api.logger.dto.LoginLogCreateReqDTO;

import javax.validation.Valid;

/**
 * 登录日志的API接口
 */
public interface LoginLogApi {

  void createLoginLog(@Valid LoginLogCreateReqDTO reqDTO);
}
