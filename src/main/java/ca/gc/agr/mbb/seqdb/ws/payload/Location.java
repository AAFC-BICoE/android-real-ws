package ca.gc.agr.mbb.seqdb.ws.payload;

import ca.gc.agr.mbb.seqdb.ws.Payload;


public class Location extends BasePayload{
    //public class Location  implements Payload{

    public Integer wellColumn=null;
    public String wellRow=null;
    public Long containerId=null;
    public String lastModified = null;
    public String debugMixedSpecimenUrl=null;
    public Long mixedSpecimenId=null;

    transient public MixedSpecimen mixedSpecimen=null;


    public Location(String id){
	this(Long.parseLong(id));
    }

    public Location(final long id){
	this.id = id;

    }

    public Location(){

    }


    public String toString(){
	return id.toString();
    }
}
