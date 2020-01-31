package com.mktowett.majiapp.http;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserFunctions {
    private final JSONParser jsonParser;
    private static String URL = "https://gatesystem.rongaiws.com/majimaji/";

    public UserFunctions() {
        jsonParser = new JSONParser();
    }

    public JSONObject GenericFunction(String[] Tags, String[] Values) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        int i = 0;
        for (int j = 0; j < Tags.length; j++) {
            params.add(new BasicNameValuePair(Tags[i], Values[j]));
            i++;
        }
        JSONObject json = jsonParser.getJSONFromUrl(URL, params);
        return json;
    }
}
