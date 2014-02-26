package ca.gc.agr.mbb.seqdb.ws.webservices;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ca.gc.agr.mbb.seqdb.ws.Nouns;
import ca.gc.agr.mbb.seqdb.ws.Payload;
import ca.gc.agr.mbb.seqdb.ws.payload.Container;
import org.glassfish.jersey.client.ClientResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ca.gc.agr.mbb.seqdb.ws.mockstate.MockState;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ContainerPutPostTestIT extends BaseTestIT{
    
    @Before
    public void setUp() throws Exception {
	super.setUp();
    }

    @After
    public void tearDown() throws Exception {
	super.tearDown();
    }


    ////////////////////////////////////////////////
    //  POST
    @Test
    public void postShouldCreateNewContainer(){
	Response response = sendPayload(Nouns.CONTAINER, METHOD.POST);
	assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
    }

    @Test
    public void postNewContainerShouldHaveContentLocationInHeader(){
	Response response = sendPayload(Nouns.CONTAINER, METHOD.POST);
	assertTrue(response.getHeaders().containsKey(CONTENT_LOCATION));
    }

    @Test
    public void postContainerShouldFailIfIdIsSet(){
	Response response = sendPayload(Nouns.CONTAINER, 42, METHOD.POST);
	assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
    }

    ////////////////////////////////////////////////
    //  PUT
    @Test
    public void putShouldUpdateExistingContainer(){
	long existingId = MockState.containerMap.get(MockState.containerMap.keySet().iterator().next()).id;
	Response response = sendPayload(Nouns.CONTAINER, existingId, METHOD.PUT);
	assertEquals(Response.Status.ACCEPTED.getStatusCode(), response.getStatus());
    }

    @Test
    public void putShouldFailUpdatingNonExistantContainer(){
	long badId = 9999999l;
	System.err.println("----------- putShouldFailUpdatingNonExistantContainer: " + badId);
	Response response = sendPayload(Nouns.CONTAINER, badId, METHOD.PUT);
	assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }





}
