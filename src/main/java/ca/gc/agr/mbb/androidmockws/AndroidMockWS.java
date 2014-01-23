package ca.gc.agr.mbb.androidmockws;

import static spark.Spark.*;
import spark.*;

import java.util.Map;
import java.util.Iterator;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import com.google.gson.Gson;

import ca.gc.agr.mbb.androidmockws.seqdb.SpecimenReplicate;
import ca.gc.agr.mbb.androidmockws.seqdb.ListPage;

public class AndroidMockWS {
    public static final int HTTP_200_OK = 200;
    public static final String HTTP_200_OK_URL = "http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.2.1";
    public static final int HTTP_204_NO_CONTENT = 204; // returned by successful delete
    public static final String HTTP_204_NO_CONTENT_URL = "http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.2.5";
    public static final int HTTP_400_BAD_REQUEST = 400;
    public static final String HTTP_400_BAD_REQUEST_URL = "http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.1";
    public static final int HTTP_404_NOT_FOUND = 404;
    public static final String HTTP_404_NOT_FOUND_URL = "http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.5";
    public static final int HTTP_405_METHOD_NOT_ALLOWED = 405;
    public static final String HTTP_405_METHOD_NOT_ALLOWED_URL = "http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.6";

    private static final Map<Integer, String> HTTP_STATUS_URL_MAP = new HashMap<Integer, String>();
    static {
	HTTP_STATUS_URL_MAP.put(HTTP_200_OK, HTTP_200_OK_URL);
	HTTP_STATUS_URL_MAP.put(HTTP_204_NO_CONTENT, HTTP_204_NO_CONTENT_URL);
	HTTP_STATUS_URL_MAP.put(HTTP_400_BAD_REQUEST, HTTP_400_BAD_REQUEST_URL);
	HTTP_STATUS_URL_MAP.put(HTTP_404_NOT_FOUND, HTTP_404_NOT_FOUND_URL);
	HTTP_STATUS_URL_MAP.put(HTTP_405_METHOD_NOT_ALLOWED, HTTP_405_METHOD_NOT_ALLOWED_URL);
    }

    static final String FORCE_TEST_HTTP_RESPONSE="FORCE_TEST_HTTP_RESPONSE";

    public static String thisRoute = null;

    public static final String RESPONSE_TYPE = "application/json";
    public static final String VERSION = "v1";

    public static final String PATH_BASE = "/" + VERSION;

    public static final String ID_PARAMETER = ":ID";
    public static final String COUNT = "count";

    static Map<String, String>nounPathMap = new HashMap<String,String>(10);

    static List<Service>services = new ArrayList<Service>();

    // NOUNS
    public static final String CONTAINER="container";
    public static final String LOCATION="location";
    public static final String MIXED_SPECIMEN="mixedSpecimen";
    public static final String PCR_PRIMER = "pcrPrimer";
    public static final String SAMPLE="sample";
    public static final String SPECIMEN_REPLICATE = "specimenReplicate";
    public static final String STORAGE="storage";

    //PATHS - make all services
    public static final String CONTAINER_PATH = makePath(CONTAINER, false);
    public static final String LOCATION_PATH = makePath(LOCATION, false);
    public static final String MIXED_SPECIMEN_PATH = makePath(MIXED_SPECIMEN, false);
    public static final String PCR_PRIMER_PATH = makePath(PCR_PRIMER, false);
    public static final String SAMPLE_PATH = makePath(SAMPLE, false);
    public static final String SPECIMEN_REPLICATE_PATH = makePath(SPECIMEN_REPLICATE);
    public static final String STORAGE_PATH = makePath(STORAGE, false);


    public static final String PAGING_OFFSET="offset";
    public static final String PAGING_LIMIT="limit";
    public static final int PAGING_LIMIT_LIMIT=500;

    Map <String, SpecimenReplicate> specimenReplicates = new HashMap <String, SpecimenReplicate>();
    
    public static void main(String[] args) {
	AndroidMockWS service = new AndroidMockWS();
	service.start();
    }
    
    public void stop(){

    }

