package ca.gc.agr.mbb.seqdb.ws;

import com.google.gson.Gson;


public class GsonWrapper {
    static Gson gson = null;
    static{
	if(gson == null){
	    gson = new Gson();
	}
    }

    public String toJson(Envelope e){
	e.getMeta().ellapsedMillis = System.currentTimeMillis() - e.getMeta().startMillis;
	return gson.toJson(e);
    }

    public Object fromJson(String s, Class toClass){
	return gson.fromJson(s, toClass);
    }
}
