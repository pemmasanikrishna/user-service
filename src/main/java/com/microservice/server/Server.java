package com.microservice.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.microservice.server.config.Config;
import com.microservice.server.config.Constants;
import com.microservice.server.exceptions.ServerException;
import com.microservice.server.http.ServerContext;
import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpServer;

/**
 * The Http server used for serving the api requests
 */
public class Server {

  private static final Logger logger = Logger.getLogger(Server.class.getName());

  private HttpServer server;

  private List<ServerContext> contexts = new ArrayList<>();

  /**
   * Adds context to the server
   * @param context The ServerContext to create route, map handler and filters
   * @return
   */
  public Server addContext(ServerContext context) {
    contexts.add(context);
    return this;

  }

  /**
   * Maps the contexts available and starts the server with the config
   */
  public void start() {


    try {
      server = HttpServer.create(new InetSocketAddress(Config.getPort()), 0);

      for (ServerContext context : contexts) {
        List<Filter> filters =
            server.createContext(context.getRoute(), context.getHandler()).getFilters();
        if (context.getFilters() != null) {
          filters.addAll(context.getFilters());
        }

      }

      server.start();
      logger.log(Level.INFO, "Server up and running on port {0}", new Object[]{Config.getPort()});

    } catch (IOException e) {

      logger.log(Level.SEVERE, e.getMessage());

      throw new ServerException(e.getMessage());
    }

  }

  public void stop(){
    server.stop(0);
  }

}
