package ca.gc.agr.mbb.seqdb.ws.webservices;

import org.glassfish.grizzly.http.server.HttpServer;
import javax.ws.rs.client.WebTarget;
import ca.gc.agr.mbb.seqdb.ws.http.Main;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

abstract public class BaseTest{
    protected HttpServer server;
    protected WebTarget target;


    public void setUp() throws Exception {
        // start the server
        server = Main.startServer();
        // create the client
        Client c = ClientBuilder.newClient();
        target = c.target(Main.BASE_URI);
    }

    public void tearDown() throws Exception {
        server.stop();
    }

}