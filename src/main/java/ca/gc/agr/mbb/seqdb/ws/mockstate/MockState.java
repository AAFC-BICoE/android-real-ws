package ca.gc.agr.mbb.seqdb.ws.mockstate;

import ca.gc.agr.mbb.seqdb.ws.payload.Location;
import ca.gc.agr.mbb.seqdb.ws.payload.Container;
import ca.gc.agr.mbb.seqdb.ws.payload.ContainerType;
import ca.gc.agr.mbb.seqdb.ws.payload.MixedSpecimen;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.UUID;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Calendar;


public class MockState{
    
    public static int containerRange = 60;
    public static int locationRange = 110;

    public static int numberOfColumns = 8;
    public static int numberOfRows = 8;
    public static int numberOfWells = numberOfRows * numberOfColumns;

    public static int numContainers;
    public static int numLocations;

    public static Map<Long,List<Long>>containerLocationsMap = new HashMap<Long, List<Long>>(containerRange);

    // this represents an occupied container column row
    // "containerID_column_row"
    public static Set<String> occupiedContainerIDLocationColumnRow = new HashSet<String>();
    

    public static List<Container> containers; 
    public static List<Location> locations; 
    public static List<MixedSpecimen> mixedSpecimens; 

    public static Map<Long,Container> containerMap; 
    public static Map<Long, Location> locationMap; 
    public static Map<Long, MixedSpecimen> mixedSpecimenMap; 

    public static ContainerType containerType1 = null;

    static Random random = new Random();

    static{
	init();
    }

