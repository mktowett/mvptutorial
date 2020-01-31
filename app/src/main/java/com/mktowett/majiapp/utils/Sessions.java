package com.mktowett.majiapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.SyncStateContract;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class Sessions {
    public static SharedPreferences.Editor editor;
    // Shared Preferences
    public SharedPreferences pref;
    public String isLogin;

    //private static final String IS_LOGIN = "IsLoggedIn";
    // Context
    Context _context;


    public Sessions(Context _context) {
        this._context = _context;
        pref = _context.getSharedPreferences("rongaiprefs", Context.MODE_PRIVATE);
        editor = pref.edit();
        //isLogin = getUserId();
    }

    public static void set(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public void setBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    public boolean getBoolean(String key) {
        return pref.getBoolean(key, false);
    }

    /**
     * @param key
     * @param value
     */
    public void setString(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public void setStringArray(String key, List<String> list) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i)).append(",");
        }
        editor.putString(key, sb.toString());
        editor.commit();
    }

    public List<String> getStringArray(String key) {
        List<String> list = new ArrayList<>();
        String[] array = getString(key).split(",");
        int k = array.length;
        for (int i = 0; i < k; i++) {
            list.add(array[i]);
        }
        return list;
    }

    public String getString(String key) {
        return pref.getString(key, null);
    }

    public void setInt(String key, int i) {
        editor.putInt(key, i);
        editor.commit();
    }

    public int getInt(String key) {
        return pref.getInt(key, 0);

    }
    public void clearData(String key) {
        editor.remove(key);
    }

    //set drivers
    public <T> void setClients(List<T> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        set(Constants.KEY_CLIENTS, json);
    }

   /* //get trucks in list
    public List<ClientModel> getClients() {
        List<ClientModel> OutletModelList;
        String accountsListString = pref.getString(Constants.KEY_CLIENTS, null);
        Gson gson = new Gson();
        OutletModelList = gson.fromJson(accountsListString, new TypeToken<ArrayList<ClientModel>>() {
        }.getType());
        return OutletModelList;
    }*/


    //set userid
    public void setUserId(String id) {
        setString(Constants.KEY_USERID, id);
    }

    //get userid
    public String getUserId() {
        return getString(Constants.KEY_USERID);
    }

    //set clientId
    public void setClientId(String id) {
        setString(Constants.KEY_CLIENTID, id);
    }

    //get clientId
    public String getClientId() {
        return getString(Constants.KEY_CLIENTID);
    }
}
