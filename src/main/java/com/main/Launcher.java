package com.main;

import cloud.orbit.actors.Stage;
import com.api.PlayerEndPoint;
import com.providers.TaskProvider;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

/**
 * Created by andrewbates11 on 2017-05-31.
 */
public class Launcher {
    public static void main(String[] args) throws Exception {

        //Container container = new Container();
        Stage stage = new Stage();
        stage.setClusterName("andrew.test");
        stage.start().get();
        stage.bind();

        final ResourceConfig resourceConfig = new ResourceConfig();
        resourceConfig.register(TaskProvider.class);
        resourceConfig.register(PlayerEndPoint.class);

        ServletHolder jerseyServlet
                = new ServletHolder(new ServletContainer(resourceConfig));

        Server server = new Server(8080);
        ServletContextHandler context
                = new ServletContextHandler(server, "/");

        context.addServlet(jerseyServlet, "/*");

        try {
            server.start();
            server.join();
        }
        catch(Exception e){
            System.out.println("hey");
        }finally {
            server.destroy();
        }
    }
}
