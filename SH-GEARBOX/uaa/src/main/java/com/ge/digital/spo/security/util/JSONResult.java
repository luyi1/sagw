package com.ge.digital.spo.security.util;

import org.json.JSONObject;

public class JSONResult{
    public static String fillResultString(String status, String message, Object result,Boolean body){
        JSONObject jsonObject = new JSONObject();
        
        jsonObject.put("errCode", status);
        jsonObject.put("errMsg", message);
        jsonObject.put("token", result);
        jsonObject.put("body", body);
        return jsonObject.toString();
    }
    public static String fillResultString(String status, String message, Object result){
        JSONObject jsonObject = new JSONObject();
 
        return fillResultString( status,  message,  result,false);
    }
}
