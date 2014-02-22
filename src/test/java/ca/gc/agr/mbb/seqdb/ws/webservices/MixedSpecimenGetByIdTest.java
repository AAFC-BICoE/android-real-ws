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
import ca.gc.agr.mbb.seqdb.ws.payload.MixedSpecimen;
import ca.gc.agr.mbb.seqdb.ws.webservices.WSConstants;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class) 
public class MixedSpecimenGetByIdTest extends BaseTest{
    private static int mixedSpecimenRange = 5;
    private Long id = null;
    private Long expectedId = null;

    public MixedSpecimenGetByIdTest(final Long id, final Long expectedId){
	this.id = id;
    }


    @Parameters
    public static Collection<Long[]> data() {
	MockState.locationRange = mixedSpecimenRange;
	MockState.init();
	List<Long[]> params =
	    new ArrayList<Long[]>();
	for(MixedSpecimen mixedSpecimen: MockState.mixedSpecimens){
	    params.add(new Long[] {mixedSpecimen.id, null});
	}
	return params;
    }

    

    @Before
    public void setUp() throws Exception {
	MockState.locationRange = mixedSpecimenRange;
	super.setUp();
    }

    @After
    public void tearDown() throws Exception {
	super.tearDown();
    }
    
    /*
    @Test
    public void shouldGetMixedSpecimenById(){
	String path = WSConstants.BASEPATH + Nouns.MIXED_SPECIMEN + "/" +  id;
	System.err.println("path=[" + path + "]");
	Response response = target.path(path).request().accept(MediaType.APPLICATION_JSON).get();
	assertEquals(200, response.getStatus());
    }
    */

    @Test
    public void shouldFailWithBadId(){
    	String path = WSConstants.BASEPATH + Nouns.MIXED_SPECIMEN + "/" +  id + "b";
    	System.err.println("path=[" + path + "]");
    	Response response = target.path(path).request().accept(MediaType.APPLICATION_JSON).get();
    	assertEquals(404, response.getStatus());
    }

    @Test
    public void shouldFailWithOutOfRangeId(){
	long outOfRangeId = MockState.countMixedSpecimens() + 100;
	System.err.println("Out=" + outOfRangeId);
    	String path = WSConstants.BASEPATH + Nouns.MIXED_SPECIMEN + "/" + outOfRangeId;
    	System.err.println("path=[" + path + "]");
    	Response response = target.path(path).request().accept(MediaType.APPLICATION_JSON).get();
    	assertEquals(404, response.getStatus());
    }
}

