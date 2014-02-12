package ca.gc.agr.mbb.seqdb.ws.webservices;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import ca.gc.agr.mbb.seqdb.ws.http.Main;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import javax.ws.rs.client.WebTarget;

import ca.gc.agr.mbb.seqdb.ws.payload.Container;
import ca.gc.agr.mbb.seqdb.ws.Nouns;


public class DeleteTest {
    private Long id = null;
    private Long expectedId = null;

    public DeleteTest(final Long id, final Long expectedId){
	this.id = id;
	//this.expectedId = expectedId;
    }

    private HttpServer server;
    private WebTarget target;

    @Before
    public void setUp() throws Exception {
        // start the server
        server = Main.startServer();
        // create the client
        Client c = ClientBuilder.newClient();
        target = c.target(Main.BASE_URI);
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }
    

    

}

