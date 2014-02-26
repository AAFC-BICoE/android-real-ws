package ca.gc.agr.mbb.seqdb.ws.webservices;

import ca.gc.agr.mbb.seqdb.ws.webservices.WSConstants;
import ca.gc.agr.mbb.seqdb.ws.mockstate.MockState;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ca.gc.agr.mbb.seqdb.ws.Nouns;

import ca.gc.agr.mbb.seqdb.ws.http.Main;
import ca.gc.agr.mbb.seqdb.ws.Payload;
import ca.gc.agr.mbb.seqdb.ws.payload.Container;
import ca.gc.agr.mbb.seqdb.ws.payload.MixedSpecimen;
import ca.gc.agr.mbb.seqdb.ws.payload.BasePayload;
import ca.gc.agr.mbb.seqdb.ws.payload.Location;
import com.google.gson.Gson;
import org.glassfish.grizzly.http.server.HttpServer;

abstract public class BaseTestIT implements WSConstants{
    protected HttpServer server = null;
    protected WebTarget target;
    protected Gson gson = null;

    public void setUp() throws Exception {
        // start the server
	Main main = new Main();
	server = main.startServer();
	//create the client
        Client c = ClientBuilder.newClient();
        target = c.target(Main.BASE_URI);
	gson = new Gson();
    }

    public void tearDown() throws Exception {
	if(server != null){
	    server.stop();
	}
    }

    public String toJson(Payload p){
	if(p == null){
	    throw new NullPointerException("Payload is null");
	}
	System.err.println("Class Name=" + p.getClass().getSimpleName().toLowerCase());
	return "{\"" + p.getClass().getSimpleName() + "\": " + gson.toJson(p) + "}";
    }

    protected Response sendPayload(String noun, WSConstants.METHOD method){
	return sendPayload(noun, false, 0, method);
    }

    protected Response sendPayload(String noun, long id, WSConstants.METHOD method){
	return sendPayload(noun, true, id, method);
    }

    protected Response sendPayload(String noun, boolean setId, long id, WSConstants.METHOD method){
	try{
	System.err.println("START--------------------------------------------------------------------------");
	String path = WSConstants.BASEPATH + noun;
	if(method == WSConstants.METHOD.PUT){
	    path += "/" +  id;
	}
	BasePayload p=null;

	System.err.println("Client: " + method + " NOUN: " + noun);
	System.err.println("Client: " + method + " PATH: " + path);

	switch(noun){
	case Nouns.CONTAINER:
	    if(setId && MockState.containerMap.containsKey(id)){
		p = MockState.containerMap.get(id);
	    }else{
		p = new Container();
	    }
	    break;
	case Nouns.LOCATION:
	    if(setId && MockState.locationMap.containsKey(id)){
		p = MockState.locationMap.get(id);
	    }else{
		p = new Location();
		((Location)p).mixedSpecimen = null;
	    }
	    break;
	case Nouns.MIXED_SPECIMEN:
	    if(setId && MockState.mixedSpecimenMap.containsKey(id)){
		p = MockState.mixedSpecimenMap.get(id);
	    }else{
		p = new MixedSpecimen();
	    }
	    break;
	}

	if(setId){
	    p.id = new Long(16745);
	}
	String json = toJson(p);
	Entity entity = Entity.entity(json, MediaType.APPLICATION_JSON);
	System.err.println("Client sending json: " + json);
	System.err.println("SENDING--------------------------------------------------------------------------");
	Response response;
	switch(method){
	    case POST:
		response = target.path(path).request().accept(MediaType.APPLICATION_JSON).post(entity);
		break;
 	    case PUT:
		response = target.path(path).request().accept(MediaType.APPLICATION_JSON).put(entity);
		break;
	    default:
		throw new NullPointerException("Not supported http method: " + method);
	}
	System.err.println("Client response location: " + response.getLocation());
	System.err.println("Client recieved Headers: " + response.getHeaders());
	System.err.println("Client received Headers: Content-Location:" + response.getHeaders().getFirst(WSConstants.CONTENT_LOCATION));
	System.err.println("Client response=" + response.getStatus());

	System.err.println("DONE--------------------------------------------------------------------------");	
	return response;
	}catch(Throwable t){
	    System.err.println("BASE error");
	    t.printStackTrace();
	}
	return null;
    }	

}
