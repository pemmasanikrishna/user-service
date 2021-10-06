package com.microservice.server.http;

import com.sun.net.httpserver.HttpHandler;

public interface  ServerHandler extends HttpHandler {

	public String getRoute() ;

}
