package com.microservice.server.filters;

import static org.junit.jupiter.api.Assertions.*;

import com.microservice.DummyExchange;
import com.microservice.server.config.Constants;
import com.microservice.server.http.HttpStatus;
import com.microservice.server.http.ResponseEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;

class DefaultFilterTest {

  DefaultFilter defaultFilter;

  @BeforeEach
  void setUp() {
    defaultFilter = new DefaultFilter();
  }

  @Test
  void processResponseWithResponseBody() throws IOException {
    URI uri = URI.create(Constants.API_BASE_PATH+"/users");
    DummyExchange exchange = new DummyExchange("GET",uri, HttpStatus.OK.getCode());

    ResponseEntity responseEntity = new ResponseEntity("response",HttpStatus.OK,"GET");
    exchange.setAttribute("response",responseEntity);

    defaultFilter.processResponse(exchange);

    assertEquals(200, exchange.getResponseCode());
    assertEquals(10,exchange.getResponseLength());

  }


  @Test
  void processResponseWithOutResponseBody() throws IOException {
    URI uri = URI.create(Constants.API_BASE_PATH+"/users");
    DummyExchange exchange = new DummyExchange("GET",uri, HttpStatus.OK.getCode());

    ResponseEntity responseEntity = new ResponseEntity(null,HttpStatus.OK,"GET");
    exchange.setAttribute("response",responseEntity);

    defaultFilter.processResponse(exchange);

    assertEquals(200, exchange.getResponseCode());
    assertEquals(-1,exchange.getResponseLength());

  }

}