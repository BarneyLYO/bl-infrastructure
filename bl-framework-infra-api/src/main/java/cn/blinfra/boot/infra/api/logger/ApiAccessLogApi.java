package cn.blinfra.boot.infra.api.logger;

import cn.blinfra.boot.infra.api.logger.dto.ApiAccessLogCreateReqDTO;

import javax.validation.Valid;

public interface ApiAccessLogApi {
  void createApiAccessLog(@Valid ApiAccessLogCreateReqDTO createReqDTO);
}
