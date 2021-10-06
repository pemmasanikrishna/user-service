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
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpPrincipal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;

class UsersHandlerTest {

  UsersHandler usersHandler;


  @BeforeEach
  void setUp() throws IOException {
    DataSource dataSource = new FileDataSource("userdata.json");
    UserDao userDao = new UserDao(dataSource);
    UserService userService = new UserService(userDao);
    usersHandler = new UsersHandler(userService);
  }

  @Test
  void usersGetHandle() {
    URI uri = URI.create(Constants.API_BASE_PATH+"/users");
    HttpExchange exchange = new DummyExchange("GET",uri, HttpStatus.OK.getCode());
    usersHandler.handle(exchange);

    ResponseEntity responseEntity = (ResponseEntity) exchange.getAttribute("response");

    assertTrue(responseEntity.getResponseLength() > 0);
    assertTrue(responseEntity.getStatus() == HttpStatus.OK.getCode());


  }


  @Test
  void usersHeadHandle() {
    URI uri = URI.create(Constants.API_BASE_PATH+"/users");
    HttpExchange exchange = new DummyExchange("HEAD",uri, HttpStatus.OK.getCode());
    usersHandler.handle(exchange);

    ResponseEntity responseEntity = (ResponseEntity) exchange.getAttribute("response");

    assertTrue(responseEntity.getResponseLength() == -1);
    assertTrue(responseEntity.getStatus() == HttpStatus.OK.getCode());

  }


  @Test
  void usersDefaultHandle() {
    URI uri = URI.create(Constants.API_BASE_PATH+"/users");
    DummyExchange exchange = new DummyExchange("PUT",uri, HttpStatus.OK.getCode());
    usersHandler.handle(exchange);

    ResponseEntity responseEntity = (ResponseEntity) exchange.getAttribute("response");


    assertEquals( -1,responseEntity.getResponseLength());
    assertEquals(HttpStatus.MethodNotAllowed.getCode(),responseEntity.getStatus());

  }

}