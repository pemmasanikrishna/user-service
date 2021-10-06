package com.microservice.server.handlers;

import java.io.IOException;

import com.microservice.server.http.HttpStatus;
import com.microservice.server.http.ResponseEntity;
import com.microservice.services.UserService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

/**
 * Handles user related api requests
 */
public class UserHandler implements HttpHandler {

	private final UserService userService;

	public UserHandler(UserService userService) {
		this.userService = userService;
	}

	@Override
	public void handle(HttpExchange exchange) {

		String httpMethod = exchange.getRequestMethod();
		try {
			switch (httpMethod) {

			case "HEAD":
			case "GET":
				handleGetRequest(exchange);
				break;

			default:
				handleDefaultRequest(exchange);

			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void handleDefaultRequest(HttpExchange exchange) {

		ResponseEntity responseEntity = new ResponseEntity(null, HttpStatus.MethodNotAllowed, exchange.getRequestMethod());

		exchange.setAttribute("response", responseEntity);

	}


	private void handleGetRequest(HttpExchange exchange) throws IOException {

		String path = exchange.getRequestURI().getPath();

		Object object = null;
		HttpStatus status = HttpStatus.OK;
		
		if (path.endsWith("/users")){
			object = userService.getUsers();
			if (object == null) {
				status = HttpStatus.NotFound;
			}
		}else{
			
			String[] subPaths = path.split("/");
			if (subPaths.length != 5 ){
					status = HttpStatus.BadRequest;
			}else {
				String userId = subPaths[4];
				object = userService.getUser(userId);
				if (object == null) {
					status = HttpStatus.NotFound;

				}
			}
		}
		

		ResponseEntity responseEntity = new ResponseEntity(object, status, exchange.getRequestMethod());

		exchange.setAttribute("response", responseEntity);

	}
	

}
