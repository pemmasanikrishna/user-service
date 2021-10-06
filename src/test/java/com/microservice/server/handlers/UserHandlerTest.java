package com.microservice.server.handlers;

import static org.junit.jupiter.api.Assertions.*;

import com.microservice.DummyExchange;
import com.microservice.dao.UserDao;
import com.microservice.data.DataSource;
import com.microservice.data.FileDataSource;
import com.microservice.server.config.Constants;
import com.microservice.server.http.HttpStatus;
import com.microservice.server.http.ResponseEntity;
import com.microservice.services.UserService;
import com.sun.net.httpserver.HttpExchange;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;

class UserHandlerTest {


  UserHandler userHandler;


  @BeforeEach
  void setUp() throws IOException {
    DataSource dataSource = new FileDataSource("userdata.json");
    UserDao userDao = new UserDao(dataSource);
    UserService userService = new UserService(userDao);
    userHandler = new UserHandler(userService);
  }

  @Test
  void userGetHandle() {
    URI uri = URI.create("/api/v1/users/144bf891-f161-4c9a-8d83-38a275e088a5");
    HttpExchange exchange = new DummyExchange("GET",uri, HttpStatus.OK.getCode());
    userHandler.handle(exchange);

    ResponseEntity responseEntity = (ResponseEntity) exchange.getAttribute("response");

    assertTrue(responseEntity.getResponseLength() > 0);
    assertTrue(responseEntity.getStatus() == HttpStatus.OK.getCode());


  }

  @Test
  void userNotFoundGetHandle() {
    URI uri = URI.create("/api/v1/users/144bf891-f161-4c9a-8d83-38a275e088a");
    HttpExchange exchange = new DummyExchange("GET",uri, HttpStatus.OK.getCode());
    userHandler.handle(exchange);

    ResponseEntity responseEntity = (ResponseEntity) exchange.getAttribute("response");

    assertTrue(responseEntity.getResponseLength() == -1);
    assertTrue(responseEntity.getStatus() == HttpStatus.NotFound.getCode());


  }


  @Test
  void userHeadHandle() {
    URI uri = URI.create("/api/v1/users/144bf891-f161-4c9a-8d83-38a275e088a5");
    HttpExchange exchange = new DummyExchange("HEAD",uri, HttpStatus.OK.getCode());
    userHandler.handle(exchange);

    ResponseEntity responseEntity = (ResponseEntity) exchange.getAttribute("response");

    assertTrue(responseEntity.getResponseLength() == -1);
    assertTrue(responseEntity.getStatus() == HttpStatus.OK.getCode());

  }


  @Test
  void userDefaultHandle() {
    URI uri = URI.create("/api/v1/users/144bf891-f161-4c9a-8d83-38a275e088a5");
    DummyExchange exchange = new DummyExchange("PUT",uri, HttpStatus.OK.getCode());
    userHandler.handle(exchange);

    ResponseEntity responseEntity = (ResponseEntity) exchange.getAttribute("response");


    assertEquals( -1,responseEntity.getResponseLength());
    assertEquals(HttpStatus.MethodNotAllowed.getCode(),responseEntity.getStatus());

  }


  @Test
  void usersGetHandle() {
    URI uri = URI.create(Constants.API_BASE_PATH+"/users");
    HttpExchange exchange = new DummyExchange("GET",uri, HttpStatus.OK.getCode());
    userHandler.handle(exchange);

    ResponseEntity responseEntity = (ResponseEntity) exchange.getAttribute("response");

    assertTrue(responseEntity.getResponseLength() > 0);
    assertTrue(responseEntity.getStatus() == HttpStatus.OK.getCode());


  }


  @Test
  void usersHeadHandle() {
    URI uri = URI.create(Constants.API_BASE_PATH+"/users");
    HttpExchange exchange = new DummyExchange("HEAD",uri, HttpStatus.OK.getCode());
    userHandler.handle(exchange);

    ResponseEntity responseEntity = (ResponseEntity) exchange.getAttribute("response");

    assertTrue(responseEntity.getResponseLength() == -1);
    assertTrue(responseEntity.getStatus() == HttpStatus.OK.getCode());

  }


  @Test
  void usersDefaultHandle() {
    URI uri = URI.create(Constants.API_BASE_PATH+"/users");
    DummyExchange exchange = new DummyExchange("PUT",uri, HttpStatus.OK.getCode());
    userHandler.handle(exchange);

    ResponseEntity responseEntity = (ResponseEntity) exchange.getAttribute("response");


    assertEquals( -1,responseEntity.getResponseLength());
    assertEquals(HttpStatus.MethodNotAllowed.getCode(),responseEntity.getStatus());

  }

  @Test
  void badRequestTest(){

    URI uri = URI.create(Constants.API_BASE_PATH+"/users/2131321/name/123");
    DummyExchange exchange = new DummyExchange("GET",uri, HttpStatus.OK.getCode());
    userHandler.handle(exchange);

    ResponseEntity responseEntity = (ResponseEntity) exchange.getAttribute("response");


    assertEquals( -1,responseEntity.getResponseLength());
    assertEquals(HttpStatus.BadRequest.getCode(),responseEntity.getStatus());

  }


}