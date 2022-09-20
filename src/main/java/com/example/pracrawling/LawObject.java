package com.example.pracrawling;

import org.json.JSONObject;

public interface LawObject <T>{
    T update(JSONObject laws);
}
