package com.microservice.server.filters;

import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.microservice.server.http.ResponseEntity;
import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;

/**
 * Filter to handle response to the client
 */
public class DefaultFilter extends Filter {

	private static final Logger logger = Logger.getLogger(DefaultFilter.class.getName());

	@Override
	public String description() {
		return "filter to handle the response to the client";
	}

	@Override
	public void doFilter(HttpExchange exchange, Chain chain) throws IOException {

		String clientIP = exchange.getRemoteAddress().getAddress().getHostAddress();

		logger.log(Level.INFO,"{0} {1} {2}", new Object[]{exchange.getRequestMethod(),exchange.getRequestURI().getPath(),clientIP});

		chain.doFilter(exchange);

		processResponse(exchange);

	}

	public void processResponse(HttpExchange exchange) throws IOException {

		ResponseEntity responseEntity = (ResponseEntity) exchange.getAttribute("response");

		exchange.sendResponseHeaders(responseEntity.getStatus(), responseEntity.getResponseLength());

		if (responseEntity.getResponseLength() > 0) {
			OutputStream response = exchange.getResponseBody();
			response.write(responseEntity.getResponse());
			response.flush();
		}

		exchange.close();
	}

}
