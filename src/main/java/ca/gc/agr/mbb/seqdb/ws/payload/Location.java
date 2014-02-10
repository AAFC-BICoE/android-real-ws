package ca.gc.agr.mbb.seqdb.ws.payload;

import ca.gc.agr.mbb.seqdb.ws.Payload;

public class Location implements Payload{
    public long id;
    public Integer wellColumn;
    public String wellRow;
    public long containerId;

    public Location(String id){
	this(Long.parseLong(id));
    }

    public Location(final long id){
	this.id = id;
    }

    public Location(){

    }

}
