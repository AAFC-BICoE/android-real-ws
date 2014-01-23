package ca.gc.agr.mbb.androidmockws;

public class Delete{
    String id;
    boolean success;

    public Delete(final String id, final boolean success){
	this.id = id;
	this.success = success;
    }
}
