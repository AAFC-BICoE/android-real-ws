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
import ca.gc.agr.mbb.seqdb.ws.http.Main;
import ca.gc.agr.mbb.seqdb.ws.mockstate.MockState;
import ca.gc.agr.mbb.seqdb.ws.payload.Container;
import ca.gc.agr.mbb.seqdb.ws.webservices.WSConstants;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class) 
public class ContainerGetByIdTestIT extends BaseTestIT{
    private int containerRange = 5;
    private Long id = null;
    private Long expectedId = null;

    public ContainerGetByIdTestIT(final Long id, final Long expectedId){
	this.id = id;
	//this.expectedId = expectedId;
    }


    @Parameters
    public static Collection<Long[]> data() {
	MockState.containerRange = 5;
	MockState.init();
	List<Long[]> params =
	    new ArrayList<Long[]>();
	for(Container container: MockState.containers){
	    params.add(new Long[] {container.id, null});
	}
	return params;
    }

    

    @Before
    public void setUp() throws Exception {
	MockState.containerRange = containerRange;
	super.setUp();
    }

    @After
    public void tearDown() throws Exception {
	super.tearDown();
    }
    

    @Test
    public void shouldGetContainerById(){
	String path = WSConstants.BASEPATH + Nouns.CONTAINER + "/" +  id;
	System.err.println("path=[" + path + "]");
	Response response = target.path(path).request().accept(MediaType.APPLICATION_JSON).get();
	assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void shouldFailWithBadId(){
    	String path = WSConstants.BASEPATH + Nouns.CONTAINER + "/" +  id + "b";
    	System.err.println("path=[" + path + "]");
    	Response response = target.path(path).request().accept(MediaType.APPLICATION_JSON).get();
    	assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void shouldFailWithOutOfRangeId(){
	long outOfRangeId = MockState.numContainers+id+5;
	System.err.println("Out=" + outOfRangeId);
    	String path = WSConstants.BASEPATH + Nouns.CONTAINER + "/" + outOfRangeId;
    	System.err.println("path=[" + path + "]");
    	Response response = target.path(path).request().accept(MediaType.APPLICATION_JSON).get();
    	assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }
}

