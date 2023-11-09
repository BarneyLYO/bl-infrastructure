package cn.blinfra.boot.common.util;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.map.TableMap;
import cn.hutool.core.net.url.UrlBuilder;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.Map;

public class HttpUtils {

  @SuppressWarnings("unchecked")
  public static String replaceUrlQuery(String url, String key, String value) {

    UrlBuilder builder = UrlBuilder.of(url, Charset.defaultCharset());

    Object fieldValue =
      ReflectUtil.getFieldValue(builder.getQuery(), "query");

    TableMap<CharSequence, CharSequence> query =
      (TableMap<CharSequence, CharSequence>) fieldValue;

    query.remove(key);

    builder.addQuery(key, value);

    return builder.build();
  }

  public static String append(
    String base,
    Map<String, ?> query,
    Map<String, String> keys,
    boolean hashFragment
  ) {

    UriComponentsBuilder template = UriComponentsBuilder.newInstance();
    UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(base);
    URI redirectUri;
    try {
      redirectUri = builder.build(true).toUri();
    }
    catch (Exception e) {
      redirectUri = builder.build().toUri();
      builder = UriComponentsBuilder.fromUri(redirectUri);
    }
    template.scheme(redirectUri.getScheme())
            .port(redirectUri.getPort())
            .host(redirectUri.getHost())
            .userInfo(redirectUri.getUserInfo())
            .path(redirectUri.getPath());

    if (hashFragment) {
      StringBuilder values = new StringBuilder();
      if (redirectUri.getFragment() != null) {
        values.append(redirectUri.getFragment());
      }
      for (String key : query.keySet()) {
        if (values.length() > 0) {
          values.append("&");
        }
        String name = key;
        if (keys != null && keys.containsKey(key)) {
          name = keys.get(key);
        }
        values.append(name)
              .append("={")
              .append(key)
              .append("}");
      }
      if (values.length() > 0) {
        template.fragment(values.toString());
      }
      UriComponents encoded = template.build()
                                      .expand(query)
                                      .encode();
      builder.fragment(encoded.getFragment());
    }
    else {
      for (String key : query.keySet()) {
        String name = key;
        if (keys != null && keys.containsKey(key)) {
          name = keys.get(key);
        }
        template.queryParam(name, "{" + key + "}");
      }
      template.fragment(redirectUri.getFragment());

      UriComponents encoded = template.build()
                                      .expand(query)
                                      .encode();

      builder.query(encoded.getQuery());
    }
    return builder.build().toUriString();
  }

  public static String append(String base, Map<String, ?> query, boolean fragment) {
    return append(base, query, null, fragment);
  }

  public static String[] obtainBasicAuthorization(HttpServletRequest request) {
    String clientId;
    String clientSecret;
    String authorization = request.getHeader("Authorization");
    authorization = StrUtil.subAfter(authorization, "Basic ", true);
    if (StringUtils.hasText(authorization)) {
      authorization = Base64.decodeStr(authorization);
      clientId = StrUtil.subBefore(authorization, ":", false);
      clientSecret = StrUtil.subAfter(authorization, ":", false);
    }
    else {
      clientId = request.getParameter("client_id");
      clientSecret = request.getParameter("client_secret");
    }
    if (StrUtil.isNotEmpty(clientId) && StrUtil.isNotEmpty(clientSecret)) {
      return new String[]{clientId, clientSecret};
    }
    return null;
  }

}
