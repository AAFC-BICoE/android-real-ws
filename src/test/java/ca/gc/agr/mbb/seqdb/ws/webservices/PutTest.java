package ca.gc.agr.mbb.seqdb.ws.webservices;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.client.ClientResponse;

import ca.gc.agr.mbb.seqdb.ws.Nouns;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import javax.ws.rs.client.Entity;

public class PutTest extends BaseTest{
    
    @Before
    public void setUp() throws Exception {
	super.setUp();
    }

    @After
    public void tearDown() throws Exception {
	super.tearDown();
    }


    @Test
    public void shouldCreateNewContainer(){
	String path = WSConstants.BASEPATH + Nouns.CONTAINER;
	System.err.println("path=[" + path + "]");

	String containerJson = "{\"container\": {\"containerNumber\": \"43\",    \"containerType\": {        \"name\": \"t2\",        \"baseType\": \"t6\",        \"numberOfColumns\": 8,        \"numberOfRows\": 9,        \"numberOfWells\": 72,        \"id\": 42    }}}";

	Entity entity = Entity.entity(containerJson, MediaType.APPLICATION_JSON);
	Response response = target.path(path).request().accept(MediaType.APPLICATION_JSON).put(entity);
	System.err.println("Client response location: " + response.getLocation());
	System.err.println("Headers: " + response.getHeaders());
	assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
    }

    @Test
    public void shouldCreateNewLocation(){
	String path = WSConstants.BASEPATH + Nouns.LOCATION;
	System.err.println("path=[" + path + "]");

	String locationJson = "\"location\": {    \"id\": 1,    \"wellColumn\": 2,    \"wellRow\": \"A\",    \"containerId\": 52}";

	Entity entity = Entity.entity(locationJson, MediaType.APPLICATION_JSON);
	Response response = target.path(path).request().accept(MediaType.APPLICATION_JSON).put(entity);
	System.err.println("Client response location: " + response.getLocation());
	System.err.println("Headers: " + response.getHeaders());
	assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
    }


}
