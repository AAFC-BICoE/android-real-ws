package ca.gc.agr.mbb.seqdb.ws.payload;

import ca.gc.agr.mbb.seqdb.ws.Payload;

public class ContainerType extends BasePayload{
    public String name = null;
    public String baseType = null;
    public Integer numberOfColumns = null;
    public Integer numberOfRows = null;
    public Integer numberOfWells = null;

    public String lastModified;

    public void update(Payload payload){
	super.update(payload);
	if(payload == null){
	    return;
	}

	ContainerType u = (ContainerType)payload;
	if(u.name != null){
	    this.name = u.name;
	}
	if(u.baseType != null){
	    this.baseType = u.baseType;
	}
	if(u.numberOfColumns != null){
	    this.numberOfColumns = u.numberOfColumns;
	}
	if(u.numberOfRows != null){
	    this.numberOfRows = u.numberOfRows;
	}
	if(u.numberOfRows != null
	   && u.numberOfRows != null){
	    this.numberOfWells = this.numberOfRows * this.numberOfColumns;
	}
    }
}
