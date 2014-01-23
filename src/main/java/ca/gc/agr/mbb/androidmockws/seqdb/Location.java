package ca.gc.agr.mbb.androidmockws.seqdb;

import java.util.Random;

import ca.gc.agr.mbb.androidmockws.Util;

public class Location implements SeqObj{
    public int containerNumber = -1;
    public int storageUnit = -1;
    public int compartment = -1;
    public int shelf = -1;
    public int rack = -1;

    public String dateMoved = null;

    public int wellColumn = -1;
    public String wellRow = new String();

    static final Random r = new Random();

    public Location(){
	containerNumber = 1 + r.nextInt(36);
	storageUnit = 1 + r.nextInt(6);
	compartment = 1 + r.nextInt(2);
	shelf = 1 + r.nextInt(4);
	rack = 1 + r.nextInt(3);
	wellColumn = 1 + r.nextInt(6);

	wellRow = Util.makeRandomString(1, false);

	dateMoved = Util.randomDate();
    }

}
