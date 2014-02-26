package ca.gc.agr.mbb.seqdb.ws.payload;

import ca.gc.agr.mbb.seqdb.ws.Payload;


public class Location extends BasePayload{
    //public class Location  implements Payload{

    public Integer wellColumn=null;
    public String wellRow=null;
    public Long containerId=null;
    public String lastModified = null;

    public MixedSpecimen mixedSpecimen=null;

    public Location(String id){
	this(Long.parseLong(id));
    }

    public Location(final long id){
	this.id = id;

    }

    public Location(){

    }

    public void update(Payload payload){
	super.update(payload);
	if(payload == null){
	    return;
	}
	Location u = (Location)payload;
	
	if(u.wellColumn != null){
	    this.wellColumn = u.wellColumn;
	}

	if(u.wellRow != null){
	    this.wellRow = u.wellRow;
	}

	if(u.containerId != null){
	    this.containerId = u.containerId;
	}
	if(this.mixedSpecimen != null){
	    this.mixedSpecimen.update(u.mixedSpecimen);
	}	
    }

    public String toString(){
	return id.toString();
    }
}
