package com.cvs.crm.util;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

import java.io.FileReader;

@Service
@Data
@Slf4j
public class JsonUtil {

    public JSONObject currentDataJo = null;
    public JSONArray currentDataJsonArray = null;
    
    public JSONObject readJsonFileGetObject(String filePath, String jsonDataObjectName) {
        JSONObject toReturn = new JSONObject();
        try {
            FileReader fr = new FileReader(filePath);
            JSONParser js = new JSONParser();
            JSONObject jo = (JSONObject) js.parse(fr);

            toReturn = (JSONObject) jo.get(jsonDataObjectName);

        } catch (Exception e) {
            e.getMessage();
        }
        return toReturn;
    }

    public JSONArray readJsonFileGetArray(String filePath, String jsonDataObjectName) {

        try {
            FileReader fr = new FileReader(filePath);
            JSONParser js = new JSONParser();
            JSONObject jo = (JSONObject) js.parse(fr);

            currentDataJsonArray = (JSONArray) jo.get(jsonDataObjectName);

        } catch (Exception e) {
            e.getMessage();
        }
        return currentDataJsonArray;
    }

    public JSONObject readJsonFileGetObject(String filePath, String testEnvironment, String jsonDataObjectName) {
        JSONObject toReturn = new JSONObject();
        try {
            FileReader fr = new FileReader(filePath);
            JSONParser js = new JSONParser();
            JSONObject jo = (JSONObject) js.parse(fr);

            currentDataJo = (JSONObject) jo.get(testEnvironment);
            toReturn = (JSONObject) currentDataJo.get(jsonDataObjectName);

        } catch (Exception e) {
            e.getMessage();
        }
        return toReturn;
    }

    public JSONArray readJsonFileGetArray(String filePath, String testEnvironment, String jsonDataObjectName) {

        try {
            FileReader fr = new FileReader(filePath);
            JSONParser js = new JSONParser();
            JSONObject jo = (JSONObject) js.parse(fr);

            currentDataJo = (JSONObject) jo.get(testEnvironment);
            currentDataJsonArray = (JSONArray) currentDataJo.get(jsonDataObjectName);

        } catch (Exception e) {
            e.getMessage();
        }
        return currentDataJsonArray;
    }

}
