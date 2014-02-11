package ca.gc.agr.mbb.seqdb.ws.mockstate;

import ca.gc.agr.mbb.seqdb.ws.payload.Location;
import ca.gc.agr.mbb.seqdb.ws.payload.Container;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;


public class MockState{
    public static int containerRange = 40;
    public static int locationRange = 100;

    public static int numContainers;
    public static int numLocations;

    public static Map<Integer,List<Integer>>containerLocationsMap = new HashMap<Integer, List<Integer>>(containerRange);

    public static Container[] containers; 
    public static Location[] locations; 
    public static Map<Integer,Container> containerMap; 
    public static Map<Integer, Location> locationMap; 

    static Random random = new Random();

    static{
	init();
    }

    static final void init() {
	numContainers = random.nextInt(containerRange);
	numLocations = random.nextInt(locationRange);
	
	containers = new Container[numContainers];
	containerMap = new HashMap<Integer, Container>(numContainers);
	for(int i=0; i<numContainers; i++){
	    containers[i] = new Container((long)i);
	    containerMap.put(i, containers[i]);
	    List<Integer> locationList = new ArrayList<Integer>();
	    containerLocationsMap.put(i, locationList);
	}
	
	locations = new Location[numLocations];
	locationMap = new HashMap<Integer, Location>(numLocations);
	for(int i=0; i<numLocations; i++){
	    Location loc = new Location((long)i);
	    locationMap.put(i, loc);
	    loc.wellRow = "A";
	    locations[i] = loc;
	    int containerId = random.nextInt(numContainers);
	    loc.containerId = (long)containerId;
	    loc.wellColumn = 1 + random.nextInt(7);
	    switch (loc.wellColumn){
	    case 1:
	    case 2:
		loc.wellRow = "A";
		break;
	    case 3:
	    case 4:
		loc.wellRow = "E";
		break;
	    default:
		loc.wellRow = "B";
	    }
	    containerLocationsMap.get(containerId).add(i);
	}
    }


}
