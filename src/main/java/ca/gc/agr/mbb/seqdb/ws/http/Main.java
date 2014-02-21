package ca.gc.agr.mbb.seqdb.ws.http;

import java.util.logging.*;

import java.io.IOException;
import java.net.URI;
import javax.json.stream.JsonGenerator;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import ca.gc.agr.mbb.seqdb.ws.mockstate.MockState;

public class Main {
    private final Logger logger=Logger.getLogger(this.getClass().getPackage().getName());
    // Base URI the Grizzly HTTP server will listen on
    public static final String BASE_URI = "http://localhost:8080/";

    public static MockState mockState;

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * @return Grizzly HTTP server.
     */
    public HttpServer startServer() {
        // create a resource config that scans for JAX-RS resources and providers
        // in com.example package
        final ResourceConfig rc = new ResourceConfig().packages("ca.gc.agr.mbb.seqdb.ws");
	rc.property(JsonGenerator.PRETTY_PRINTING, true);

	mockState = new MockState();
	logger.log(Level.INFO, "Some message");
        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    /**
     * Main method.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
	Main main = new Main();
        final HttpServer server = main.startServer();
        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));
        System.in.read();
        server.stop();
    }

}

