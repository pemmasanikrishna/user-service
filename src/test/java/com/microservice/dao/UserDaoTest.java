package com.microservice.dao;

import static org.junit.jupiter.api.Assertions.*;

import com.microservice.data.FileDataSource;
import com.microservice.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class UserDaoTest {

  UserDao userDao ;

  @BeforeEach
  void setUp() throws IOException {
    FileDataSource dataSource = new FileDataSource("userdata.json");
    userDao = new UserDao(dataSource);
  }

  @Test
  void testGetUsers() {
    assertNotNull( userDao.getUsers());
    assertTrue(userDao.getUsers().size() > 0);
  }

  @Test
  void getUserWithValidUserId() {
    User user = userDao.getUser("144bf891-f161-4c9a-8d83-38a275e088a5");
    assertNotNull(user);
    assertEquals("144bf891-f161-4c9a-8d83-38a275e088a5" ,user.getId());
  }

  @Test
  void getUserWithInvalidUserId(){
    User user = userDao.getUser("144bf891-f161-4c9a-8d83-38a275e088a");
    assertNull(user);
  }
}