package com.microservice.server.exceptions;

public class ServerException extends RuntimeException {
	

	private static final long serialVersionUID = -2959357039617535223L;

	public ServerException(String message) {
		super(message);
	}

}
