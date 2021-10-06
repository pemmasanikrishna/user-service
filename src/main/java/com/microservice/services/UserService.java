package com.microservice.services;

import java.util.List;

import com.microservice.dao.UserDao;
import com.microservice.models.User;

public class UserService {
	
	private final UserDao userDao ;
	
	public UserService(UserDao userDao) {
		this.userDao = userDao;
	}

	public List<User> getUsers(){
		return userDao.getUsers();
	}
	
	public User getUser(String id) {
		return userDao.getUser(id);
	}

}
