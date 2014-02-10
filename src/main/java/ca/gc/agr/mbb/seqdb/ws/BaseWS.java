package ca.gc.agr.mbb.seqdb.ws;

import javax.xml.bind.annotation.XmlRootElement;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import com.google.gson.Gson;
import javax.ws.rs.WebApplicationException;

@XmlRootElement
@Produces({MediaType.APPLICATION_JSON})
@JsonInclude(Include.NON_NULL)
public class BaseWS{
    public static boolean ALL_DEBUG = false;
    static GsonWrapper gsonWrapper = new GsonWrapper();

    public BaseWS(){

    }

    public String toJson(final Envelope envelope){
	if(envelope == null){
	    return null;
	}
	return gsonWrapper.toJson(envelope);
    }

    public boolean okId(long id, long max){
	if(Util.okId(id, max))
	    return true;
	else
	    throw new WebApplicationException(404);
    }

    
}
