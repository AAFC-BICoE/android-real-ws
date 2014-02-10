package ca.gc.agr.mbb.seqdb.ws;

import java.util.List;
import java.util.ArrayList;

import ca.gc.agr.mbb.seqdb.ws.webservices.WSConstants;

public class Envelope{
    private Meta meta;

    private Payload payload;

    public Envelope(){
	meta = new Meta();
    }

    public Envelope(final Payload payload, final String thisUrl){
	this();
	this.payload = payload;
	meta.payloadType = payload.getClass().getSimpleName().toLowerCase();
	meta.thisUrl = thisUrl;
    }

    public Meta getMeta(){
	return meta;
    }

}
