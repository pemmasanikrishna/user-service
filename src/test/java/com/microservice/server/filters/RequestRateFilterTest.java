package com.microservice.server.filters;

import static org.junit.jupiter.api.Assertions.*;

import com.microservice.DummyExchange;
import com.microservice.server.config.Constants;
import com.microservice.server.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.github.bucket4j.Bucket;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

class RequestRateFilterTest {

  RequestRateFilter requestRateFilter;

  @BeforeEach
  void setUp(){
    Map<String, Bucket> bucketStore = new HashMap<>();
    requestRateFilter = new RequestRateFilter(bucketStore);
  }

  @Test
  void shouldExecute() {
    URI uri = URI.create(Constants.API_BASE_PATH+"/users");
    DummyExchange exchange = new DummyExchange("GET",uri, HttpStatus.OK.getCode());

    assertTrue(requestRateFilter.canExecute(exchange));

  }


  @Test
  void shouldNotExecute() {
    URI uri = URI.create(Constants.API_BASE_PATH+"/users");
    DummyExchange exchange = new DummyExchange("GET",uri, HttpStatus.OK.getCode());

    for (int i = 0; i < 10 ; i++) {
      requestRateFilter.canExecute(exchange);
    }

    assertFalse(requestRateFilter.canExecute(exchange));

  }

}