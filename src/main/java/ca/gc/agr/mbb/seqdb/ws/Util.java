package ca.gc.agr.mbb.seqdb.ws;

public class Util{
    
    public static final boolean okId(long id, long max){
	return (id >= 0 && id < max);
    }

}
