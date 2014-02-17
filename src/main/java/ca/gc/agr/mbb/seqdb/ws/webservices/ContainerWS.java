package ca.gc.agr.mbb.seqdb.ws.webservices;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
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
	if(!MockState.containerMap.containsKey(id)){
	    return notFound(CONTAINER, id);
	}
	Container container = MockState.containerMap.get(id);
	if(MockState.countLocationsForContainer(id) > 0){
	    container.locations = uri.getBaseUri().toString() + WSConstants.BASEPATH + LOCATION + "/" + CONTAINER + "/" + id;
	}
	Envelope envelope = new Envelope(uri, container);
	return ok(envelope);
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

    /////////////// PUT (create)
    @PUT @Path(CONTAINER)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createContainer(@Context UriInfo uri) {
	int newId = MockState.containers.size() +1;
	MockState.addNewContainer(newId);
	URI contentLocationURI = null;
	try{
	    contentLocationURI = new URI("foo" + newId);
	}catch(URISyntaxException e){
	    e.printStackTrace();
	    return Response.status(Response.Status.INTERNAL_SERVER_ERROR ).build();
	}
 	return Response.status(Response.Status.CREATED).contentLocation(contentLocationURI).build();
    }

}
