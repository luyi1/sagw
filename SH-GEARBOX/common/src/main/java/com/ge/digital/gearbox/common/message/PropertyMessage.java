package com.ge.digital.gearbox.common.message;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


public class PropertyMessage implements MessageResource {
    private static ResourceBundle resource;
    private static ResourceBundle resource_CN;
    private static ResourceBundle resource_EN;
    PropertyMessage() {							 
	        resource = ResourceBundle.getBundle("com.ge.digital.gearbox.common.message.exceptions");
	        resource_CN = ResourceBundle.getBundle("com.ge.digital.gearbox.common.message.exceptions",Locale.CHINA);
	        resource_EN = ResourceBundle.getBundle("com.ge.digital.gearbox.common.message.exceptions",Locale.US);
    	}
    PropertyMessage(Locale locale) {							 
        resource = ResourceBundle.getBundle("com.ge.digital.spo.commoni18n.message.exceptions",locale);
	}
    @Override
    public String getMessage(String msgId) {
    	ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    	String locale = "";
    	if(attributes!=null){
    		locale = (String)attributes.getAttribute("locale", RequestAttributes.SCOPE_REQUEST);
    	}
		if(locale==null || locale.trim().equals(""))
		{
			   return resource.getString(msgId);
		}else if(locale!=null && locale.trim().equals("EN"))
		{
			   return resource_EN.getString(msgId);
		}else if(locale!=null && locale.trim().equals("CN"))
		{
			   return resource_CN.getString(msgId);
		}
		  return resource.getString(msgId);
      
    }
    
	
    @Override
    public String getMessage(String msgId, String Parameter) {
    	ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    	String locale = "";
    	if(attributes!=null){
    		locale = (String)attributes.getAttribute("locale", RequestAttributes.SCOPE_REQUEST);
    	}
		if(locale==null || locale.trim().equals(""))
		{
			   return MessageFormat.format(resource.getString(msgId), Parameter);
		}else if(locale!=null && locale.trim().equals("EN"))
		{
			   return MessageFormat.format(resource_EN.getString(msgId), Parameter);
		}else if(locale!=null && locale.trim().equals("CN"))
		{
			   return MessageFormat.format(resource_CN.getString(msgId), Parameter);
		}
        return MessageFormat.format(resource_CN.getString(msgId), Parameter);
    }

    @Override
    public String getMessage(String msgId, String[] bindList) {
    	ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    	String locale = "";
    	if(attributes!=null){
    		locale = (String)attributes.getAttribute("locale", RequestAttributes.SCOPE_REQUEST);
    	}
		if(locale==null || locale.trim().equals(""))
		{
			return MessageFormat.format(resource.getString(msgId), (Object [])bindList);
		}else if(locale!=null && locale.trim().equals("EN"))
		{
			return MessageFormat.format(resource_EN.getString(msgId), (Object [])bindList);
		}else if(locale!=null && locale.trim().equals("CN"))
		{
			return MessageFormat.format(resource_CN.getString(msgId), (Object [])bindList);
		}
        return MessageFormat.format(resource.getString(msgId), (Object [])bindList);
    }
	@Override
	public void applyLocale(Locale locale) {
		
		 resource = ResourceBundle.getBundle("com.ge.digital.gearbox.common.message.exceptions",locale);	
	}



}