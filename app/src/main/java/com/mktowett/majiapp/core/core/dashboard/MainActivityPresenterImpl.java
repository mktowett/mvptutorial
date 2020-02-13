package com.mktowett.majiapp.core.core.dashboard;

import android.util.Log;

import com.mktowett.majiapp.model.TenantModel;
import com.mktowett.majiapp.network.ApiClientString;
import com.mktowett.majiapp.network.ApiInterface;
import com.mktowett.majiapp.utils.Constants;
import com.mktowett.majiapp.utils.Sessions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityPresenterImpl implements MainActivityContract.Presenter {
    //declare mainView
    MainActivityContract.MainActivityView mainActivityView;

    //create constructor
    public MainActivityPresenterImpl(MainActivityContract.MainActivityView mainActivityView) {
        this.mainActivityView = mainActivityView;

    }

    @Override
    public void onSubmitForm(String id, String userId, String meterReading, String adminId, Sessions sessions) {
        mainActivityView.showProgress();
        ApiInterface apiInterface = ApiClientString.getClient().create(ApiInterface.class);
        Call<String> call = apiInterface.recordMeter(id,userId,meterReading,adminId);
        Log.d("pointerX","mark"+call);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d("callback","resp"+response);
                mainActivityView.dismissProgress();
                try{
                    JSONObject obj = new JSONObject(response.body());
                    String respcode = obj.optString(Constants.KEY_RESPCODE);
                    String respdesc = obj.optString(Constants.KEY_RESPDESC);

                    if (respcode.equals("00")){
                        sessions.clearData(Constants.KEY_TENANTS);
                        List<TenantModel> tenantModelList = new ArrayList<>();
                        JSONArray tenantJsonArray = obj.optJSONArray("tenants");
                        //Iterating over array
                        for  (int i = 0; i<tenantJsonArray.length(); i++){
                            JSONObject tenantObj = tenantJsonArray.optJSONObject(i);
                            TenantModel tenant =  new TenantModel(
                                    tenantObj.optString("USER_ID"),
                                    tenantObj.optString("FIRSTNAME"),
                                    tenantObj.optString("SURNAME"),
                                    tenantObj.optString("USER_PHONE"),
                                    tenantObj.optString("USER_METER"),
                                    tenantObj.optString("USER_METER_NUMBER"));

                            //add tenants to a list
                            tenantModelList.add(tenant);
                        }
                        //save list of tenants in sharePref
                        sessions.setTenant(tenantModelList);
                        mainActivityView.showSuccessMessage(respdesc);

                    }else {
                      mainActivityView.showErrorMessage(respdesc);
                    }

                }catch (JSONException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                mainActivityView.dismissProgress();
                mainActivityView.showErrorMessage(t.toString());
            }
        });
    }


}