    public void start(){
	populateReplicateData(specimenReplicates);


	before(new Filter() { // matches all routes
		@Override
		public void handle(Request request, Response response) {
		    String forceTestString = request.queryParams(FORCE_TEST_HTTP_RESPONSE);
		    System.err.println("filter");
		    if (forceTestString != null) {
			response.type(RESPONSE_TYPE);
			Gson gson = new Gson();
			int httpStatus = 400;
			String message=null;
			System.err.println("filter 1");
			try{
			    httpStatus = Integer.parseInt(forceTestString);
			    System.err.println("filter 2");
			}catch(NumberFormatException e){
			    System.err.println("filter 3");
			    httpStatus = 400;
			    message = FORCE_TEST_HTTP_RESPONSE + ": real error: " + forceTestString + ": Is not a supported"
				+ FORCE_TEST_HTTP_RESPONSE + " code.";
			}
			if(!HTTP_STATUS_URL_MAP.containsKey(httpStatus)){
			    System.err.println("filter 4");
			    httpStatus = 400;
			    message = forceTestString + ": Is not a supported force status code.";
			}else{
			    System.err.println("filter 5");
			    message = FORCE_TEST_HTTP_RESPONSE + ": response code=" + forceTestString;
			}
			System.err.println("filter 6");
			halt(httpStatus, error(request, httpStatus, message, gson));
		    }
		}
	    });

	/////////////
	// Base URL: lists all get URLs
	// 
	thisRoute = PATH_BASE;
	get(new Route(thisRoute) {
		@Override
		public Object handle(Request request, Response response) {
		    if(isForceTest(request)){
			return handleForceTest(request, response);
		    }
		    Gson gson = new Gson();
		    response.type(RESPONSE_TYPE);
		    // .. Show something ..
		    System.err.println("--*GET: " + request.pathInfo());

		    for(Service s: services){
			if(s.url == null){
			    s.url = request.scheme() + "://" + request.host() + s.path;
			}
		    }

		    List<Service>forceServices = new ArrayList<Service>();

		    for(Service s: services){
			for (Integer key : HTTP_STATUS_URL_MAP.keySet()){
			    Service force = new Service(s);
			    force.isWorking = true;
			    force.url = force.url + "?" + FORCE_TEST_HTTP_RESPONSE + "=" + key;
			    forceServices.add(force);
			}
		    }
		    services.addAll(forceServices);

		    return gson.toJson(services);

		}
	    });

	/////////////
	// Specimen Replicates
	// get count
	thisRoute = SPECIMEN_REPLICATE_PATH + "/" + COUNT + "/";
	get(new Route(thisRoute) {
		@Override
		public Object handle(Request request, Response response) {
		    response.type(RESPONSE_TYPE);

		    System.err.println("*GET: count" + request.pathInfo());

		    return "{ "
			+ "\"type\": \"specimenReplicates\""
			+ ",\n"
			+ "\"count\": " + specimenReplicates.size()
			+ "}";
		}
	    });

	// get all
	thisRoute = SPECIMEN_REPLICATE_PATH;
	get(new Route(thisRoute) {
		@Override
		public Object handle(Request request, Response response) {
		    response.type(RESPONSE_TYPE);
		    Gson gson = new Gson();

		    System.err.println("*GET: " + request.pathInfo());
		    StringBuilder sb = new StringBuilder();
		    final int size = specimenReplicates.size();
		    
		    int offset = 0;
		    int limit = 100;

		    String pagingOffset = request.queryParams(PAGING_OFFSET);
		    if(pagingOffset != null){
			if(! isInteger(pagingOffset)) {
			    response.status(HTTP_400_BAD_REQUEST);
			    return error(HTTP_400_BAD_REQUEST, PAGING_OFFSET + ": Not an integer: " + pagingOffset, gson);
			}
			offset = Integer.parseInt(pagingOffset);
		    }
		    String pagingLimit = request.queryParams(PAGING_LIMIT);
		    if(pagingLimit != null){
			if(! isInteger(pagingLimit)) {
			    response.status(HTTP_400_BAD_REQUEST);
			    return error(HTTP_400_BAD_REQUEST, PAGING_LIMIT + ": Not an integer: " + pagingLimit, gson);
			}
			limit = Integer.parseInt(pagingLimit);
		    }

		    if(offset < 0 || limit < 1){
			response.status(HTTP_405_METHOD_NOT_ALLOWED);
			return error(HTTP_405_METHOD_NOT_ALLOWED, PAGING_OFFSET + " < 0 or " + PAGING_LIMIT + " < 1", gson);
		    }

		    if(limit >PAGING_LIMIT_LIMIT){
			response.status(HTTP_405_METHOD_NOT_ALLOWED);
			return error(HTTP_405_METHOD_NOT_ALLOWED, PAGING_OFFSET + " > MAX=" + PAGING_LIMIT_LIMIT, gson);
		    }

		    String baseUrl = request.scheme() + "://" + request.host() + request.pathInfo();
		    int previous = offset-limit;
		    String[] uris = null;
		    if(offset < size){
			if(offset+limit > size){
			    limit = size - offset;
			}
			uris = new String[limit];
			int i=0;
			int end = limit+offset;
			for (String key : specimenReplicates.keySet()) {
			    if(i > end){
				break;
			    }
			    if(i >= offset && i < offset + limit){
				uris[i-offset] = makeUrl(request, SPECIMEN_REPLICATE_PATH) + "/" + key;
			    }
			    ++i;
			}
			if(previous < 0){
			    previous = 0;
			}
		    }else{
			previous = size - limit;
		    }
		    
		    ListPage listPage  = new ListPage(size, offset, limit, SPECIMEN_REPLICATE, uris, 
						  makePreviousUrl(size, offset, limit, baseUrl),
						  makeNextUrl(size, offset, limit, baseUrl));
		    return gson.toJson(listPage);
		}
	    });

	// get by ID
	thisRoute = SPECIMEN_REPLICATE_PATH + "/" + ID_PARAMETER;
	get(new Route(thisRoute) {
		@Override
		public Object handle(Request request, Response response) {
		    response.type(RESPONSE_TYPE);
		    // .. Show something ..
		    Gson gson = new Gson();
		    String id = request.params(ID_PARAMETER);
		    System.err.println("*GET: " + request.pathInfo() + "   id=" + id);
		    
		    StringBuilder errorString = new StringBuilder();
		    MutableInt errorCode = new MutableInt(HTTP_200_OK);
		    if(!integerAndFound(request, ID_PARAMETER, specimenReplicates, errorCode, errorString)){
			response.status(HTTP_400_BAD_REQUEST);
			return error(request, HTTP_400_BAD_REQUEST, errorString.toString(), gson);
		    }

		    if(! isInteger(id)) {
			response.status(HTTP_400_BAD_REQUEST);
			return error(request, HTTP_400_BAD_REQUEST, "Not an integer: " + id, gson);
		    }else{
			if(specimenReplicates.containsKey(id)){
			    response.status(errorCode.value);
			    SpecimenReplicate sr = specimenReplicates.get(id);
			    sr.makeParent(makeUrl(request, SPECIMEN_REPLICATE_PATH));
			    return gson.toJson(sr);
			}else{
			    response.status(HTTP_404_NOT_FOUND);
			    return error(HTTP_404_NOT_FOUND, "Not found: " + id, gson); 
			}
		    }
		}
	    });
       
	thisRoute = SPECIMEN_REPLICATE_PATH;
	post(new Route(SPECIMEN_REPLICATE_PATH) {
		@Override
		public Object handle(Request request, Response response) {
		    response.type(RESPONSE_TYPE);
		    // .. Create something .. 
		    return SPECIMEN_REPLICATE_PATH + "888";
		}
	    });
       
	thisRoute = SPECIMEN_REPLICATE_PATH + "/" + ID_PARAMETER;
	put(new Route(SPECIMEN_REPLICATE_PATH) {
		@Override
		public Object handle(Request request, Response response) {
		    // .. Update something ..
		    return SPECIMEN_REPLICATE_PATH + "333";
		}
	    });
       
	thisRoute = SPECIMEN_REPLICATE_PATH + "/" + ID_PARAMETER;
	delete(new Route(SPECIMEN_REPLICATE_PATH) {
		@Override
		public Object handle(Request request, Response response) {
		    // .. annihilate something ..
		    //if(!integerAndFound(request, SPECIMEN_REPLICATE_ID, specimenReplicates, errorString)){
		    //response.status(400);
		    //return error(errorString.toString(), gson);
		    //}
		    return SPECIMEN_REPLICATE_PATH+ "999";
		}
	    });


    // 	/////////////
    // 	// Specimen Replicates
    // 	get(new Route("/" + VERSION) {
    // 		@Override
    // 		public Object handle(Request request, Response response) {
    // 		    // .. Show something ..
    // 		    return SPECIMEN_REPLICATE_PATH + "444";
    // 		}
    // 	    });
       
    // 	post(new Route("/ + VERSION") {
    // 		@Override
    // 		public Object handle(Request request, Response response) {
    // 		    // .. Create something .. 
    // 		    return SPECIMEN_REPLICATE_PATH + "11111";
    // 		}
    // 	    });
       
    // 	put(new Route("/ + VERSION") {
    // 		@Override
    // 		public Object handle(Request request, Response response) {
    // 		    // .. Update something ..
    // 		    return SPECIMEN_REPLICATE_PATH + "9898";
    // 		}
    // 	    });
       
    // 	delete(new Route("/" + VERSION) {
    // 		@Override
    // 		public Object handle(Request request, Response response) {
    // 		    // .. annihilate something ..
    // 		    return SPECIMEN_REPLICATE_PATH + "4333";
    // 		}
    // 	    });
    }

