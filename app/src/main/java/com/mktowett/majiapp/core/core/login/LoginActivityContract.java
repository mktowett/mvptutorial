package com.mktowett.majiapp.core.core.login;

import com.mktowett.majiapp.utils.Sessions;

import org.json.JSONObject;

public class LoginActivityContract {

    interface LoginPresenter{
        void onInvokeLogin(String username, String password, Sessions sessions);
    }

    interface LoginMainView{

        void showValidationErrorMsg();
        void onSuccessfulLogin();
        void onFailedLogin(String msg);
        void showProgress();
        void dismissProgress();
        void loadTenantsSpinner();

    }

    //Intractor are used to fetch from database,web  service and other data source
    interface GetTenantsIntractor{

        interface OnFinishedListener{
            void onFinsidhed(JSONObject jsonObject);
            void onFailuer(Throwable t);
        }

        void getTenantsArrayList();
    }
}
