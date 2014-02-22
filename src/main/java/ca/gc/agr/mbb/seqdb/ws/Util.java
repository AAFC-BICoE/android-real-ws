package ca.gc.agr.mbb.seqdb.ws;

import java.io.PrintWriter;
import java.io.StringWriter;
public class Util{

    
    public static final boolean okId(long id, long max){
	return (id >= 0 && id < max);
    }


    // From stackoverflow: http://stackoverflow.com/a/18546861/459050
    public static String exceptionString(final Throwable throwable) {
	if(throwable == null){
	    return "null";
	}
	try{
	    final StringWriter sw = new StringWriter();
	    final PrintWriter pw = new PrintWriter(sw, true);
	    throwable.printStackTrace(pw);
	    String s = sw.getBuffer().toString();
	    sw.close();
	    pw.close();
	    return s;
	}catch(Throwable t){
	    return "Unable to stringify exception. " + throwable.getMessage();
	}
    }



}
