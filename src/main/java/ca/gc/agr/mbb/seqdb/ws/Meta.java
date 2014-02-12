package ca.gc.agr.mbb.seqdb.ws;

import ca.gc.agr.mbb.seqdb.ws.http.Main;
import ca.gc.agr.mbb.seqdb.ws.webservices.WSConstants;
import java.util.Calendar;

public class Meta{
    public String version;
    public Long ellapsedMillis;
    public String thisUrl;
    public String debugToggleUrl = Main.BASE_URI + WSConstants.BASEPATH + WSConstants.DEBUG_PATH;;
    public boolean debug = BaseWS.ALL_DEBUG;
    public String payloadType=null;
    public String mode="mock"; // real/mock
    public String timestamp = Calendar.getInstance().getTime().toString();
    public String errorString;
    public int status;

    public transient Long startMillis = System.currentTimeMillis();
}
