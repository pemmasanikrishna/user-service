package com.microservice.dao;

import java.util.List;

import com.microservice.data.DataSource;
import com.microservice.models.User;

public class UserDao {

	private final DataSource dataSource;

	public UserDao(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public List<User> getUsers() {

		return dataSource.getUsers();

	}

	/**
	 * Finds user with the give id and returns null if found none
	 * @param id id of the user
	 * @return User if the user is found, else null
	 */
	public User getUser(String id) {
		for (User user : getUsers()) {
			if (user.getId().equals(id))
				return user;
		}

		return null;
	}

}
