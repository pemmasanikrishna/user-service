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

class EtagFilterTest {

  EtagFilter etagFilter;

  @BeforeEach
  void setUp() {
    etagFilter = new EtagFilter();
  }

  @Test
  void processETagIfNoneMatch() throws IOException {
    URI uri = URI.create(Constants.API_BASE_PATH+"/users");
    DummyExchange exchange = new DummyExchange("GET",uri, HttpStatus.OK.getCode());

    exchange.setAttribute("response", new ResponseEntity(null,HttpStatus.OK,"GET"));
    exchange.getRequestHeaders().add("If-None-Match","");

    etagFilter.processETag(exchange);

    ResponseEntity responseEntity = (ResponseEntity) exchange.getAttribute("response");

    assertEquals(HttpStatus.OK.getCode(), responseEntity.getStatus());
    assertEquals("1",exchange.getResponseHeaders().get("ETag").get(0));
  }

  @Test
  void processETagIfMatch() throws IOException {
    URI uri = URI.create(Constants.API_BASE_PATH+"/users");
    DummyExchange exchange = new DummyExchange("GET",uri, HttpStatus.OK.getCode());

    exchange.setAttribute("response", new ResponseEntity(null,HttpStatus.OK,"GET"));
    exchange.getRequestHeaders().add("If-Match","");

    etagFilter.processETag(exchange);


    ResponseEntity responseEntity = (ResponseEntity) exchange.getAttribute("response");

    assertEquals(HttpStatus.NotModified.getCode(), responseEntity.getStatus());
    assertEquals("1",exchange.getResponseHeaders().get("ETag").get(0));
  }


  @Test
  void processETagIfMatchMatch() throws IOException {
    URI uri = URI.create(Constants.API_BASE_PATH+"/users");
    DummyExchange exchange = new DummyExchange("GET",uri, HttpStatus.OK.getCode());

    exchange.setAttribute("response", new ResponseEntity(null,HttpStatus.OK,"GET"));
    exchange.getRequestHeaders().add("If-Match","1");

    etagFilter.processETag(exchange);

    ResponseEntity responseEntity = (ResponseEntity) exchange.getAttribute("response");

    assertEquals(-1,responseEntity.getResponseLength());
    assertEquals(HttpStatus.OK.getCode(),responseEntity.getStatus());
    assertEquals("1",exchange.getResponseHeaders().get("ETag").get(0));
  }



  @Test
  void processETagIfNoneMatchMatch() throws IOException {
    URI uri = URI.create(Constants.API_BASE_PATH+"/users");
    DummyExchange exchange = new DummyExchange("GET",uri, HttpStatus.OK.getCode());

    exchange.setAttribute("response", new ResponseEntity(null,HttpStatus.OK,"GET"));
    exchange.getRequestHeaders().add("If-None-Match","1");

    etagFilter.processETag(exchange);

    ResponseEntity responseEntity = (ResponseEntity) exchange.getAttribute("response");

    assertEquals(-1,responseEntity.getResponseLength());
    assertEquals(HttpStatus.NotModified.getCode(),responseEntity.getStatus());
    assertEquals("1",exchange.getResponseHeaders().get("ETag").get(0));
  }


}