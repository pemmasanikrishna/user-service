package com.microservice.data;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class FileDataSourceTest {

  DataSource dataSource ;

  @BeforeEach
  void setUp() throws IOException {
    dataSource = new FileDataSource("userdata.json");
  }

  @AfterEach
  void tearDown() {
  }


  @Test
  void shouldLoadUserDataFromFile(){
    assertNotNull( dataSource.getUsers());
    assertTrue(dataSource.getUsers().size() > 0);
  }

  @Test
  void shouldThrowException() throws IOException {
    assertThrows(IOException.class, () ->new FileDataSource(""));
  }



}