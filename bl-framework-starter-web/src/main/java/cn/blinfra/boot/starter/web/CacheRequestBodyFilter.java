package cn.blinfra.boot.starter.web;

import cn.blinfra.boot.common.util.ServletUtils;
import cn.hutool.extra.servlet.ServletUtil;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ReadListener;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class CacheRequestBodyFilter extends OncePerRequestFilter {
  @Override
  protected void doFilterInternal(
    HttpServletRequest request,
    HttpServletResponse response,
    FilterChain filterChain
  ) throws ServletException, IOException {
    /*实现body的可重复读取*/
    filterChain.doFilter(new CacheRequestBodyWrapper(request), response);
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request)
  throws ServletException {
    return !ServletUtils.isJsonRequest(request);
  }


  /**
   * 将body缓存
   */
  public static class CacheRequestBodyWrapper extends HttpServletRequestWrapper {
    private final byte[] body;

    public CacheRequestBodyWrapper(HttpServletRequest request) {
      super(request);
      body = ServletUtil.getBodyBytes(request);
    }

    @Override
    public BufferedReader getReader() throws IOException {
      return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
      return new AttachedBodyServletInputStream(body);
    }
  }

  public static class AttachedBodyServletInputStream extends ServletInputStream {
    private final byte[] attachedBody;
    private final ByteArrayInputStream inputStream;

    public AttachedBodyServletInputStream(byte[] attachedBody) {
      this.attachedBody = attachedBody;
      this.inputStream = new ByteArrayInputStream(attachedBody);
    }

    @Override
    public boolean isFinished() {
      return false;
    }

    @Override
    public boolean isReady() {
      return false;
    }

    @Override
    public void setReadListener(ReadListener readListener) {}

    @Override
    public int read() throws IOException {
      return inputStream.read();
    }

    @Override
    public int available() throws IOException {
      return this.attachedBody.length;
    }
  }
}
