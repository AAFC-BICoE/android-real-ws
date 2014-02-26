package ca.gc.agr.mbb.seqdb.ws.payload;

import ca.gc.agr.mbb.seqdb.ws.Payload;

abstract public class BasePayload implements Payload{
    public Long id;

    public void update(Payload payload){
	if(payload == null){
	    return;
	}
	if(! this.getClass().isAssignableFrom(payload.getClass())){
	    throw new ClassCastException("Payload not " + this.getClass().getName() + ": is instead: " + payload.getClass().getName());
	}
    }
}
