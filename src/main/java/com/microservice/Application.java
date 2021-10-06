package com.microservice;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.microservice.dao.UserDao;
import com.microservice.data.DataSource;
import com.microservice.data.FileDataSource;
import com.microservice.server.filters.DefaultFilter;
import com.microservice.server.filters.EtagFilter;
import com.microservice.server.filters.RequestRateFilter;
import com.microservice.server.Server;
import com.microservice.server.config.Config;
import com.microservice.server.config.Constants;
import com.microservice.server.handlers.HealthHandler;
import com.microservice.server.handlers.UserHandler;
import com.microservice.server.handlers.UsersHandler;
import com.microservice.server.http.ServerContext;
import com.microservice.services.UserService;
import com.sun.net.httpserver.Filter;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;

/**
 * Starting point of the application
 */
public class Application {

	public static void main(String[] args) throws IOException {

		Server server = new Server();

		DataSource dataSource = new FileDataSource("userdata.json");
		UserDao userDao = new UserDao(dataSource);
		UserService userService = new UserService(userDao);


		List<Filter> filters = new ArrayList<>();
		filters.add(new DefaultFilter());
		filters.add(new EtagFilter());
		Map<String, Bucket> bucketStore = new HashMap<>();
		filters.add(new RequestRateFilter(bucketStore));


		UserHandler usersHandler = new UserHandler(userService);
		String usersRoute = Constants.API_BASE_PATH + "/users";
		ServerContext usersContext = new ServerContext(usersHandler, usersRoute, filters);

		HealthHandler healthHandler = new HealthHandler();
		String healthRoute = "/health";
		ServerContext healthContext = new ServerContext(healthHandler,healthRoute);

		server.addContext(usersContext).addContext(healthContext).start();

	}

}
