package ca.gc.agr.mbb.seqdb.ws;

import java.util.List;
import java.util.ArrayList;

import ca.gc.agr.mbb.seqdb.ws.webservices.WSConstants;
import ca.gc.agr.mbb.seqdb.ws.payload.Container;
import ca.gc.agr.mbb.seqdb.ws.payload.Location;
import ca.gc.agr.mbb.seqdb.ws.payload.MixedSpecimen;
import javax.ws.rs.core.UriInfo;

public class Envelope{
    private Meta meta;

    // payloads
    public Container container;
    public Location location;
    public MixedSpecimen mixedSpecimen;
    //
    public PagingPayload pagingPayload;
    public CountPayload countPayload;
    public DebugPayload debugPayload;

    public Envelope(){
	meta = new Meta();
    }

    public Envelope(final UriInfo uriInfo, final Payload payload){
	this();
	meta.thisUrl = uriInfo.getAbsolutePath().toString();
	setPayload(payload);
    }

    public Meta getMeta(){
	return meta;
    }

    public void setPayload(final Payload payload){
	System.err.println("Setting payload: " + payload);
	if(payload instanceof Container){
	    container = (Container)payload;
	}else{ 	
	    if(payload instanceof Location){
		location = (Location)payload;
	    }else{
		if(payload instanceof PagingPayload){
		    pagingPayload = (PagingPayload)payload;
		}else{
		    if(payload instanceof MixedSpecimen){
			mixedSpecimen = (MixedSpecimen)payload;
			System.err.println("Is a MixedSpecimen");
		    }else{ // SPECIALS
			if(payload instanceof CountPayload){
			    countPayload = (CountPayload)payload;
			}else{
			    if(payload instanceof DebugPayload){
				debugPayload = (DebugPayload)payload;
			    }else{
				throw new NullPointerException("Unknown payload type: not known by Envelope");
			    }
			}
		    }
		}
	    }
	}
    }

}

