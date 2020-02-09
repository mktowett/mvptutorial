package com.mktowett.majiapp.core.core.login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mktowett.majiapp.R;
import com.mktowett.majiapp.core.core.dashboard.MainActivity;
import com.mktowett.majiapp.utils.Sessions;
import com.mktowett.majiapp.utils.StringUtilities;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

//implement MainView
public class LoginActivity extends AppCompatActivity implements LoginActivityContract.LoginMainView {

    //declare and init view
    @BindView(R.id.etUsername) EditText etUsername;
    @BindView(R.id.etPassword) EditText etPassword;
    @BindView(R.id.btnLogin) Button btnLogin;

    public SweetAlertDialog pDialog;
    public ProgressBar progressBar;
    private Sessions sessions;
    private LoginActivityContract.LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        //create object of LoginPresenterImpl
        presenter = new LoginPreseneterImpl(this);
        sessions = new Sessions(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               showValidationErrorMsg();
            }
        });

        etUsername.setText("itadmin");
        etPassword.setText("itadmin");
    }

    @Override
    public void showValidationErrorMsg() {
        if(StringUtilities.checkFilledEditText(etUsername,"this is required") &&
        StringUtilities.checkFilledEditText(etPassword,"this is required")){
            presenter.onInvokeLogin(etUsername.getText().toString(),etPassword.getText().toString(),sessions);
            //showProgress();
        }
    }

    @Override
    public void onSuccessfulLogin() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onFailedLogin(String msg) {
        //Toast.makeText(this, " "+msg, Toast.LENGTH_SHORT).show();
        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("Something went wrong!")
                .setContentText(msg)
                .show();
    }


    @Override
    public void showProgress() {
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    public void dismissProgress() {
        if (pDialog != null){
            pDialog.dismiss();
            pDialog = null;
        }
    }
}
