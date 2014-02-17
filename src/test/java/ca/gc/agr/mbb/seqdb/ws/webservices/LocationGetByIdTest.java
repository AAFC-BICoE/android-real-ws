package ca.gc.agr.mbb.seqdb.ws.webservices;

import java.lang.StackTraceElement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ca.gc.agr.mbb.seqdb.ws.Nouns;
import ca.gc.agr.mbb.seqdb.ws.mockstate.MockState;
import ca.gc.agr.mbb.seqdb.ws.payload.Location;
import ca.gc.agr.mbb.seqdb.ws.webservices.WSConstants;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class) 
public class LocationGetByIdTest extends BaseTest{
    private int locationRange = 5;    
    private Long id = null;
    private Long expectedId = null;

    public LocationGetByIdTest(final Long id, final Long expectedId){
	this.id = id;
	this.expectedId = expectedId;
    }


    @Parameters
    public static Collection<Long[]> data() {
	List<Long[]> params =
	    new ArrayList<Long[]>();
	for(Location location: MockState.locations){
	    params.add(new Long[] {location.id, location.id});
	}
	return params;
    }

    
    @Before
    public void setUp() throws Exception {
	MockState.locationRange = locationRange;;
	super.setUp();
    }

    @After
    public void tearDown() throws Exception {
	super.tearDown();
    }
    

    @Test
    public void shouldGetLocationById(){
	String path = WSConstants.BASEPATH + Nouns.LOCATION + "/" +  id;
	System.err.println("path=[" + path + "]");
	Response response = target.path(path).request().accept(MediaType.APPLICATION_JSON).get();
	assertEquals(200, response.getStatus());
    }

    @Test
    public void shouldNotGetLocationByBadId(){
    	String path = WSConstants.BASEPATH + Nouns.LOCATION + "/" +  (id+locationRange) + "b";
    	System.err.println("path=[" + path + "]");
    	Response response = target.path(path).request().accept(MediaType.APPLICATION_JSON).get();
    	assertEquals(404, response.getStatus());
    }
}

