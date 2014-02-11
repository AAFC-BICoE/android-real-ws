package ca.gc.agr.mbb.seqdb.ws.webservices;

import javax.ws.rs.core.Response;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
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

    @GET @Path(CONTAINER)
    public String listContainers(@Context UriInfo uri) {
	PagingPayload payload = new PagingPayload(42);
	payload.baseUrl = uri.getAbsolutePath().toString();
	for(int i=0; i<MockState.containers.length; i++){
	    payload.addUrl(Long.toString(MockState.containers[i].id));
	}
	
	//Envelope envelope = new Envelope(payload, uri.getBaseUri().toString());
	Envelope envelope = new Envelope(uri.getAbsolutePath().toString());
	envelope.pagingPayload = payload;
	return toJson(envelope);
    }

    @GET @Path(CONTAINER+ID_PARAM)
    public Response getContainer(@PathParam(ID) final int id, @Context UriInfo uri) {
	if(!MockState.containerMap.containsKey(id)){
	    return Response.status(Response.Status.NOT_FOUND).entity("Container not found for ID: " + id).build();
	}

	Container container = new Container(id);
	container.locations = uri.getBaseUri().toString() + WSConstants.BASEPATH + LOCATION + "/" + CONTAINER + "/" + id;
	Envelope envelope = new Envelope(uri.getBaseUri().toString());
	envelope.container = container;

	return Response.ok(toJson(envelope)).build();
    }

 
    @GET @Path(CONTAINER+COUNT_PATH)
    public String countContainers(@Context UriInfo uri) {
	CountPayload cp = new CountPayload(117);
	Envelope envelope = new Envelope(uri.getBaseUri().toString());
	envelope.countPayload = cp;
    
	return toJson(envelope);
    }

}