    ////////////////////////////////////////////////
    public static boolean isInteger(String s) {
	if (s == null){
	    return false;
	}
	try { 
	    Integer.parseInt(s); 
	} catch(NumberFormatException e) { 
	    return false; 
	}
	// only got here if we didn't return false
	return true;
    }
    
    static final Random r = new Random();
    private static void populateReplicateData(final Map <String, SpecimenReplicate> sReplicates){
	if (sReplicates == null){
	    throw new NullPointerException();
	}
	// SpecimenReplicate
	for(int i=1; i<(10000 + r.nextInt(1000)); i++){
	    SpecimenReplicate sr = new SpecimenReplicate();
	    sReplicates.put(Integer.toString(sr.primaryKey), sr);
	}
	
	List<String> keysAsArray = new ArrayList<String>(sReplicates.keySet());
	for (String key: keysAsArray){
	    SpecimenReplicate sr = sReplicates.get(key);
	    if(r.nextInt(100)>80){
		sr.parentId = keysAsArray.get(r.nextInt(keysAsArray.size()));
	    }
	}

    }

    public final static String jsonWrap(final String s){
	return "{\n" + s + "\n}";
    }


    private static final String makePreviousUrl(final int size, final int offset, int limit, final String baseUrl){
	int prevOffset = offset - limit;
	if(prevOffset > size | prevOffset < 0){
	    return null;
	}
	if(prevOffset + limit > size){
	    limit = size -prevOffset;
	}
	return baseUrl + "?" + PAGING_OFFSET+ "=" + prevOffset + "&" + PAGING_LIMIT + "=" + limit;
    }

