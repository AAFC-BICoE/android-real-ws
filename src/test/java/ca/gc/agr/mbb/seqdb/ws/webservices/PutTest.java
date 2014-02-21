package ca.gc.agr.mbb.seqdb.ws.webservices;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ca.gc.agr.mbb.seqdb.ws.Nouns;
import ca.gc.agr.mbb.seqdb.ws.Nouns;
import ca.gc.agr.mbb.seqdb.ws.Payload;
import ca.gc.agr.mbb.seqdb.ws.payload.Container;
import ca.gc.agr.mbb.seqdb.ws.payload.Location;
import org.glassfish.jersey.client.ClientResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PutTest extends BaseTest{
    
    @Before
    public void setUp() throws Exception {
	super.setUp();
    }

    @After
    public void tearDown() throws Exception {
	super.tearDown();
    }


    ////////////////////////////////////////////////
    // CONTAINER
    @Test
    public void shouldCreateNewContainer(){
	Response response = putPayload(Nouns.CONTAINER);
	assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
    }

    @Test
    public void shouldPutNewContainerContentLocationInHeader(){
	Response response = putPayload(Nouns.CONTAINER);
	assertTrue(response.getHeaders().containsKey(WSConstants.CONTENT_LOCATION));
    }


    ////////////////////////////////////////////////
    // LOCATION
    @Test
    public void shouldCreateNewLocation(){
	Response response = putPayload(Nouns.LOCATION);
	assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
    }

    @Test
    public void shouldPutNewLocationContentLocationInHeader(){
	Response response = putPayload(Nouns.LOCATION);
	assertTrue(response.getHeaders().containsKey(WSConstants.CONTENT_LOCATION));
    }

    @Test
    public void putShouldFailIfIdIsSet(){
	Response response = putPayload(Nouns.LOCATION, true);
	assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
    }



}
