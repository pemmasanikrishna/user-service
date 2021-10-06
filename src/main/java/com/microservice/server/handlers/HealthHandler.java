package com.microservice.server.handlers;

import com.microservice.server.http.HttpStatus;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

/**
 * Handles health  api requests
 */
public class HealthHandler implements HttpHandler {
  @Override
  public void handle(HttpExchange exchange) throws IOException {
    exchange.sendResponseHeaders(HttpStatus.OK.getCode(),-1);
  }
}
