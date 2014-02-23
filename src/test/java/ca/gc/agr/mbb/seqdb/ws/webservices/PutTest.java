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
	Response response = sendPayload(Nouns.CONTAINER, false, METHOD.POST);
	assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
    }

    @Test
    public void shouldPutNewContainerContentLocationInHeader(){
	Response response = sendPayload(Nouns.CONTAINER, false, METHOD.POST);
	assertTrue(response.getHeaders().containsKey(CONTENT_LOCATION));
    }

    @Test
    public void putContainerShouldFailIfIdIsSet(){
	Response response = sendPayload(Nouns.CONTAINER, true, METHOD.POST);
	assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
    }


    ////////////////////////////////////////////////
    // LOCATION
    //  PUT
    @Test
    public void shouldCreateNewLocation(){
	Response response = sendPayload(Nouns.LOCATION, false, METHOD.POST);
 	assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
    }

    @Test
    public void shouldPutNewLocationContentLocationInHeader(){
	Response response = sendPayload(Nouns.LOCATION, false, METHOD.POST);
	assertTrue(response.getHeaders().containsKey(WSConstants.CONTENT_LOCATION));
    }

    @Test
    public void putLocationShouldFailIfIdIsSet(){
	Response response = sendPayload(Nouns.LOCATION, true, METHOD.POST);
	assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
    }


    ////////////////////////////////////////////////
    // MIXED_SPECIMEN
    @Test
    public void shouldCreateNewMixedSpecimen(){
	Response response = sendPayload(Nouns.MIXED_SPECIMEN, false, METHOD.POST);
	assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
    }

    @Test
    public void shouldPutNewMixedSpecimenContentLocationInHeader(){
	Response response = sendPayload(Nouns.MIXED_SPECIMEN, false, METHOD.POST);
	assertTrue(response.getHeaders().containsKey(WSConstants.CONTENT_LOCATION));
    }

    @Test
    public void putMixedSpecimenShouldFailIfIdIsSet(){
	Response response = sendPayload(Nouns.MIXED_SPECIMEN, true, METHOD.POST);
	assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
    }



}
