package com.microservice;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpPrincipal;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.stream.MemoryCacheImageOutputStream;

public class DummyExchange extends HttpExchange {

  private String requestMethod;
  private URI requestUri;
  private int responseCode;
  private Long responseLength = -1L;

  private Map<String, Object> attributes = new HashMap<>();
  private Headers responseHeaders = new Headers();
  private Headers requestHeaders = new Headers();

  public DummyExchange(String requestMethod, URI requestUri, int responseCode){
    this.requestMethod = requestMethod;
    this.requestUri = requestUri;
    this.responseCode = responseCode;
  }

  @Override
  public Headers getRequestHeaders() {

    return requestHeaders;
  }

  @Override
  public Headers getResponseHeaders() {

    return responseHeaders;
  }

  @Override
  public URI getRequestURI() {
    return requestUri;
  }

  @Override
  public String getRequestMethod() {
    return requestMethod;
  }

  @Override
  public HttpContext getHttpContext() {
    return null;
  }

  @Override
  public void close() {

  }

  @Override
  public InputStream getRequestBody() {
    return null;
  }

  @Override
  public OutputStream getResponseBody() {
    return new ByteArrayOutputStream();
  }

  @Override
  public void sendResponseHeaders(int rCode, long responseLength) throws IOException {
    this.responseCode = rCode;
    this.responseLength = responseLength;
  }

  @Override
  public InetSocketAddress getRemoteAddress() {
    return new InetSocketAddress(54321);
  }

  @Override
  public int getResponseCode() {
    return responseCode;
  }

  @Override
  public InetSocketAddress getLocalAddress() {
    return null;
  }

  @Override
  public String getProtocol() {
    return null;
  }

  @Override
  public Object getAttribute(String name) {
    return attributes.get(name);
  }

  @Override
  public void setAttribute(String name, Object value) {
    attributes.put(name,value);

  }

  @Override
  public void setStreams(InputStream i, OutputStream o) {

  }

  @Override
  public HttpPrincipal getPrincipal() {
    return null;
  }

  public Long getResponseLength() {
    return responseLength;
  }
}
