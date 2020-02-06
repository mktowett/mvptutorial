package com.mktowett.majiapp.core.core.dashboard;

import com.mktowett.majiapp.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Response;

public class SubmitFormIntractor implements MainActivityContract.SubmitFormInteractor.OnFinishListener{

    @Override
    public void onFinished(Response<String> response) {
        try{
        JSONObject obj = new JSONObject(response.body());
        String respcode = obj.optString(Constants.KEY_RESPCODE);
        String respdesc = obj.optString(Constants.KEY_RESPDESC);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(Throwable t) {

    }
}
