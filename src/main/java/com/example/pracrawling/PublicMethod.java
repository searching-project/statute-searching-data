package com.example.pracrawling;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PublicMethod {

    public static JSONArray ObjectsToJSonArray(Object object){
        JSONArray objects = new JSONArray();
        if(object instanceof JSONObject){
            JSONObject jsonObject = (JSONObject) object;
            objects.put(jsonObject);
        }else if(object instanceof JSONArray){
            objects = (JSONArray) object;
        }else if(object instanceof String){
            objects.put(object);
        }
        return objects;
    }
    public static JSONObject ObjectsToJSonObject(Object object){
        JSONObject jsonObject = new JSONObject();
        if(object instanceof JSONObject){
            return (JSONObject) object;
        }else if(object instanceof String){
            jsonObject.put((String) object,object);
        }
        return jsonObject;
    }
    public static Object getOptional(Set<String> keys, String key, JSONObject object ){
        return keys.contains(key) ? object.get(key) : null;
    }

}
