package ca.gc.agr.mbb.seqdb.ws.payload;

import java.sql.Timestamp;

public class ContainerType {
    public String name = "t2";
    public String baseType = "t6";
    public Integer numberOfWells = 10;
    public Integer numberOfColumns = 8;
    public Integer numberOfRows = 9;
    public Integer id = 42;
    public Timestamp lastModified;
}
