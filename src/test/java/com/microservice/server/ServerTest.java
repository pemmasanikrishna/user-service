package com.microservice.server;

import static org.junit.jupiter.api.Assertions.*;

import com.microservice.server.config.Constants;
import com.microservice.server.exceptions.ServerException;
import com.microservice.server.filters.DefaultFilter;
import com.microservice.server.filters.EtagFilter;
import com.microservice.server.filters.RequestRateFilter;
import com.microservice.server.handlers.HealthHandler;
import com.microservice.server.handlers.UserHandler;
import com.microservice.server.http.ServerContext;
import com.sun.net.httpserver.Filter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.github.bucket4j.Bucket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ServerTest {


  ServerContext healthContext;

  @BeforeEach
  void setUp() {
    List<Filter> filters = new ArrayList<>();
    filters.add(new DefaultFilter());
    filters.add(new EtagFilter());
    Map<String, Bucket> bucketStore = new HashMap<>();
    filters.add(new RequestRateFilter(bucketStore));

    HealthHandler healthHandler = new HealthHandler();
    String healthRoute =  "/user";

    healthContext = new ServerContext(healthHandler, healthRoute, filters);


  }


  @Test
  void addContext() {
    Server server = new Server();
   assertNotNull( server.addContext( healthContext));

  }

  @Test
  void start() {
    Server server = new Server();
    server.addContext( healthContext);
    server.start();
    server.stop();
  }

  @Test
  void serverAlreadyStartedException() {
    Server server = new Server();
    server.addContext( healthContext);
    server.start();
    assertThrows(ServerException.class, () -> server.start());
    server.stop();
  }
}