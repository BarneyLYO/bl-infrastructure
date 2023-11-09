package cn.blinfra.boot.starter.security;

import cn.hutool.core.map.MapUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class LoginUser {

  private Long id;

  private Integer userType;

  private Long tenantId;

  private List<String> scopes;

  @JsonIgnore
  private Map<String, Object> context;

  public void setContext(String key, Object value) {
    if (context == null) {
      context = new HashMap<>();
    }
    context.put(key, value);
  }

  public <T> T getContext(String key, Class<T> type) {
    return MapUtil.get(context, key, type);
  }
}
