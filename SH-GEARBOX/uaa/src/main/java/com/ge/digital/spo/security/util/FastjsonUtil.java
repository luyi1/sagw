package com.ge.digital.spo.security.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

/**
 * fastjson json高新能处理类  
 * @author jzj
 *2、主要的使用入口
 *
Fastjson API入口类是com.alibaba.fastjson.JSON，常用的序列化操作都可以在JSON类上的静态方法直接完成。
public static final Object parse(String text); // 把JSON文本parse为JSONObject或者JSONArray 
public static final JSONObject parseObject(String text)； // 把JSON文本parse成JSONObject    
public static final <T> T parseObject(String text, Class<T> clazz); // 把JSON文本parse为JavaBean 
public static final JSONArray parseArray(String text); // 把JSON文本parse成JSONArray 
public static final <T> List<T> parseArray(String text, Class<T> clazz); //把JSON文本parse成JavaBean集合 
public static final String toJSONString(Object object); // 将JavaBean序列化为JSON文本 
public static final String toJSONString(Object object, boolean prettyFormat); // 将JavaBean序列化为带格式的JSON文本 
public static final Object toJSON(Object javaObject); 将JavaBean转换为JSONObject或者JSONArray。
 
3、有关类库的一些说明
 
SerializeWriter：相当于StringBuffer
JSONArray：相当于List<Object>
JSONObject：相当于Map<String, Object>
 */
public class FastjsonUtil {/*
	*//**
	 * 任意类型转化为字json符串
	 * @param <P> 任意类型
	 * @param p
	 * @return
	 */
	public static<P> String getObjectToStr(P p ){
		return JSON.toJSONString(p);
	}
	/**
	 * json字符串转化成javabean对象,
	 * @param classname
	 * @param jsonStr
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static<P> P getStrToObject(Class classname,String jsonStr ){
		return (P) JSON.parseObject(jsonStr,classname);
	}
	/**
	 * json字符串转化成list对象,
	 * @param <P>
	 * @param classname
	 * @param jsonStr
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static<P> P getStrToListObject(Class classname,String jsonStr ){
		return (P) JSON.parseArray(jsonStr, classname);
	}
	/**
	 * json字符串转化成list<String>
	 * @param str
	 * @return
	 */
	public static List<String> getStrToListStr(String str ){
		return JSON.parseObject(str,new TypeReference<List<String>>(){});
	}
	/**
	 * json字符串转listMap 
	 * @param str
	 * @return
	 */
	public static List<Map<String,Object>> getStrToListMap(String str ){
		List<Map<String,Object>> list =
			JSON.parseObject(str,new TypeReference<List<Map<String,Object>>>(){});
		return list;
	}
	/**
	 * 判断是否是json
	 * @param str
	 * @return
	 */
	public static Boolean isJson(Object object ){
	    if(FastjsonUtil.isNullOrEmpty(object)){
	        return false;
	    }else if(object instanceof Integer){
	        return false;
	    }else if(object instanceof Short){
            return false;
	    }else if(object instanceof Float){
            return false;
	    }else if(object instanceof Double){
            return false;
	    }else if(object instanceof Boolean){
            return false;
        }else if(object instanceof Object[]){
            return false;
       }else if(object instanceof Collection){
            return false;
       }
	    //其他类别转化string验证
           String str = object.toString().trim();
           if(FastjsonUtil.isNullOrEmpty(str)){
               return false;
           }
           //验证整数和浮点数
           else if(str.matches("^[0-9]*$") || str.matches("^(-?\\d+)(\\.\\d+)?$")){
               return false;
           }
           try {
               JSON.parse(object.toString());
           } catch (Exception e) {
               return false;
           }
		return true;
	}
    /**
     * 判断对象或对象数组中每一个对象是否为空: 对象为null，字符序列长度为0，集合类、Map为empty
     * 
     * @param obj
     * @return
     */
    private static boolean isNullOrEmpty(Object obj)
    {
        if (obj == null)
            return true;

        if (obj instanceof CharSequence)
            return ((CharSequence) obj).length() == 0;

        if (obj instanceof Collection)
            return ((Collection) obj).isEmpty();

        if (obj instanceof Map)
            return ((Map) obj).isEmpty();

        if (obj instanceof Object[])
        {
            Object[] object = (Object[]) obj;
            if (object.length == 0)
            {
                return true;
            }
            boolean empty = true;
            for (int i = 0; i < object.length; i++)
            {
                if (!isNullOrEmpty(object[i]))
                {
                    empty = false;
                    break;
                }
            }
            return empty;
        }
        return false;
    }

}
