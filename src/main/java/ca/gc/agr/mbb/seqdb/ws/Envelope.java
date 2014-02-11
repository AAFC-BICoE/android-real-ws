package ca.gc.agr.mbb.seqdb.ws;

import java.util.List;
import java.util.ArrayList;

import ca.gc.agr.mbb.seqdb.ws.webservices.WSConstants;
import ca.gc.agr.mbb.seqdb.ws.payload.Container;
import ca.gc.agr.mbb.seqdb.ws.payload.Location;

public class Envelope{
    private Meta meta;

    // payloads
    public Container container;
    public Location location;
    public PagingPayload pagingPayload;
    public CountPayload countPayload;
    public DebugPayload debugPayload;

    public Envelope(){
	meta = new Meta();
    }

    public Envelope(final String thisUrl){
	this();
	meta.thisUrl = thisUrl;
    }

    public Meta getMeta(){
	return meta;
    }

    public void setPayload(final Payload payload){
	if(payload instanceof Container){
	    container = (Container)payload;
	}else 	
	    if(payload instanceof Location){
		location = (Location)payload;
	    }else
		if(payload instanceof PagingPayload){
		    pagingPayload = (PagingPayload)payload;
		}else
		    if(payload instanceof CountPayload){
			countPayload = (CountPayload)payload;
			if(payload instanceof DebugPayload){
			    debugPayload = (DebugPayload)payload;
			}
		    }
    }

}
