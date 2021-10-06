package com.microservice.server.handlers;

import static org.junit.jupiter.api.Assertions.*;

import com.microservice.DummyExchange;
import com.microservice.server.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;

class HealthHandlerTest {

  HealthHandler healthHandler = new HealthHandler();

  @BeforeEach
  void setUp() {
  }

  @Test
  void handle() throws IOException {
    URI uri = URI.create("/health");
    DummyExchange exchange = new DummyExchange("GET",uri, HttpStatus.OK.getCode());
    healthHandler.handle(exchange);

    assertEquals(HttpStatus.OK.getCode(),exchange.getResponseCode());
    assertEquals(-1,exchange.getResponseLength());

  }
}