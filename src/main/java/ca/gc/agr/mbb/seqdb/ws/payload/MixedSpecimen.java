package ca.gc.agr.mbb.seqdb.ws.payload;

import ca.gc.agr.mbb.seqdb.ws.Payload;

public class MixedSpecimen extends BasePayload{
    public String mixedSpecimenNumber;
    public String fungiIsolated;
    public String notes;
    public String treatment;
    public String project;

    public String debugLocationUrl=null;
    public Long locationId=null;
    transient public Location location = null;

    //public Date sampleDestroyed;
    //public Timestamp lastModified;


}
