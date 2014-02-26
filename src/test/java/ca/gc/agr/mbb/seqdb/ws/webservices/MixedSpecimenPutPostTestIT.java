package ca.gc.agr.mbb.seqdb.ws.webservices;

import ca.gc.agr.mbb.seqdb.ws.mockstate.MockState;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ca.gc.agr.mbb.seqdb.ws.Nouns;
import ca.gc.agr.mbb.seqdb.ws.Payload;
import org.glassfish.jersey.client.ClientResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MixedSpecimenPutPostTestIT extends BaseTestIT{
    
    @Before
    public void setUp() throws Exception {
	super.setUp();
    }

    @After
    public void tearDown() throws Exception {
	super.tearDown();
    }



    // POST
    @Test
    public void postShouldCreateNewMixedSpecimen(){
	Response response = sendPayload(Nouns.MIXED_SPECIMEN, METHOD.POST);
	assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
    }

    @Test
    public void shouldPostNewMixedSpecimenContentLocationInHeader(){
	Response response = sendPayload(Nouns.MIXED_SPECIMEN, METHOD.POST);
	assertTrue(response.getHeaders().containsKey(WSConstants.CONTENT_LOCATION));
    }

    @Test
    public void postMixedSpecimenShouldFailIfIdIsSet(){
	long existingId = MockState.containerMap.get(MockState.containerMap.keySet().iterator().next()).id;
	Response response = sendPayload(Nouns.MIXED_SPECIMEN, existingId, METHOD.POST);
	assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
    }


    // PUT
    @Test
    public void putShouldFailUpdatingNonExistantMixedSpecimen(){
	long badId = 9999999l;
	Response response = sendPayload(Nouns.MIXED_SPECIMEN, badId, METHOD.PUT);
	assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }


}
