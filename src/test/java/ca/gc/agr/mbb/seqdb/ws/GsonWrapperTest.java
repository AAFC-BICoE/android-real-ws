package ca.gc.agr.mbb.seqdb.ws;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.Random;

import ca.gc.agr.mbb.seqdb.ws.payload.Container;
import ca.gc.agr.mbb.seqdb.ws.payload.Location;
import com.google.gson.JsonObject;
;import com.google.gson.Gson;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GsonWrapperTest{

    @Test
    public void simple(){
	try{
	    GsonWrapper gw = new GsonWrapper();
	    
	    String json = "{\"containerNumber\":\"4-2765\",\"containerType\":{\"name\":\"Acme\",\"baseType\":\"standard #1\",\"numberOfColumns\":8,\"numberOfRows\":12,\"numberOfWells\":96},\"lastModified\":\"Mon Mar 31 00:42:02 EDT 2014\",\"id\":9999}";

	    Object c = gw.fromJson(json, Container.class);
	    System.out.println(c);  
	    assertEquals(true, c != null);
	}catch(Throwable t){
	    t.printStackTrace();
	}

    }

    @Test 
    public void location(){
	try{
	    GsonWrapper gw = new GsonWrapper();
	    String json = "{\"wellColumn\":7,\"wellRow\":\"F\",\"containerId\":0,\"lastModified\":\"Mon Mar 31 00:53:29 EDT 2014\",\"mixedSpecimenId\":25553,\"id\":63}";
	    Location c = (Location)gw.fromJson(json, Location.class);
	    System.out.println(c);  
	    assertEquals(true, c != null);
	}catch(Throwable t){
	    t.printStackTrace();
	}
    }

}
