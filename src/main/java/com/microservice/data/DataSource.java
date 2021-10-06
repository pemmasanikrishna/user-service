package com.microservice.data;

import java.util.List;

import com.microservice.models.User;

public interface DataSource {

	List<User> getUsers();

}