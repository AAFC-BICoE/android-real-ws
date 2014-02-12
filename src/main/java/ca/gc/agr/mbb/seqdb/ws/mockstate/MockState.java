package ca.gc.agr.mbb.seqdb.ws.mockstate;

import ca.gc.agr.mbb.seqdb.ws.payload.Location;
import ca.gc.agr.mbb.seqdb.ws.payload.Container;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;


public class MockState{
    public static int containerRange = 60;
    public static int locationRange = 110;

    public static int numContainers;
    public static int numLocations;

    public static Map<Long,List<Long>>containerLocationsMap = new HashMap<Long, List<Long>>(containerRange);

    public static Container[] containers; 
    public static Location[] locations; 
    public static Map<Long,Container> containerMap; 
    public static Map<Long, Location> locationMap; 

    static Random random = new Random();

    static{
	init();
    }

    public static final void init() {
	numContainers = containerRange;
	numLocations = locationRange;
	
	containers = new Container[numContainers];
	containerMap = new HashMap<Long, Container>(numContainers);
	for(int i=0; i<numContainers; i++){
	    containers[i] = new Container((long)i);
	    containerMap.put((long)i, containers[i]);
	    List<Long> locationList = new ArrayList<Long>();
	    containerLocationsMap.put((long)i, locationList);
	}
	
	locations = new Location[numLocations];
	locationMap = new HashMap<Long, Location>(numLocations);
	for(int i=0; i<numLocations; i++){
	    Location loc = new Location((long)i);
	    locationMap.put((long)i, loc);
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
	    containerLocationsMap.get((long)containerId).add((long)i);
	}
    }

    public static final long countLocationsForContainer(long containerId){
	if(containerLocationsMap != null 
	   && containerLocationsMap.containsKey(containerId)){
	    List<Long>locations = containerLocationsMap.get(containerId);
	    if(locations != null){
		return locations.size();
	    }
	}
	return 0;
    }

    public static final long countContainers(){
	if(containers == null){
	    return 0l;
	}
	return containers.length;
    }
}
