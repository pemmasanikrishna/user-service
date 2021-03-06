package com.microservice.server.http;

public enum HttpStatus {

		
		OK(200, "OK", "The request sent by the client was successful."),
		Created(201, "Created", "The request was successful and the resource has been created."),
		Accepted(202, "Accepted", "The request has been accepted but has not yet finished processing."),
		NonAuthoritativeInformation(203, "Non-Authoritative Information", "The returned meta-information in the entity header is not the definitative set of information, it might be a local copy or contain local alterations."),
		NoContent(204, "No Content", "The request was successful but not require the return of an entity body."),
		ResetContent(205, "Reset Content", "The request was successful and the user agent should reset the view that sent the request."),
		PartialContent(206, "Partial Content", "The partial request was successful."),

		
		NotModified(304, "Not Modified", "The resource has not been modified since the last request."),
		
		BadRequest(400, "Bad Request", "The request could not be understood by the server."),
		Unauthorized(401, "Unauthorized", "The request requires authorization."),
		PaymentRequired(402, "Payment Required", "Reserved for future use."),
		Forbidden(403, "Forbidden", "Whilst the server did understand the request, the server is refusing to complete it. This is not an authorization problem."),
		NotFound(404, "Not Found", "The requested resource was not found."),
		MethodNotAllowed(405, "Method Not Allowed", "The supplied method was not allowed on the given resource."),
		NotAcceptable(406, "Not Acceptable", "The resource is not able to return a response that is suitable for the characteristics required by the accept headers of the request."),
		ProxyAuthenticationRequired(407, "Proxy Authentication Required", "The client must authenticate themselves with the proxy."),
		RequestTimeout(408, "Request Timeout", "The client did not supply a request in the period required by the server."),
		Conflict(409, "Conflict", "The request could not be completed as the resource is in a conflicted state."),
		Gone(410, "Gone", "The requested resource is no longer available on the server and no redirect address is available."),
		LengthRequired(411, "Length Required", "The server will not accept the request without a Content-Length field."),
		PreconditionFailed(412, "Precondition Failed", "The supplied precondition evaluated to false on the server."),
		RequestEntityTooLarge(413, "Request Entity Too Large", "The request was unsuccessful because the request entity was larger than the server would allow"),
		RequestedURITooLong(414, "Request URI Too Long", "The request was unsuccessful because the requested URI is longer than the server is willing to process (that's what she said)."),
		UnsupportedMediaType(415, "Unsupported Media Type", "The request was unsuccessful because the request was for an unsupported format."),
		RequestRangeNotSatisfiable(416, "Request Range Not Satisfiable", "The range of the resource does not overlap with the values specified in the requests Range header field and not alternative If-Range field was supplied."),
		ExpectationFailed(417, "Expectation Failed", "The expectation supplied in the Expectation header field could not be met by the server."),
		ImATeapot(418, "I'm a teapot", "I'm a teapot"),
		TooManyRequests(429,"Too many requests","Too many requests"),

		InternalServerError(500, "Internal Server Error", "The request was unsuccessful because the server encountered an unexpected error."),
		NotImplemented(501, "Not Implemented", "The server does not support the request."),
		BadGateway(502, "Bad Gateway", "The server, whilst acting as a proxy, received an invalid response from the server that was fulfilling the request."),
		ServiceUnavailable(503, "Service Unavailable", "The request was unsuccessful as the server is either down or slash^H^H^H^H^Hdug^H^H^Hreddited."),
		GatewayTimeout(504, "Gateway Timeout", "The server, whilst acting as a proxy, did not receive a response from the upstream server in an acceptable time."),
		HttpVersionNotSupported(505, "HTTP Version Not Supported", "The server does not supported the HTTP protocol version specified in the request"),

		Unknown(400, "Unknown HTTP Status Code", "Unknown or unsupported HTTP status code");

		private final int code;
		private final String name;
		private final String description;


		private HttpStatus(int code, String name, String description) {
			this.code = code;
			this.name = name;
			this.description = description;
		}

		/**
		 * Returns the int status code this enum represents
		 * 
		 * @return the int status code this enum represents
		 */
		public final int getCode() {
			return code;
		}

		/**
		 * Returns the name of the HTTP status this enum represents
		 * 
		 * @return the name of the HTTP status this enum represents
		 */
		public final String getName() {
			return name;
		}

		/**
		 * Returns a description of the HTTP status this enum represents
		 * 
		 * @return a description of the HTTP status this enum represents
		 */
		public final String getDescription() {
			return description;
		}


		/**
		 * Returns the HttpStatus object with a code matching the supplied int
		 * 
		 * @param httpStatus
		 *            the httpStatus code
		 * @return the HttpStatus object with a code matching the supplied int
		 */
		public static HttpStatus getByCode(int httpStatus) {
			for (HttpStatus status : HttpStatus.values()) {
				if (status.getCode() == httpStatus) {
					return status;
				}
			}
			return Unknown;
		}

		/**
		 * Returns the HttpStatus with the Integer code that matches the supplied
		 * String. Returns HttpStatus.Unknown if the supplied String is not a valid
		 * Integer or is not in the list of available HTTP status codes.
		 * 
		 * @param httpStatus
		 *            the String containing the status code to match
		 * @return the HttpStatus for the supplied String
		 */
		public static HttpStatus getByCode(String httpStatus) {
			int statusCode;
			try {
				statusCode = Integer.parseInt(httpStatus);
			} catch (NumberFormatException e) {
				return Unknown;
			}
			return getByCode(statusCode);
		}
	
	
}
