package cn.blinfra.boot.system.api.logger;

import cn.blinfra.boot.system.api.logger.dto.OperateLogCreateReqDTO;

import javax.validation.Valid;

public interface OperateLogApi {

  /**
   * 创建操作日志
   *
   * @param createReqDTO 请求
   */
  void createOperateLog(@Valid OperateLogCreateReqDTO createReqDTO);
}
