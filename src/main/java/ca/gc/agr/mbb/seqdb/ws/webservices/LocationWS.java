package ca.gc.agr.mbb.seqdb.ws.webservices;

import java.util.logging.*;
import javax.ws.rs.core.Response;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.DefaultValue;
import java.net.URI;
import java.net.URISyntaxException;
import javax.ws.rs.QueryParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlRootElement;

import ca.gc.agr.mbb.seqdb.ws.Util;


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
    public Response listLocations(@Context final Request request,
				  @Context final UriInfo uri,
				  @DefaultValue(DEFAULT_PAGING_OFFSET_STRING) @QueryParam(PAGING_OFFSET_PARAMETER) int offset,
				  @DefaultValue(DEFAULT_PAGING_LIMIT_STRING) @QueryParam(PAGING_LIMIT_PARAMETER) int limit){
	Envelope envelope = null;
	try{
	    limit = limitMax(limit);
	    System.err.println("getAbsolutePath()=" + uri.getAbsolutePath());
	    System.err.println("getBaseUri()=" + uri.getBaseUri());
	    System.err.println("getPath()=" + uri.getPath());
 
	    Long[] locationIds = MockState.locationMap.keySet().toArray(new Long[0]);
	    PagingPayload payload = new PagingPayload(uri.getBaseUri().toString()+ BASEPATH + LOCATION,
						      offset,
						      limit,
						      locationIds,
						      false);
	    envelope = new Envelope(uri, payload);
	}catch(Throwable t){
	    t.printStackTrace();
	}
	return ok(envelope);
    }

    @GET @Path(LOCATION+ID_PARAM)
    public Response getLocation(@PathParam(ID) final long id, @Context UriInfo uri) {
	if(!MockState.locationMap.containsKey(id)){
	    return notFound(LOCATION, id);
	}
	Location location = MockState.locationMap.get(id);
	System.err.println("Location by id=" + id + " #locations=" + MockState.locations.size());
	Envelope envelope = new Envelope(uri, location);
	return ok(envelope);
    }


    // Get all Locations for Container ID
    @GET @Path(LOCATION + "/" + CONTAINER + ID_PARAM)
    public Response getLocationBYContainer(@PathParam(ID) final long id, @Context final UriInfo uri, @Context final Request request) {
	Envelope envelope = null;
	PagingPayload payload = null;
	System.err.println("LocationId.getLocationBYContainer: " + id + ":" + MockState.numContainers);
	if(!MockState.containerLocationsMap.containsKey(id)){
	    return notFound(LOCATION, id);
	}
	List<Long> locations = MockState.containerLocationsMap.get(id);
	payload = new PagingPayload(uri.getBaseUri().toString()+ BASEPATH + LOCATION, 
				    0l,
				    100,
				    locations.toArray(new Long[0]),
				    false);
	return ok(new Envelope(uri, payload));
    }


    @GET @Path(LOCATION+COUNT_PATH)
    public Response countLocations(@Context final UriInfo uri) {
	System.err.println("# locations=" + MockState.locations.size());
	Envelope envelope = new Envelope(uri, new CountPayload(MockState.locations.size()));
	
	return ok(envelope);
    }

    /////////////// DELETE
    @DELETE @Path(LOCATION+ID_PARAM)
    public Response deleteLocation(@PathParam(ID) final long id, @Context UriInfo uri) {
	if(!MockState.locationMap.containsKey(id)){
	    return Response.status(Response.Status.NOT_FOUND).entity("Location not found for ID: " + id).build();
	}

	MockState.locationMap.remove(id);
	return Response.status(Response.Status.NO_CONTENT).build();
    }

    /////////////// PUT (create)
    @POST @Path(LOCATION)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createLocation(String json, @Context UriInfo uri) throws Throwable {
	try{
	    Location location = null;
	    location = (Location)fromJson(json, Location.class);
	
	    int newId = MockState.locations.size() +1;
	    location.id = new Long(newId);
	    MockState.addNewLocation(newId);
	    logger.log(Level.INFO, location.toString());

	    URI contentLocationURI = null;
	    contentLocationURI = new URI(uri.getBaseUri().toString() + WSConstants.BASEPATH + LOCATION 
					 + "/" + LOCATION + "/" + newId);
	    logger.log(Level.INFO, "PUT Location: all OK");
	    return Response.status(Response.Status.CREATED).contentLocation(contentLocationURI).build();
	}
	catch(Throwable t){
	    return fatal(t);
	}
    }    

}
