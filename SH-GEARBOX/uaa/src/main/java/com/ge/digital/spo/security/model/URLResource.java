package com.ge.digital.spo.security.model;

public class URLResource {
private String url;
public String getUrl() {
	return url;
}
public void setUrl(String url) {
	this.url = url;
}
public int getType() {
	return type;
}
public URLResource() {
super();
}
public URLResource(String url, int type) {
	super();
	this.url = url;
	this.type = type;
}
public void setType(int type) {
	this.type = type;
}
private int type;
}
