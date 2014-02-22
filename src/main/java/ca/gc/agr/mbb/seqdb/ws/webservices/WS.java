package ca.gc.agr.mbb.seqdb.ws.webservices;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.ArrayList;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.net.URI;
import java.util.zip.GZIPOutputStream;
import javax.ws.rs.GET;
import javax.ws.rs.NameBinding;
import javax.ws.rs.Path;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;
import javax.xml.bind.annotation.XmlRootElement;

import ca.gc.agr.mbb.seqdb.ws.BaseWS;
import ca.gc.agr.mbb.seqdb.ws.DebugPayload;
import ca.gc.agr.mbb.seqdb.ws.Envelope;
import ca.gc.agr.mbb.seqdb.ws.Nouns;
import ca.gc.agr.mbb.seqdb.ws.PagingPayload;
import ca.gc.agr.mbb.seqdb.ws.payload.Container;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;



/**
 * Root resource 
 */
@Path(WSConstants.BASEPATH)
@XmlRootElement
@Produces({MediaType.APPLICATION_JSON})
//@JsonInclude(Include.NON_DEFAULT)
@JsonInclude(Include.NON_NULL)
public class WS extends BaseWS implements Nouns, WSConstants{

    public WS(){
    }

    public String name = "my-name";
    public Integer age = 43;
    public Integer time;


    // Base URL: list all possible WS urls
    @GET
    public final Response listResources(@Context UriInfo uri,
				      @DefaultValue("false") @QueryParam("meta__toggleDebug") boolean toggleBoolean) {
	System.err.println("toggleBoolean:" + toggleBoolean);
	if(toggleBoolean){
	    ALL_DEBUG = !ALL_DEBUG;
	}
	PagingPayload paging = new PagingPayload(uri.getAbsolutePath().toString(), 0l, 100, makeBasePayload(), false);
	Envelope envelope = new Envelope(uri, paging);
	envelope.getMeta().thisUrl = uri.getAbsolutePath().toString();
	return ok(envelope);
    }


    // toggle debug
    @GET @Path(DEBUG_PATH)
    public final Response toggleDebug(@Context UriInfo uri) {
	ALL_DEBUG = !ALL_DEBUG;
	Envelope envelope = new Envelope(uri, new DebugPayload(ALL_DEBUG)); 
	return ok(envelope);
    }

    
    //////////////////////////////////////////////////////////////////////////////////////
    static final String[] makeBasePayload(){
	// Base nouns
	String[] urlFragments = new String[WS.NOUNS.length * 2];

	int j=0;
	for(int i=0; i<WS.NOUNS.length; i++){
	    String noun = WS.NOUNS[i];
	    urlFragments[j] = noun;
	    ++j;
	    urlFragments[j] = noun + COUNT_PATH;
	    ++j;
	}
	return urlFragments;
    }



    //=============================================
    @GET @Path(PCR_PRIMER)
    public WS listPcrPrimers() {
	return new WS();
    }

    @GET @Path(PCR_PRIMER+COUNT_PATH)
    public WS countPcrPrimers() {
	return new WS();
    }


    //=============================================
    @GET @Path(SAMPLE)
    public WS listSamples() {
	return new WS();
    }

    @GET @Path(SAMPLE+COUNT_PATH)
    public WS countSamples() {
	return new WS();
    }

    //=============================================
    @GET @Path(SPECIMEN_REPLICATE)
    public WS listSpecimenReplicates() {
	return new WS();
    }

    @GET @Path(SPECIMEN_REPLICATE+COUNT_PATH)
    public WS countSpecimenReplicates() {
	return new WS();
    }

    //=============================================
    @GET @Path(STORAGE)
    public WS listStorages() {
	return new WS();
    }

    @GET @Path(STORAGE+COUNT_PATH)
    public WS countStorageCount() {
	return new WS();
    }
    //=============================================

    public static final String count(String noun){
	return noun + "/count";
    }

	
	
    public String toString(){
        return "Got it!";
    }
}
