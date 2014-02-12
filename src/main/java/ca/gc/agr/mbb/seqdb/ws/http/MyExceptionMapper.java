
// From: http://stackoverflow.com/a/18648256/459050
package ca.gc.agr.mbb.seqdb.ws.http;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.glassfish.grizzly.utils.Exceptions;

@Provider
public class MyExceptionMapper implements ExceptionMapper<WebApplicationException> {
    @Override
    public Response toResponse(WebApplicationException ex) {
	System.err.println(Exceptions.getStackTraceAsString(ex));
	if(System.getProperties().containsKey("DEBUG")){
	    //ex.printStackTrace();
	    //return Response.status(500).entity(Exceptions.getStackTraceAsString(ex)).type("text/plain").build();
	}
	return ex.getResponse();
    }

}
