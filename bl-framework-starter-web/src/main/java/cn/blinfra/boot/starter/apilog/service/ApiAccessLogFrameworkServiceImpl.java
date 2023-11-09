package cn.blinfra.boot.starter.apilog.service;

import cn.blinfra.boot.infra.api.logger.ApiAccessLogApi;
import cn.blinfra.boot.infra.api.logger.dto.ApiAccessLogCreateReqDTO;
import cn.hutool.core.bean.BeanUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;

@RequiredArgsConstructor
public class ApiAccessLogFrameworkServiceImpl
  implements ApiAccessLogFrameworkService {

  private final ApiAccessLogApi apiAccessLogApi;

  @Async
  @Override
  public void createApiAccessLog(ApiAccessLog accessLog) {
    ApiAccessLogCreateReqDTO reqDTO =
      BeanUtil.copyProperties(accessLog, ApiAccessLogCreateReqDTO.class);

    apiAccessLogApi.createApiAccessLog(reqDTO);
  }
}
