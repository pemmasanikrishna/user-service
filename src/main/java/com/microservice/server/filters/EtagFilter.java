package com.microservice.server.filters;

import com.microservice.server.http.HttpStatus;
import com.microservice.server.http.ResponseEntity;
import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.List;
import java.util.zip.Adler32;
import java.util.zip.Checksum;

/**
 * Filter to handle Etag
 */
public class EtagFilter extends Filter {

	@Override
	public String description() {
		return "filter to handle ETag";
	}

	@Override
	public void doFilter(HttpExchange exchange, Chain chain) throws IOException {

		chain.doFilter(exchange);

		processETag(exchange);

	}

	public void processETag(HttpExchange exchange) throws IOException {

		Headers requestHeaders = exchange.getRequestHeaders();
		ResponseEntity responseEntity = (ResponseEntity) exchange.getAttribute("response");


		Headers responseHeaders = exchange.getResponseHeaders();


		Checksum checksum = new Adler32();

		checksum.update(responseEntity.getResponse(),0,responseEntity.getResponse().length);

		String eTagFromServer = String.valueOf(checksum.getValue());



		List<String> eTagFromBrowser = null;

		if (requestHeaders.containsKey("If-None-Match")) {
			eTagFromBrowser = requestHeaders.get("If-None-Match");

			for (String browserTag : eTagFromBrowser) {
				if (browserTag.equals(eTagFromServer)) {
					responseEntity.setResponse(null);
					responseEntity.setStatus(HttpStatus.NotModified);
					break;
				}
			}

		}

		else if(requestHeaders.containsKey("If-Match")){
			eTagFromBrowser = requestHeaders.get("If-Match");
			boolean matchFound = false;
			for (String browserTag : eTagFromBrowser) {
				if (browserTag.equals(eTagFromServer) || "*".equals(eTagFromBrowser)) {
					matchFound = true;
					break;
				}
			}

			if(!matchFound) {
				responseEntity.setResponse(null);
				responseEntity.setStatus(HttpStatus.NotModified);
			}

		}

		responseHeaders.add("ETag",eTagFromServer);
	}

}
