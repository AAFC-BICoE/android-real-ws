package ca.gc.agr.mbb.seqdb.ws.webservices;


import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;

import ca.gc.agr.mbb.seqdb.ws.Nouns;
import ca.gc.agr.mbb.seqdb.ws.http.Main;
import ca.gc.agr.mbb.seqdb.ws.payload.Container;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import static org.junit.Assert.assertEquals;

public class DeleteTest extends BaseTest{
    static final int KNOWN_ID = 2;
    public DeleteTest(){

    }


    @Before
    public void setUp() throws Exception {
	super.setUp();
    }

    @After
    public void tearDown() throws Exception {
	super.tearDown();
    }
    

    ///Container
    @Test
    public void shouldDeleteContainerWithExistingID(){
	String path = WSConstants.BASEPATH + Nouns.CONTAINER + "/" +  KNOWN_ID;
	System.err.println("path=[" + path + "]");
	Response response = target.path(path).request().accept(MediaType.APPLICATION_JSON).delete();
	// put it back so other tests are OK
	Main.mockState.addNewContainer(KNOWN_ID);
	assertEquals(204, response.getStatus());
    }

    @Test
    public void shouldBeNoContainerForDeleteFollowedByGet(){
	// Delete
	String path = WSConstants.BASEPATH + Nouns.CONTAINER + "/" +  KNOWN_ID;
	System.err.println("DELETE path=[" + path + "]");
	Response response = target.path(path).request().accept(MediaType.APPLICATION_JSON).delete();

	// Get the now deleted ID
	path = WSConstants.BASEPATH + Nouns.CONTAINER + "/" +  KNOWN_ID;
	System.err.println("GET path=[" + path + "]");
	response = target.path(path).request().accept(MediaType.APPLICATION_JSON).get();

	// put it back so other tests are OK
	Main.mockState.addNewContainer(KNOWN_ID);	

	assertEquals(404, response.getStatus());
    }

    @Test
    public void shouldNotDeleteContainerWithNonExistingID(){
	String path = WSConstants.BASEPATH + Nouns.CONTAINER + "/" +  Main.mockState.containerRange*2;
	System.err.println("path=[" + path + "]");
	Response response = target.path(path).request().accept(MediaType.APPLICATION_JSON).delete();
	assertEquals(404, response.getStatus());
    }

    //Location
    @Test
    public void shouldDeleteLocationWithExistingID(){
	String path = WSConstants.BASEPATH + Nouns.LOCATION + "/" +  KNOWN_ID;
	System.err.println("path=[" + path + "]");
	Response response = target.path(path).request().accept(MediaType.APPLICATION_JSON).delete();
	// put it back so other tests are OK
	Main.mockState.addNewLocation(KNOWN_ID);
	assertEquals(204, response.getStatus());
	
    }

    @Test
    public void shouldNotDeleteLocationWithNonExistingID(){
	String path = WSConstants.BASEPATH + Nouns.LOCATION + "/" +  Main.mockState.locationRange*2;
	System.err.println("path=[" + path + "]");
	Response response = target.path(path).request().accept(MediaType.APPLICATION_JSON).delete();
	assertEquals(404, response.getStatus());

    }


}

