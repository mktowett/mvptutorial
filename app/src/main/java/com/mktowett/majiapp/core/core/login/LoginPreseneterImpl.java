package com.mktowett.majiapp.core.core.login;

import android.text.TextUtils;
import android.util.Log;

import com.mktowett.majiapp.network.ApiClientString;
import com.mktowett.majiapp.network.ApiInterface;
import com.mktowett.majiapp.utils.Constants;
import com.mktowett.majiapp.utils.Sessions;

import org.json.JSONException;
import org.json.JSONObject;

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
       /* if (TextUtils.isEmpty(username)&&TextUtils.isEmpty(password)){
            //user methods in contract
            loginMainView.showValidationErrorMsg();
        }else if(username.equals("user")&&password.equals("pass")){
            loginMainView.onSuccessfulLogin();
        }else {
            loginMainView.onFailedLogin();
        }*/
       loginMainView.showProgress();
       //make api call to server
        ApiInterface apiInterface = ApiClientString.getClient().create(ApiInterface.class);
        Call<String> call = apiInterface.loginApp(username, password);
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
                        Log.d("dataResponse","res"+response.body());
                        sessions.setUserId(obj.optJSONObject("user_data").optString("USER_ID"));
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
