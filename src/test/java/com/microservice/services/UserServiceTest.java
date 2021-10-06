package com.microservice.services;

import static org.junit.jupiter.api.Assertions.*;

import com.microservice.dao.UserDao;
import com.microservice.data.DataSource;
import com.microservice.data.FileDataSource;
import com.microservice.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class UserServiceTest {

  UserService userService;

  @BeforeEach
  void setUp() throws IOException {
    DataSource dataSource = new FileDataSource("userdata.json");
    UserDao userDao = new UserDao(dataSource);
    userService = new UserService(userDao);
  }

  @Test
  void testGetUsers() {
    assertNotNull( userService.getUsers());
    assertTrue(userService.getUsers().size() > 0);
  }

  @Test
  void getUserWithValidUserId() {
    User user = userService.getUser("144bf891-f161-4c9a-8d83-38a275e088a5");
    assertNotNull(user);
    assertEquals("144bf891-f161-4c9a-8d83-38a275e088a5" ,user.getId());
  }

  @Test
  void getUserWithInvalidUserId(){
    User user = userService.getUser("144bf891-f161-4c9a-8d83-38a275e088a");
    assertNull(user);
  }
}