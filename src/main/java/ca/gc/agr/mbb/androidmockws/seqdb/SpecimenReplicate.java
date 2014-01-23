package ca.gc.agr.mbb.androidmockws.seqdb;

import java.util.Random;
import ca.gc.agr.mbb.androidmockws.Util;

public class SpecimenReplicate implements SeqObj{

    public int primaryKey = -1;
    public String name = Util.makeRandomString(7);
    public String state = Util.makeRandomString(12);
    public int specimenIdentifier = 1000 + r.nextInt(100000);
    public String version = Util.makeRandomString(3);
    public String contents = Util.makeRandomString(12, true);
    public String notes = Util.makeRandomString(20, true);
    public String storageMedium = Util.makeRandomString(20, true);

    public String startDate = Util.randomDate(0);
    public String revivalDate = Util.randomDate(-4);
    public String dateDestroyed = Util.randomDate(0,45f);

    public String parent;
    public transient String parentId = null;

    public SeqObj location;
    static final Random r = new Random();

    public SpecimenReplicate(){
	this(1000 + r.nextInt(1000000));
    }

    public SpecimenReplicate(final int id){
	primaryKey = id;
	location = new Location();
    }

    public void makeParent(final String baseUrl){
	if(parentId != null){
	    parent = baseUrl + "/" + parentId;
	}
    }

}
