package ca.gc.agr.mbb.seqdb.ws.payload;

import ca.gc.agr.mbb.seqdb.ws.Payload;

//public class Container implements Payload{
public class Container extends BasePayload{

    public String containerNumber;
    public ContainerType containerType;
    public String locations;
    public String lastModified = null;

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


    public void update(Payload payload){
	super.update(payload);
	if(payload == null){
	    return;
	}

	Container u = (Container)payload;
	if(u.containerNumber != null){
	    this.containerNumber = u.containerNumber;
	}
	if(u.locations != null){
	    this.locations = u.locations;
	}
	
	if(containerType != null){
	    containerType.update(u.containerType);
	}
    }

    public String toString(){
	return id.toString();
    }
}
