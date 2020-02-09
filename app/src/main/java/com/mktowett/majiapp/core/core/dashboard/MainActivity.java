package com.mktowett.majiapp.core.core.dashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mktowett.majiapp.R;
import com.mktowett.majiapp.model.TenantModel;
import com.mktowett.majiapp.utils.Sessions;
import com.mktowett.majiapp.utils.StringUtilities;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity implements MainActivityContract.MainActivityView {
    @BindView(R.id.spTenants)
    SearchableSpinner spTenants;
    @BindView(R.id.etNames)
    EditText etNames;
    @BindView(R.id.etMeterReading)
    EditText etMeterReading;
    @BindView(R.id.etCurrentReading)
    EditText etCurrentReading;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;


    Sessions sessions;
    public SweetAlertDialog pDialog;
    private MainActivityContract.Presenter presenter;

    List<TenantModel>tenantModelList = new ArrayList<>();
    ArrayAdapter<String> tenantsListAdapter;

    List<String> tenantList = new ArrayList<String>();
    String tenantId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //initialize class session
        sessions = new Sessions(this);
        ButterKnife.bind(this);

        //init presenter
        presenter = new MainActivityPresenterImpl(this);

        //get tenants
        tenantModelList = sessions.getTenant();
        //Iterating over tenants array
        for (int i=0; i<tenantModelList.size();i++){
            TenantModel tenant = tenantModelList.get(i);
            //set name in spinner
            tenantList.add(tenant.getMeterNumber()+"-"+tenant.getFirstname()+" "+tenant.getSurname());
        }

        //set list of tenants in adapter
        tenantsListAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,tenantList);
        //populate spinner with tenants details i.e meter number
        spTenants.setAdapter(tenantsListAdapter);

        //set spinner onClick listener
        spTenants.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //populate fields with tenant details
                TenantModel tenant = tenantModelList.get(position);
                etNames.setText(tenant.getFirstname());
                etMeterReading.setText(tenant.getMeterreading());
                tenantId = tenant.getUserID();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //onclick submit button
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFormValidation();
            }
        });


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

    @Override
    public void showSuccessMessage(String successMsg) {
        new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Good job!")
                .setContentText(successMsg)
                .show();
    }

    @Override
    public void showErrorMessage(String errorMsg) {
        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("Something went wrong!")
                .setContentText(errorMsg)
                .show();
    }

    @Override
    public void onFormValidation() {
        if(StringUtilities.checkFilledEditText(etCurrentReading,"This is required")){
            presenter.onSubmitForm(sessions.getUserId(),tenantId,etCurrentReading.getText().toString(),sessions.getAdminId(),sessions);
            Log.d("sending_data","tenant: "+tenantId+" ||userId: "+sessions.getUserId()+" ||reading: "+etCurrentReading+"adminID: "+sessions.getAdminId());
        }
    }
}
