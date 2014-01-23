package ca.gc.agr.mbb.androidmockws;

import java.util.Map;

public class Error{

    private String error;
    private int http_status_code;
    private String http_status_code_url;
    private String requestMethod;
    private String contentType;
    private String url;
    private String ip;
    private Map<String, String> headers;
    private Map<String, String> queryParams;

    public Error(final String contentType, final String url, final String ip,final Map<String, String>headers, 
		 final String requestMethod, final int http_status_code, final String http_status_code_url, final String error){
	this(http_status_code, http_status_code_url, error);
	this.headers = headers;
	this.requestMethod = requestMethod;
	this.url = url;
	this.ip = ip;
	this.contentType = contentType;
    }

    public Error(final int http_status_code, final String http_status_code_url, final String error){
	this.error = error;
	this.http_status_code = http_status_code;
	this.http_status_code_url = http_status_code_url;
    }
}
