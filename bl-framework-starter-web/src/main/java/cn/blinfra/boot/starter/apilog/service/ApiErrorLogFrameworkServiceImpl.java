package cn.blinfra.boot.starter.apilog.service;

import cn.blinfra.boot.infra.api.logger.ApiErrorLogApi;
import cn.blinfra.boot.infra.api.logger.dto.ApiErrorLogCreateReqDTO;
import cn.hutool.core.bean.BeanUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;

@RequiredArgsConstructor
public class ApiErrorLogFrameworkServiceImpl implements ApiErrorLogFrameworkService {
  private final ApiErrorLogApi apiErrorLogApi;

  @Async
  @Override
  public void createApiErrorLog(ApiErrorLog apiErrorLog) {
    ApiErrorLogCreateReqDTO reqDTO =
      BeanUtil.copyProperties(apiErrorLog, ApiErrorLogCreateReqDTO.class);
    apiErrorLogApi.createApiErrorLog(reqDTO);
  }
}
