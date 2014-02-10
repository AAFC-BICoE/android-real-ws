package ca.gc.agr.mbb.seqdb.ws.payload;

import ca.gc.agr.mbb.seqdb.ws.Payload;

public class Container implements Payload{
    public long id = 4666l;
    public String containerNumber = "43";
    public ContainerType containerType;
    public long containerId;
    
    public String locations;

    public Container(final String id){
	this(Long.parseLong(id));
    }

    public Container(final long id){
	this();
	this.id = id;
    }

    public Container(){
	containerType = new ContainerType();
    }

}
