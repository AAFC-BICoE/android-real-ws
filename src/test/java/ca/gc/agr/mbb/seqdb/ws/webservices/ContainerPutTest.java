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

public class ContainerPutTest extends BaseTest{
    
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
	String foo = "foo";
	Entity entity = Entity.entity(foo, MediaType.APPLICATION_JSON);
	Response response = target.path(path).request().accept(MediaType.APPLICATION_JSON).put(entity);
	assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
    }


}
