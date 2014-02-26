package ca.gc.agr.mbb.seqdb.ws.webservices;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlRootElement;

import ca.gc.agr.mbb.seqdb.ws.BaseWS;
import ca.gc.agr.mbb.seqdb.ws.Util;
import ca.gc.agr.mbb.seqdb.ws.CountPayload;
import ca.gc.agr.mbb.seqdb.ws.Envelope;
import ca.gc.agr.mbb.seqdb.ws.Nouns;
import ca.gc.agr.mbb.seqdb.ws.PagingPayload;
import ca.gc.agr.mbb.seqdb.ws.mockstate.MockState;
import ca.gc.agr.mbb.seqdb.ws.payload.Container;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Path(WSConstants.BASEPATH)
@XmlRootElement
@Produces({MediaType.APPLICATION_JSON})
@JsonInclude(Include.NON_DEFAULT)
//@JsonInclude(Include.NON_NULL)
public class ContainerWS  extends BaseWS implements Nouns, WSConstants{

    public ContainerWS()
    {
	super();
    }

    /////////////// GET
    @GET @Path(CONTAINER)
    public Response listContainers(@Context UriInfo uri) {
	PagingPayload payload = new PagingPayload(uri.getAbsolutePath().toString(), 
						  0l, 
						  MockState.containers.size(), 
						  MockState.containerMap.keySet().toArray(new Long[0]),
						  false);
	Envelope envelope = new Envelope(uri, payload);
	return ok(envelope);
    }

    @GET @Path(CONTAINER+ID_PARAM)
    public Response getContainer(@PathParam(ID) final long id, @Context UriInfo uri) {
	int i=1;
	try{
	    System.err.println("getting: " + id);
	    if(!MockState.containerMap.containsKey(id)){
		System.err.println("Not able to find id=" + id);
		return notFound(CONTAINER, id);
	    }
	    Container container = MockState.containerMap.get(id);
	    if(MockState.countLocationsForContainer(id) > 0){
		container.locations = uri.getBaseUri().toString() + WSConstants.BASEPATH + LOCATION + "/" + CONTAINER + "/" + id;
	    }
	    Envelope envelope = new Envelope(uri, container);
	    return ok(envelope);
	}catch(Throwable t){
	    System.err.println(CONTAINER + " GET by ID problem");
	    System.err.println(Util.exceptionString(t));
	    t.printStackTrace();
	    return fatal(t);
	}
    }

 
    @GET @Path(CONTAINER+COUNT_PATH)
    public Response countContainers(@Context UriInfo uri) {
	CountPayload countPayload = new CountPayload(MockState.countContainers());
	Envelope envelope = new Envelope(uri, countPayload);
	return ok(envelope);
    }


    /////////////// DELETE
    @DELETE @Path(CONTAINER+ID_PARAM)
    public Response deleteContainer(@PathParam(ID) final long id, @Context UriInfo uri) {
	if(!MockState.containerMap.containsKey(id)){
	    return Response.status(Response.Status.NOT_FOUND).entity("Container not found for ID: " + id).build();
	}

	MockState.containerMap.remove(id);
	return Response.status(Response.Status.NO_CONTENT).build();
    }

    /////////////// POST (create)
    @POST @Path(CONTAINER)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createContainer(String json, @Context UriInfo uri) {
	try{
	    System.err.println("Container put json=[" + json + "]");
	    Container container = null;
	    container = (Container)fromJson(json, Container.class);
	    
	    int newId = MockState.containers.size() +1;
	    MockState.addNewContainer(newId);
	    container.id = new Long(newId);
	    System.err.println("Container: " + container);
	    URI contentLocationURI = null;
	    contentLocationURI = new URI(uri.getBaseUri().toString() + WSConstants.BASEPATH + LOCATION + "/" + CONTAINER + "/" + newId);
	    return Response.status(Response.Status.CREATED).contentLocation(contentLocationURI).build();
	}catch(Throwable t){
	    return fatal(t);
	}
    }

    /////////////// PUT (update)
    @PUT @Path(CONTAINER+ID_PARAM)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateContainer(String json, @PathParam(ID) final long id, @Context UriInfo uri) {
	System.err.println("####################################################################################################");
	System.err.println("PUT updateContainer: id=" + id);
    	try{
	    if(!MockState.containerMap.containsKey(id)){
		System.err.println("######################### Not able to find id=" + id);
		return notFound(CONTAINER, id);
	    }
    	    System.err.println("Container update id="
			       + id
			       + " json=[" + json + "]");
	    Container container = MockState.containerMap.get(id);
	    
    	    Container newContainer = (Container)fromJson(json, Container.class);
	    container.update(newContainer);

    	    URI contentLocationURI = null;
    	    //contentLocationURI = new URI(uri.getBaseUri().toString() + WSConstants.BASEPATH + LOCATION + "/" + CONTAINER + "/" + newId);
    	    return Response.status(Response.Status.ACCEPTED).build();
    	}catch(Throwable t){
    	    return fatal(t);
    	}
    }

}
