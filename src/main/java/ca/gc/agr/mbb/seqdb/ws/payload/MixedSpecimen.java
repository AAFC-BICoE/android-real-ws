package ca.gc.agr.mbb.seqdb.ws.payload;

import ca.gc.agr.mbb.seqdb.ws.Payload;

public class MixedSpecimen extends BasePayload{
    public String mixedSpecimenNumber;
    public String fungiIsolated;
    public String notes;
    public String treatment;
    public String project;

    public String locationUrl;
    transient public Location location = null;

    //public Date sampleDestroyed;
    //public Timestamp lastModified;


    public void update(Payload payload){
	super.update(payload);
	if(payload == null){
	    return;
	}
	MixedSpecimen u = (MixedSpecimen)payload;
	
	if(u.mixedSpecimenNumber != null){
	    this.mixedSpecimenNumber = u.mixedSpecimenNumber;
	}

	if(u.fungiIsolated != null){
	    this.fungiIsolated = u.fungiIsolated;
	}

	if(u.notes != null){
	    this.notes = u.notes;
	}

	if(u.treatment != null){
	    this.treatment = u.treatment;
	}

	if(u.project != null){
	    this.project = u.project;
	}
    }

}
