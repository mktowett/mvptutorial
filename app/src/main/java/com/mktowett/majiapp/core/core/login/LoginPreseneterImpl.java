package com.mktowett.majiapp.core.core.login;

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

//implement Presenter
public class LoginPreseneterImpl implements LoginActivityContract.LoginPresenter {
    //declare loginview
    private LoginActivityContract.LoginMainView loginMainView;
    //contructor
    public LoginPreseneterImpl(LoginActivityContract.LoginMainView loginMainView){
        this.loginMainView = loginMainView;
    }

    //auto-gen method from Presenter
    @Override
    public void onInvokeLogin(String username, String password, Sessions sessions) {

       loginMainView.showProgress();
       //make api call to server
        ApiInterface apiInterface = ApiClientString.getClient().create(ApiInterface.class);
        Call<String> call = apiInterface.loginApp(username, password);
        Log.d("Resp: ", call.toString());
        //handle response
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                loginMainView.dismissProgress();
                try {
                    JSONObject obj = new JSONObject(response.body());
                    String respcode = obj.optString(Constants.KEY_RESPCODE);
                    String respdesc = obj.optString(Constants.KEY_RESPDESC);

                    if (respcode.equals("00")){
                        sessions.clearData(Constants.KEY_TENANTS
                        );
                        Log.d("dataResponse","res"+response.body());
                        sessions.setUserId(obj.optJSONObject("user_data").optString("USER_ID"));
                        sessions.setAdminId(obj.optJSONObject("user_data").optString("USER_CREATED_BY"));

                        //get and set tenants
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
                        loginMainView.onSuccessfulLogin();

                    }else {
                        loginMainView.onFailedLogin(respdesc);
                    }
                } catch (JSONException e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                loginMainView.dismissProgress();
                loginMainView.onFailedLogin(t.getMessage());
            }
        });
    }
}
