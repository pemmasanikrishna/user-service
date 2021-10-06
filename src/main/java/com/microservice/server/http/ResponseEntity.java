package com.microservice.server.http;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ResponseEntity {

	private byte[] response;
	private int status;
	private HttpMethod httpMethod;
	

	private static final ObjectMapper objectMapper = new ObjectMapper();

	public ResponseEntity(Object response, HttpStatus status, String httpMethod) {
		try {
			setResponse(response);
			this.status = status.getCode();
			this.httpMethod = HttpMethod.valueOf(httpMethod);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public byte[] getResponse() throws IOException {
		return   response;
	}

	public void setResponse(Object object) throws IOException {
		if (object == null)
			this.response = new byte[0];
		else
			this.response = objectMapper.writeValueAsBytes(object);
	}

	public long getResponseLength() {
		return response.length > 0 && !HttpMethod.HEAD.equals(httpMethod) ? response.length : -1;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status.getCode();

	}

}
