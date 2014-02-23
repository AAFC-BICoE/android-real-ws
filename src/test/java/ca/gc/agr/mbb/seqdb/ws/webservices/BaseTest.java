package ca.gc.agr.mbb.seqdb.ws.webservices;

import ca.gc.agr.mbb.seqdb.ws.webservices.WSConstants;
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

abstract public class BaseTest implements WSConstants{
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

    protected Response sendPayload(String noun, boolean setId, WSConstants.METHOD method){
	String path = WSConstants.BASEPATH + noun;
	BasePayload p=null;

	System.err.println("Test: PUT: " + noun);

	switch(noun){
	case Nouns.CONTAINER:
	    p = new Container();
	    break;
	case Nouns.LOCATION:
	    p = new Location();
	    break;
	case Nouns.MIXED_SPECIMEN:
	    p = new MixedSpecimen();
	    break;
	}

	if(setId){
	    p.id = new Long(9999);
	}
	Entity entity = Entity.entity(toJson(p), MediaType.APPLICATION_JSON);

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
	System.err.println("Headers: " + response.getHeaders());
	System.err.println("Headers: Content-Location:" + response.getHeaders().getFirst(WSConstants.CONTENT_LOCATION));
	
	return response;
    }

}
