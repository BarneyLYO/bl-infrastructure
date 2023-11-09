package cn.blinfra.boot.starter.xss;

import lombok.AllArgsConstructor;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@AllArgsConstructor
public class XssFilter extends OncePerRequestFilter {

  private final XssProperties properties;

  private final PathMatcher pathMatcher;

  private final XssCleaner xssCleaner;

  @Override
  protected void doFilterInternal(
    HttpServletRequest request,
    HttpServletResponse response,
    FilterChain filterChain
  ) throws ServletException, IOException {
    filterChain.doFilter(new XssRequestWrapper(request, xssCleaner), response);
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request)
  throws ServletException {
    if (!properties.isEnable()) {
      return true;
    }
    String uri = request.getRequestURI();

    return properties.getExcludeUrls()
                     .stream()
                     .anyMatch(exu -> pathMatcher.match(exu, uri));
  }

  public static class XssRequestWrapper extends HttpServletRequestWrapper {

    private final XssCleaner xssCleaner;

    public XssRequestWrapper(HttpServletRequest request, XssCleaner xssCleaner) {
      super(request);
      this.xssCleaner = xssCleaner;
    }

    // ============================ parameter ============================
    @Override
    public Map<String, String[]> getParameterMap() {
      Map<String, String[]> map = new LinkedHashMap<>();
      Map<String, String[]> parameters = super.getParameterMap();
      for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
        String[] values = entry.getValue();
        for (int i = 0; i < values.length; i++) {
          values[i] = xssCleaner.clean(values[i]);
        }
        map.put(entry.getKey(), values);
      }
      return map;
    }

    @Override
    public String[] getParameterValues(String name) {
      String[] values = super.getParameterValues(name);
      if (values == null) {
        return null;
      }
      int count = values.length;
      String[] encodedValues = new String[count];
      for (int i = 0; i < count; i++) {
        encodedValues[i] = xssCleaner.clean(values[i]);
      }
      return encodedValues;
    }

    @Override
    public String getParameter(String name) {
      String value = super.getParameter(name);
      if (value == null) {
        return null;
      }
      return xssCleaner.clean(value);
    }

    // ============================ attribute ============================
    @Override
    public Object getAttribute(String name) {
      Object value = super.getAttribute(name);
      if (value instanceof String) {
        xssCleaner.clean((String) value);
      }
      return value;
    }

    // ============================ header ============================
    @Override
    public String getHeader(String name) {
      String value = super.getHeader(name);
      if (value == null) {
        return null;
      }
      return xssCleaner.clean(value);
    }

    // ============================ queryString ============================
    @Override
    public String getQueryString() {
      String value = super.getQueryString();
      if (value == null) {
        return null;
      }
      return xssCleaner.clean(value);
    }
  }
}
