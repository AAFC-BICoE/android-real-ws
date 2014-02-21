package ca.gc.agr.mbb.seqdb.ws.webservices;


public interface WSConstants{
    public static final String BASEPATH="seqdb-ws/v1/";

    public static final String COUNT_PATH="/count";
    public static final String ID="id";
    public static final String ID_PARAM="/{" + ID + ": \\d+}";
    //public static final String ID_PARAM="/" + ID;

    public static final String DEBUG_PATH = "DEBUG";

    public static final String DEFAULT_PAGING_OFFSET_STRING = "0";

    public static final int DEFAULT_PAGING_LIMIT = 100;
    public static final String DEFAULT_PAGING_LIMIT_STRING = "100";

    public static final int MAX_PAGING_LIMIT = DEFAULT_PAGING_LIMIT * 5;

    // Parameter names
    public static final String PAGING_OFFSET_PARAMETER = "offset";
    public static final String PAGING_LIMIT_PARAMETER = "limit";
    
    // http headers
    public static String CONTENT_LOCATION = "Content-Location";

}
