package ca.gc.agr.mbb.seqdb.ws.webservices;

import javax.ws.rs.core.Response;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlRootElement;

import ca.gc.agr.mbb.seqdb.ws.BaseWS;
import ca.gc.agr.mbb.seqdb.ws.CountPayload;
import ca.gc.agr.mbb.seqdb.ws.Envelope;
import ca.gc.agr.mbb.seqdb.ws.Nouns;
import ca.gc.agr.mbb.seqdb.ws.PagingPayload;
import ca.gc.agr.mbb.seqdb.ws.mockstate.MockState;
import ca.gc.agr.mbb.seqdb.ws.payload.Location;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


@Path(WSConstants.BASEPATH)
@XmlRootElement
@Produces({MediaType.APPLICATION_JSON})
@JsonInclude(Include.NON_DEFAULT)
//@JsonInclude(Include.NON_NULL)
public class LocationWS  extends BaseWS implements Nouns, WSConstants{

    public LocationWS()
    {
	super();
    }

    @GET @Path(LOCATION)
    public String listLocations(@Context final Request request,
				@Context final UriInfo uri) {
	System.err.println("getAbsolutePath()=" + uri.getAbsolutePath());
	System.err.println("getBaseUri()=" + uri.getBaseUri());
	System.err.println("getPath()=" + uri.getPath());
	PagingPayload payload = new PagingPayload(42);
	payload.baseUrl = "http://www.nrc.ca";
	payload.addUrl("aa");
	payload.addUrl("bb");
	
	Envelope envelope = new Envelope(uri.getAbsolutePath().toString());
	envelope.pagingPayload = payload;
	return toJson(envelope);
    }

    @GET @Path(LOCATION+ID_PARAM)
    public Response getLocation(@PathParam(ID) final long id, @Context UriInfo uri) {
	if(!MockState.locationMap.containsKey((int)id)){
	    return Response.status(Response.Status.NOT_FOUND).entity("Location not found for ID: " + id).build();
	}
	System.err.println("Location by id=" + id + " #locations=" + MockState.locations.length);
	Envelope envelope = new Envelope(uri.getAbsolutePath().toString());
	envelope.location = MockState.locations[(int)id];
	return Response.ok(toJson(envelope)).build();
    }


    // Get all Locations for Container ID
    @GET @Path(LOCATION + "/" + CONTAINER + ID_PARAM)
    public String getLocationBYContainer(@PathParam(ID) final long id, @Context final UriInfo uri, @Context final Request request) {
	Envelope envelope = null;
	PagingPayload payload = null;
	System.err.println("LocationId.getLocationBYContainer: " + id + ":" + MockState.numContainers);
	if( okId(id, MockState.numContainers)){
	    if(MockState.containerLocationsMap.containsKey((int)id)){
		List<Integer> locations = MockState.containerLocationsMap.get((int)id);
									      
		payload = new PagingPayload(locations.size());
		payload.baseUrl = uri.getBaseUri().toString()+ BASEPATH + LOCATION;

		for(Integer locationId: locations){
		    payload.addUrl(Long.toString(locationId));
		}
	    }
	    envelope = new Envelope(uri.getAbsolutePath().toString());
	    envelope.pagingPayload = payload;
	    }
	return toJson(envelope);
    }


    @GET @Path(LOCATION+COUNT_PATH)
    public String countLocations(@Context final UriInfo uri) {
	Envelope envelope = new Envelope(uri.getAbsolutePath().toString());
	envelope.countPayload = new CountPayload(MockState.locations.length);
	return toJson(envelope);
    }

}
