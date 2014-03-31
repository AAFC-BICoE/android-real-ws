package ca.gc.agr.mbb.seqdb.ws.webservices;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import ca.gc.agr.mbb.seqdb.ws.mockstate.MockState;
import ca.gc.agr.mbb.seqdb.ws.Nouns;
import ca.gc.agr.mbb.seqdb.ws.Payload;
import ca.gc.agr.mbb.seqdb.ws.payload.Location;
import ca.gc.agr.mbb.seqdb.ws.payload.Container;
import org.glassfish.jersey.client.ClientResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LocationPutPostTestIT extends BaseTestIT{
    Random random = new Random();
    @Before
    public void setUp() throws Exception {
	super.setUp();
    }

    @After
    public void tearDown() throws Exception {
	super.tearDown();
    }



    ////////////////////////////////////////////////
    // LOCATION
    //  POST
    @Test
    public void postShouldCreateNewLocation(){
	Response response = sendPayload(Nouns.LOCATION, METHOD.POST);
 	assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
    }

    @Test
    public void shouldPostNewLocationContentLocationInHeader(){
	Response response = sendPayload(Nouns.LOCATION, METHOD.POST);
	assertTrue(response.getHeaders().containsKey(WSConstants.CONTENT_LOCATION));
    }

    @Test
    public void postLocationShouldFailIfIdIsSet(){
	long existingId = MockState.containerMap.get(MockState.locationMap.keySet().iterator().next()).id;
	Response response = sendPayload(Nouns.LOCATION, existingId, METHOD.POST);
	assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
    }

    @Test
    public void putShouldFailUpdatingNonExistantLocation(){
	long badId = 9999999l;
	Response response = sendPayload(Nouns.LOCATION, badId, METHOD.PUT);
	assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }


    
    @Test
    public void putUpdateLocationContainer(){
    	Location loc = MockState.locations.get(3);
	System.err.println("Location.containerId=" + loc.containerId);
	while(true){
	    Container c = MockState.containers.get(random.nextInt(MockState.containers.size()));
	    if(c.id != loc.containerId){
		loc.containerId = c.id;
		loc.wellRow = "Z";
		break;
	    }
	}

	System.err.println("---Location.containerId=" + loc.containerId);
    	Response response = sendPayload((Payload)loc, Nouns.LOCATION, false, loc.id, METHOD.PUT);
    	assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
    }



}
