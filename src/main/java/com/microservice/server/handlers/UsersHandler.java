package com.microservice.server.handlers;

import java.io.IOException;

import com.microservice.server.http.HttpStatus;
import com.microservice.server.http.ResponseEntity;
import com.microservice.services.UserService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

/**
 * Handles User related api requests
 */
public class UsersHandler implements HttpHandler {

	private final UserService userService;

	public UsersHandler(UserService userService) {
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

		ResponseEntity responseEntity = new ResponseEntity(null, HttpStatus.MethodNotAllowed,exchange.getRequestMethod());

		exchange.setAttribute("response", responseEntity);

	}

	private void handleGetRequest(HttpExchange exchange) throws IOException {

		Object object = userService.getUsers();

		HttpStatus status = HttpStatus.OK;

		if (object == null) {

			status = HttpStatus.NotFound;
		}

		ResponseEntity responseEntity = new ResponseEntity(object, status, exchange.getRequestMethod());

		exchange.setAttribute("response", responseEntity);

	}

}