    private static final String makeNextUrl(final int size, final int offset, final int limit, final String baseUrl){
	int nextOffset = offset + limit;
	if(nextOffset > size){
	    return null;
	}
	return baseUrl + "?" + PAGING_OFFSET+ "=" + nextOffset + "&" + PAGING_LIMIT + "=" + limit;
    }

    private static final String error(final Request request, final int http_status_code, final String errorMessage, final Gson gson){
	String http_status_url = HTTP_STATUS_URL_MAP.get(http_status_code);
	//System.err.println(request.contentType());
	Error e = new Error(//request.contentType(), 
			    null,
			    request.url(), 
			    request.ip(), 
			    makeHeaders(request), 
			    request.requestMethod(), 
			    http_status_code, 
			    http_status_url, 
			    errorMessage);
	return gson.toJson(e);
    }
    private static final String error(final int http_status_code, final String errorMessage, final Gson gson){
	return error(null, http_status_code, errorMessage, gson);
    }

    private static boolean integerAndFound(Request request, String paramKey, Map <String, SpecimenReplicate> repMap, MutableInt errorCode, StringBuilder errorString){
	System.err.println("Looking up paramKey: " + paramKey);
	String param = request.params(paramKey);
	if(param == null){
	    errorString.append(paramKey + " is null");
	    System.err.println(paramKey + " is null");
	    errorCode.value = HTTP_400_BAD_REQUEST;
	}else{
	    if(!isInteger(param)){
		errorString.append(paramKey + "=" + param + ";  is not an integer");
		errorCode.value = HTTP_400_BAD_REQUEST;
	    }else{
		if(!repMap.containsKey(param)){
		    errorString.append(paramKey + " is not found");
		    errorCode.value = HTTP_400_BAD_REQUEST;
		}else{
		    return true;
		}
	    }
	}
	return false;
    }

    public String makeUrl(Request request, String path){
	return request.scheme() + "://" + request.host() + path;
    }

    static final String makeCountPath(final String path){
	return path + "/" + COUNT + "/";
    }

    static final String makePath(final String suffix){
	return makePath(suffix, true);
    }

    static final String makePath(final String suffix, final boolean isWorking){
	String path = PATH_BASE + "/" + suffix;
	nounPathMap.put(suffix, path);
	Service service = new Service(suffix, path, isWorking);
	services.add(service);

	//counter url
	service = new Service(suffix + " count", makeCountPath(path), isWorking);
	services.add(service);
	return path;
    }


    static final boolean isForceTest(Request request){
	return false;
    }

    static final Object handleForceTest(Request request, Response response) {
	return "hello ,,,,,";
    }

    static final Map<String, String> makeHeaders(final Request request){
	Map m = new HashMap<String, String>();
	Set<String>headers = request.headers();
	for(String h: headers){
	    m.put(h, request.headers(h));
	}
	return m;
    }

    
    // static final Map<String, String> makeQueryParams(final Request request){
    // 	Map m = new HashMap<String, String>();
    // 	Set<String>queryParams = request.headers();
    // 	for(String h: headers){
    // 	    m.put(h, request.headers(h));
    // 	}
    // 	return m;
    // }

}//
