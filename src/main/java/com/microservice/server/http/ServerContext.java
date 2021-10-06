package com.microservice.server.http;

import java.util.List;

import com.microservice.server.handlers.HealthHandler;
import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpHandler;

public class ServerContext {

	private HttpHandler handler;
	private String route;

	private List<Filter> filters;

	public ServerContext(HttpHandler handler, String route, List<Filter> filters) {
		this.handler = handler;
		this.route = route;
		this.filters = filters;
	}

	public ServerContext(HttpHandler handler, String route) {
		this.handler = handler;
		this.route = route;
	}

	public HttpHandler getHandler() {
		return handler;
	}

	public String getRoute() {
		return route;
	}

	public List<Filter> getFilters() {
		return filters;
	}

}
