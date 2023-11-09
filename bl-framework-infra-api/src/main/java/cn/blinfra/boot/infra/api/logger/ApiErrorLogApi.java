package cn.blinfra.boot.infra.api.logger;

import cn.blinfra.boot.infra.api.logger.dto.ApiErrorLogCreateReqDTO;

import javax.validation.Valid;

public interface ApiErrorLogApi {
  void createApiErrorLog(@Valid ApiErrorLogCreateReqDTO reqDTO);
}
