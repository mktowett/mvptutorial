package com.mktowett.majiapp.core.core.dashboard;

import com.mktowett.majiapp.utils.Sessions;

import org.json.JSONObject;

import retrofit2.Response;

public class MainActivityContract {

    interface Presenter{

        void onSubmitForm(String id, String userId, String meterReading, String adminId, Sessions sessions);
    }

    interface MainActivityView{
        void showProgress();
        void dismissProgress();
        void showSuccessMessage(String successMsg);
        void showErrorMessage(String errorMsg);
        void onFormValidation();

    }

    interface SubmitFormInteractor{
        interface OnFinishListener{
            void onFinished(Response<String> response);
            void onFailure(Throwable t);
        }

    }
}
