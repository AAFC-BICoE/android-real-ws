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

    public static final void addNewLocation(int i){
	try{
	    Location loc = new Location((long)i);
	    loc.mixedSpecimen = makeMixedSpecimen();
	    loc.lastModified = timeStamp();
	    int mixedSpecimenId = 20000 + random.nextInt(20000);
	    addNewMixedSpecimen(mixedSpecimenId);
	    loc.mixedSpecimen.id = (long)mixedSpecimenId;

	    locationMap.put((long)i, loc);
	    loc.wellRow = "A";
	    locations.add(loc);
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
	catch(Throwable t){
	    t.printStackTrace();
	}
    }

    public static final void addNewMixedSpecimen(int i){
	MixedSpecimen ms = makeMixedSpecimen();
	mixedSpecimenMap.put((long)i, ms);
	mixedSpecimens.add(ms);
    }

    public static MixedSpecimen makeMixedSpecimen(){
	MixedSpecimen m = new MixedSpecimen();
	
	m.fungiIsolated = TextContent.randomFungus();
	m.notes = TextContent.randomText();
	m.treatment = TextContent.randomText();
	m.project = UUID.randomUUID().toString().substring(0,6);
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

}
