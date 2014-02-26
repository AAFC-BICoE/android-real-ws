package ca.gc.agr.mbb.seqdb.ws.webservices;

import java.net.URI;
import ca.gc.agr.mbb.seqdb.ws.mockstate.MockState;
import javax.ws.rs.QueryParam;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.core.Request;
import ca.gc.agr.mbb.seqdb.ws.BaseWS;
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
import ca.gc.agr.mbb.seqdb.ws.payload.MixedSpecimen;
import ca.gc.agr.mbb.seqdb.ws.BaseWS;
import ca.gc.agr.mbb.seqdb.ws.CountPayload;
import ca.gc.agr.mbb.seqdb.ws.Envelope;
import ca.gc.agr.mbb.seqdb.ws.Nouns;
import ca.gc.agr.mbb.seqdb.ws.PagingPayload;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Path(WSConstants.BASEPATH)
@XmlRootElement
@Produces({MediaType.APPLICATION_JSON})
@JsonInclude(Include.NON_DEFAULT)
public class MixedSpecimenWS  extends BaseWS implements Nouns, WSConstants{

    public MixedSpecimenWS()
    {
	super();
    }

    @GET @Path(MIXED_SPECIMEN)
    public Response listMixedSpecimens(@Context final Request request,
				      @Context final UriInfo uri,
				      @DefaultValue(DEFAULT_PAGING_OFFSET_STRING) @QueryParam(PAGING_OFFSET_PARAMETER) int offset,
				      @DefaultValue(DEFAULT_PAGING_LIMIT_STRING) @QueryParam(PAGING_LIMIT_PARAMETER) int limit){
	Envelope envelope = null;
	try{
	    limit = limitMax(limit);
	    System.err.println("getAbsolutePath()=" + uri.getAbsolutePath());
	    System.err.println("getBaseUri()=" + uri.getBaseUri());
	    System.err.println("getPath()=" + uri.getPath());
 
	    Long[] mixedSpecimenIds = MockState.mixedSpecimenMap.keySet().toArray(new Long[0]);
	    PagingPayload payload = new PagingPayload(uri.getBaseUri().toString()+ BASEPATH + MIXED_SPECIMEN,
						      offset,
						      limit,
						      mixedSpecimenIds,
						      false);
	    envelope = new Envelope(uri, payload);
	}catch(Throwable t){
	    t.printStackTrace();
	}
	return ok(envelope);
    }


    @GET @Path(MIXED_SPECIMEN+ID_PARAM)
    public Response getMixedSpecimen(@PathParam(ID) final long id, @Context UriInfo uri) {
	try{
	    System.err.println("getting: " + id);
	    if(!MockState.mixedSpecimenMap.containsKey(id)){
		System.err.println("Not able to find id=" + id);
		return notFound(MIXED_SPECIMEN, id);
	    }
	    MixedSpecimen mixedSpecimen = MockState.mixedSpecimenMap.get(id);
	    System.err.println("Found mxedSpecimen: " + mixedSpecimen.fungiIsolated);
	    Envelope envelope = new Envelope(uri, mixedSpecimen);
	    return ok(envelope);
	}catch(Throwable t){
	    t.printStackTrace();
	    return fatal(t);
	}
    }

 
    @GET @Path(MIXED_SPECIMEN+COUNT_PATH)
    public Response countMixedSpecimens(@Context UriInfo uri) {
	CountPayload countPayload = new CountPayload(MockState.countMixedSpecimens());
	Envelope envelope = new Envelope(uri, countPayload);
	return ok(envelope);
    }


    /////////////// DELETE
    @DELETE @Path(MIXED_SPECIMEN+ID_PARAM)
    public Response deleteMixedSpecimen(@PathParam(ID) final long id, @Context UriInfo uri) {
	if(!MockState.mixedSpecimenMap.containsKey(id)){
	    return Response.status(Response.Status.NOT_FOUND).entity("MixedSpecimen not found for ID: " + id).build();
	}

	MockState.mixedSpecimenMap.remove(id);
	return Response.status(Response.Status.NO_CONTENT).build();
    }

    /////////////// PUT (create)
    @POST @Path(MIXED_SPECIMEN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createMixedSpecimen(String json, @Context UriInfo uri) {
	try{
	    System.err.println("MixedSpecimen put json=[" + json + "]");
	    MixedSpecimen mixedSpecimen = null;
	    mixedSpecimen = (MixedSpecimen)fromJson(json, MixedSpecimen.class);
	    
	    int newId = MockState.mixedSpecimens.size() +1;
	    MockState.addNewMixedSpecimen(newId);
	    mixedSpecimen.id = new Long(newId);
	    System.err.println("MixedSpecimen: " + mixedSpecimen);
	    URI contentLocationURI = null;
	    contentLocationURI = new URI(uri.getBaseUri().toString() + WSConstants.BASEPATH + LOCATION + "/" + MIXED_SPECIMEN + "/" + newId);
	    return Response.status(Response.Status.CREATED).contentLocation(contentLocationURI).build();
	}catch(Throwable t){
	    return fatal(t);
	}
    }


    /////////////// PUT (update)
    @PUT @Path(MIXED_SPECIMEN+ID_PARAM)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateMixedSpecimen(String json, @PathParam(ID) final long id, @Context UriInfo uri) {
	System.err.println("####################################################################################################");
	System.err.println("PUT updateMixedSpecimen: id=" + id);
    	try{
	    if(!MockState.mixedSpecimenMap.containsKey(id)){
		System.err.println("######################### Not able to find id=" + id);
		return notFound(MIXED_SPECIMEN, id);
	    }
    	    System.err.println("MixedSpecimen update id="
			       + id
			       + " json=[" + json + "]");
	    MixedSpecimen mixedSpecimen = MockState.mixedSpecimenMap.get(id);
	    
    	    MixedSpecimen newMixedSpecimen = (MixedSpecimen)fromJson(json, MixedSpecimen.class);
	    mixedSpecimen.update(newMixedSpecimen);

    	    URI contentLocationURI = null;
    	    //contentLocationURI = new URI(uri.getBaseUri().toString() + WSConstants.BASEPATH + LOCATION + "/" + MIXEDSPECIMEN + "/" + newId);
    	    return Response.status(Response.Status.ACCEPTED).build();
    	}catch(Throwable t){
    	    return fatal(t);
    	}
    }

}

