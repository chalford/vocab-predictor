package uk.co.bbc.team12;

import java.util.Map;

public class APIGatewayResponse {
	
	private int statusCode;
	private Map<String, String> headers;
	private Object body;
	
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public Map<String, String> getHeaders() {
		return headers;
	}
	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}
	public Object getBody() {
		return body;
	}
	public void setBody(Object body) {
		this.body = body;
	}

}