    public static final void init() {
	containerType1 = addNewContainerType("Acme");
	numContainers = containerRange;
	numLocations = locationRange;

	mixedSpecimens = new ArrayList<MixedSpecimen>(numLocations); 
	mixedSpecimenMap = new HashMap<Long, MixedSpecimen>(); 
	
	containers = new ArrayList<Container>(numContainers);
	containerMap = new HashMap<Long, Container>(numContainers);
	for(int i=0; i<numContainers; i++){
	    addNewContainer(i );
	}
	
	locations = new ArrayList<Location>(numLocations);
	locationMap = new HashMap<Long, Location>(numLocations);
	for(int i=0; i<numLocations; i++){
	    addNewLocation(i);
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
	return containers.size();
    }

    public static final long countMixedSpecimens(){
	if(mixedSpecimens == null){
	    return 0l;
	}
	return mixedSpecimens.size();
    }

    public static String timeStamp(){
	return Calendar.getInstance().getTime().toString();
    }

    public static final void addNewContainer(int i){
	Container newContainer = new Container((long)i);
	newContainer.containerType = addNewContainerType(i);
	newContainer.containerNumber = "" + random.nextInt(10) + "-" + random.nextInt(20000);
	newContainer.lastModified = timeStamp();
	containers.add(newContainer);
	containerMap.put((long)i, newContainer);
	List<Long> locationList = new ArrayList<Long>();
	containerLocationsMap.put((long)i, locationList);
    }

    public static final void addNewLocation(int locationId){
	try{
	    Location loc = new Location((long)locationId);
	    int mixedSpecimenId = 20000 + random.nextInt(20000);
	    loc.mixedSpecimen = makeMixedSpecimen(mixedSpecimenId, (long)locationId);
	    addMixedSpecimen(loc.mixedSpecimen);
	    loc.mixedSpecimen.location = loc;
	    loc.mixedSpecimenId = loc.mixedSpecimen.id;

	    loc.lastModified = timeStamp();

	    //addNewMixedSpecimen(mixedSpecimenId);
	    System.err.println("MockState.addNewLocation: mixedSpecimenId: " + 	loc.mixedSpecimen.id);

	    locationMap.put((long)locationId, loc);
	    loc.wellRow = "A";
	    locations.add(loc);
	    
	    while(true){
		int containerId = random.nextInt(numContainers);
		loc.containerId = (long)containerId;
		loc.wellColumn = 1 + random.nextInt(7);
		switch (loc.wellColumn){
		case 1:
		    loc.wellRow = "A";
		    break;
		case 2:
		    loc.wellRow = "B";
		    break;
		case 3:
		    loc.wellRow = "C";
		    break;
		case 4:
		    loc.wellRow = "D";
		    break;
		case 5:
		    loc.wellRow = "E";
		    break;
		default:
		    loc.wellRow = "F";
		}
		String occupiedKey = makeOccupied(containerId, loc.wellColumn, loc.wellRow);
		if(!occupiedContainerIDLocationColumnRow.contains(occupiedKey)){
		    occupiedContainerIDLocationColumnRow.add(occupiedKey);
		    System.err.println("Adding occupied: " + occupiedKey);
		    containerLocationsMap.get((long)containerId).add((long)locationId);	
		    break;
		}
	    }
	}
	catch(Throwable t){
	    t.printStackTrace();
	}
    }

    static final String makeOccupied(int containerId, Integer column, String row){
	return Integer.toString(containerId) + "_" + Integer.toString(column) + "_" + row;
    }

    public static final void addMixedSpecimen(MixedSpecimen ms){
	mixedSpecimenMap.put(ms.id, ms);
	mixedSpecimens.add(ms);
	System.err.println("MockState.addNewMixedSpecimen: mixedSpecimenId: " + ms.id);
    }

    public static MixedSpecimen makeMixedSpecimen(int id, long locationId){
	MixedSpecimen m = new MixedSpecimen();
	m.fungiIsolated = TextContent.randomFungus();
	m.notes = TextContent.randomText();
	m.treatment = TextContent.randomText();
	m.project = UUID.randomUUID().toString().substring(0,6);
	m.id = (long)id;
	m.locationId = locationId;
	return m;
    }

    public static ContainerType addNewContainerType(int i){
	return containerType1;
    }
    
    public static ContainerType addNewContainerType(String name){
	ContainerType ct = new ContainerType();
	ct.baseType = "standard #1";
	ct.name = name;

	ct.numberOfColumns = 8;
	ct.numberOfRows = 12;
	ct.numberOfWells = ct.numberOfRows * ct.numberOfColumns;

	return ct;
    }


    public static void updateMixedSpecimen(MixedSpecimen oldMs, MixedSpecimen newMs){
	if(newMs.mixedSpecimenNumber != null){
	    oldMs.mixedSpecimenNumber = newMs.mixedSpecimenNumber;
	}

	if(newMs.fungiIsolated != null){
	    oldMs.fungiIsolated = newMs.fungiIsolated;
	}

	if(newMs.notes != null){
	    oldMs.notes = newMs.notes;
	}

	if(newMs.treatment != null){
	    oldMs.treatment = newMs.treatment;
	}

	if(newMs.project != null){
	    oldMs.project = newMs.project;
	}
	
	if(newMs.locationId != null){
	    oldMs.locationId = newMs.locationId;
	}
    }

    public static void updateLocation(Location oldLoc, Location newLoc)
	throws OccupiedLocationException, UnknownEntityException,IllegalArgumentException
    {
	if(!containerMap.containsKey(newLoc.containerId)){
	    throw new UnknownEntityException("Container id=" + newLoc.containerId + " does not exist");
	}

	String occupiedKey = makeOccupied((int)((long)newLoc.containerId), newLoc.wellColumn, newLoc.wellRow);

	if(occupiedContainerIDLocationColumnRow.contains(occupiedKey)){
	    throw new OccupiedLocationException("Location is occupied");
	}

	if(oldLoc.containerId != newLoc.containerId){
	    List<Long>locations = containerLocationsMap.get(oldLoc.containerId);
	    locations.remove(oldLoc.id);

	    locations = containerLocationsMap.get((int)((long)newLoc.containerId));
	    locations.add(oldLoc.id);
	}
	oldLoc.wellColumn = newLoc.wellColumn;
	oldLoc.wellRow = newLoc.wellRow;
	oldLoc.containerId = newLoc.containerId;
    }
}
