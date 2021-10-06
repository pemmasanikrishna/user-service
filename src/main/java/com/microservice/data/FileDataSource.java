package com.microservice.data;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.models.User;

/**
 * Handles file data source
 */
public class FileDataSource implements DataSource {

	private List<User> users;
	private static final ObjectMapper mapper = new ObjectMapper();

	private static final Logger logger = Logger.getLogger(FileDataSource.class.getName());

	public FileDataSource(String fileName) throws IOException {

		InputStream fileStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);

			users = mapper.readValue(fileStream, new TypeReference<List<User>>() {
			});
			logger.log(Level.INFO, "data loaded from the userdata.json file");

		fileStream.close();

	}

	@Override
	public List<User> getUsers() {
		return users;
	}

}
