package ca.gc.agr.mbb.androidmockws;


public class Service{
    public String description;
    volatile public String path;
    public String url=null;
    public boolean isWorking=false;

    public Service(final String description, final String path, final boolean isWorking){
	this.description = description;
	this.path = path;
	this.isWorking=isWorking;
    }

    public Service(final Service service){
	this.description = service.description;
	this.url = service.url;
    }

}
