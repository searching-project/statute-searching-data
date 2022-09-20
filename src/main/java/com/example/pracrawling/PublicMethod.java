package com.example.pracrawling;

import org.json.JSONArray;
import org.json.JSONObject;

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
}
