package ca.gc.agr.mbb.seqdb.ws.payload;

import ca.gc.agr.mbb.seqdb.ws.Payload;

public class ContainerType extends BasePayload{
    public String name = null;
    public String baseType = null;
    public Integer numberOfColumns = null;
    public Integer numberOfRows = null;
    public Integer numberOfWells = null;

    public String lastModified;

}
