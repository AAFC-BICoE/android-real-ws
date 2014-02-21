package ca.gc.agr.mbb.seqdb.ws;

import java.util.logging.*;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.google.gson.Gson;
import ca.gc.agr.mbb.seqdb.ws.webservices.WSConstants;
 
@XmlRootElement
@Produces({MediaType.APPLICATION_JSON})
@JsonInclude(Include.NON_NULL)
public class BaseWS implements WSConstants{
    static final boolean DEBUG = System.getProperties().containsKey("debug");
    public static boolean ALL_DEBUG = false;
    static GsonWrapper gsonWrapper = new GsonWrapper();
    protected final Logger logger=Logger.getLogger(this.getClass().getPackage().getName());
    public BaseWS(){

    }


    public String toJson(final Envelope envelope){
	if(envelope == null){
	    return null;
	}
	return gsonWrapper.toJson(envelope);
    }

    public Object fromJson(String s, Class toClass){
	return gsonWrapper.fromJson(s, toClass);
    }

    public Response notFound(String noun, long id){
	//return Response.status(Response.Status.NOT_FOUND).entity(null).build();
	Envelope envelope = new Envelope();
	envelope.getMeta().errorString = "Item of type " + noun + " unable to find with id=" + id;
	envelope.getMeta().status = 404;
	return Response.status(Response.Status.NOT_FOUND).entity(toJson(envelope)).build();
    }

    public Response ok(Envelope envelope){
	envelope.getMeta().status = 200;
	Response r = Response.ok(toJson(envelope)).build();
	return r;
    }

    public Response fatal(Throwable t){
	logger.log(Level.SEVERE, "Fatal Error ", t);
	if(!DEBUG){
	    return Response.status(Response.Status.INTERNAL_SERVER_ERROR ).build();
	}else{
	    Envelope env = new Envelope();
	    env.getMeta().errorString = Util.exceptionString(t);
	    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(toJson(env)).build();
	}

    }

    

    public int limit(int v){
	if(v < MAX_PAGING_LIMIT){
	    return v;
	}else{
	    return MAX_PAGING_LIMIT;
	}
    }
}
