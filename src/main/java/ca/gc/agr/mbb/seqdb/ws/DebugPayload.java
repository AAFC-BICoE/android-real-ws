package ca.gc.agr.mbb.seqdb.ws;


public class  DebugPayload implements Payload{
    boolean debug;
    String debugUrl;

    public DebugPayload(final boolean debug){
	this.debug = debug;
    }
}
