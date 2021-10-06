package com.microservice.server.config;

public final class Config {

	private static final String port = System.getenv().getOrDefault(Constants.SERVER_PORT, "8080");

	public static int getPort() {
		return Integer.valueOf(port);
	}

	private static final String maxReuestsPerMinute = System.getenv().getOrDefault(Constants.MAX_REQUESTS_PER_MINUTE,
			"10");

	public static int getMaxreuestsperminute() {
		return Integer.valueOf(maxReuestsPerMinute);
	}

}
