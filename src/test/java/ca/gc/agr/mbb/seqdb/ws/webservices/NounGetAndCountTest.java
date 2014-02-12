package ca.gc.agr.mbb.seqdb.ws.webservices;

import java.lang.StackTraceElement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ca.gc.agr.mbb.seqdb.ws.Nouns;
import ca.gc.agr.mbb.seqdb.ws.http.Main;
import ca.gc.agr.mbb.seqdb.ws.webservices.WSConstants;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class) 
public class NounGetAndCountTest {
    
    private String noun = null;
    private String expected = null;

    public NounGetAndCountTest(final String noun, final String expected){
	this.noun = noun;
	this.expected = expected;
    }


    @Parameters
    public static Collection<String[]> data() {
	List<String[]> params =
	    new ArrayList<String[]>();
	for(String noun: Nouns.NOUNS){
	    params.add(new String[] {noun, "{\"name\":\"my-name\",\"age\":43}"});
	}
	System.err.println("Params size=" + params.size());
	return params;
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
    

    @Test
    public void testListWebServices() {
	String path = WSConstants.BASEPATH;
	System.err.println("path=[" +  path +  "]");
        Response response = target.path(path).request().accept(MediaType.APPLICATION_JSON).get();
        //assertEquals(expected, responseMsg);
	assertEquals(200, response.getStatus());
    }



    @Test
    public void shouldListAllNounItems() {
	String path = WSConstants.BASEPATH + noun;
	System.err.println("path=[" + path + "]");
	Response response = target.path(path).request().accept(MediaType.APPLICATION_JSON).get();
	assertEquals(200, response.getStatus());
    }


    @Test
    public void shouldCountNoun() {
	String path = WSConstants.BASEPATH + noun + WSConstants.COUNT_PATH;
	System.err.println("count path=[" + target.path(path).getUri() + "]");
	Response response = target.path(path).request().accept(MediaType.APPLICATION_JSON).get();
	assertEquals(200, response.getStatus());
    }

    @Test
    public void shouldFailWithBadId() {
	String path = WSConstants.BASEPATH + noun + "/zzzzzzz9";
	System.err.println("path=[" + path + "]");
	Response response = target.path(path).request().accept(MediaType.APPLICATION_JSON).get();
	assertEquals(404, response.getStatus());
    }


    @Test//(expected=javax.ws.rs.NotAcceptableException.class)
    public void shouldNotSupportMediaTypeXML(){// throws javax.ws.rs.NotAcceptableException {
	Response response = null;
	String path = WSConstants.BASEPATH;
	System.err.println("count path=[" + target.path(path).getUri() + "]");
	response = target.path(path).request().accept(MediaType.APPLICATION_XML).get();
	// This should be 406!!!!!!!
	assertEquals(406, response.getStatus());
    }

}
