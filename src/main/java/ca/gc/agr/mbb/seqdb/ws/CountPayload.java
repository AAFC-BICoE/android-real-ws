package ca.gc.agr.mbb.seqdb.ws;

public class CountPayload implements Payload{
    public Integer total = 9999;

    public CountPayload(final Integer total){
	this.total = total;
    }
}
