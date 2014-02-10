package ca.gc.agr.mbb.seqdb.ws;

import java.util.List;
import java.util.ArrayList;

public class PagingPayload extends CountPayload{
    public Integer limit = 100;
    public String baseUrl;
    public Integer offset = 0;
    public String nextPageurl = "http://default_next_url.com";

    public List<ServiceUrl> urls = new ArrayList<ServiceUrl>(100);

    public PagingPayload(final Integer total, final Integer offset, final Integer limit){
	this(total);
	this.offset = offset;
	this.limit = limit;
    }

    public PagingPayload(final Integer total){
	super(total);
    }

    public void addUrl(final String path){
	this.urls.add(new ServiceUrl(baseUrl, path));
    }

}
