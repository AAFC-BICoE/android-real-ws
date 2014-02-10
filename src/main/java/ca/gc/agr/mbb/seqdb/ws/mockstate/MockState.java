package ca.gc.agr.mbb.seqdb.ws.mockstate;

import ca.gc.agr.mbb.seqdb.ws.payload.Location;
import ca.gc.agr.mbb.seqdb.ws.payload.Container;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class MockState{
    static int containerRange = 500;
    static int locationRange = 2000;

    public static int numContainers;
    public static int numLocations;

    public static Map<Integer,List<Integer>>containerLocationsMap = new HashMap<Integer, List<Integer>>(containerRange);

    public static Container[] containers; 
    public static Location[] locations; 
    static Random random = new Random();

    static{
	init();
    }

    static final void init() {
	numContainers = random.nextInt(containerRange);
	numLocations = random.nextInt(locationRange);
	
	containers = new Container[numContainers];
	for(int i=0; i<numContainers; i++){
	    containers[i] = new Container((long)i);
	    List<Integer> locationList = new ArrayList<Integer>();
	    containerLocationsMap.put(i, locationList);
	}
	
	locations = new Location[numLocations];
	for(int i=0; i<numLocations; i++){
	    locations[i] = new Location((long)(12000+i));
	    int containerId = random.nextInt(numContainers);
	    locations[i].containerId = (long)containerId;
	    containerLocationsMap.get(containerId).add(i);
	}
    }


}
