package com.ge.digital.gearbox.common.email;

public class Mail {

	private  String[] toList;
	private  String[] ccList;
	private  String subject;
	private  String header;
	private  String body;
	private  String footer;
	public String[] getToList() {
		return toList;
	}
	public void setToList(String[] toList) {
		this.toList = toList;
	}
	public String[] getCcList() {
		return ccList;
	}
	public void setCcList(String[] ccList) {
		this.ccList = ccList;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getFooter() {
		return footer;
	}
	public void setFooter(String footer) {
		this.footer = footer;
	}
	
}
