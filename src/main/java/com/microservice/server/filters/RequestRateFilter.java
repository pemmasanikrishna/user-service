package com.microservice.server.filters;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.microservice.server.config.Config;
import com.microservice.server.http.HttpStatus;
import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;

/**
 * Filter to throttle requests based on client ip
 */
public class RequestRateFilter extends Filter {

	private static final Logger logger = Logger.getLogger(RequestRateFilter.class.getName());

	private final Map<String,Bucket> bucketStore ;

	private static  final Bandwidth limit = Bandwidth.classic(Config.getMaxreuestsperminute(),
			Refill.greedy(Config.getMaxreuestsperminute(), Duration.ofMinutes(1)));

	public RequestRateFilter(Map<String,Bucket> bucketStore) {
		this.bucketStore = bucketStore;
	}

	@Override
	public String description() {
		return "Client IP based rate limiter using token bucket algorithm";
	}

	@Override
	public void doFilter(HttpExchange exchange, Chain chain) throws IOException {
		if (canExecute(exchange)) {
			chain.doFilter(exchange);
		} else {
			exchange.sendResponseHeaders(HttpStatus.TooManyRequests.getCode(), -1);
		}
	}

	public boolean canExecute(HttpExchange exchange){

		String clientIP = exchange.getRemoteAddress().getAddress().getHostAddress();

		Bucket bucket = bucketStore.getOrDefault(clientIP,Bucket4j.builder().addLimit(limit).build());

		bucketStore.put(clientIP,bucket);
		return  bucket.tryConsume(1);
	}

}
