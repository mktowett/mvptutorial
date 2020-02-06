package com.mktowett.majiapp.model;

public class TenantModel {
    String firstname;
    String surname;
    String phone;
    String meterreading;
    String userID;
    String meterNumber;


    public TenantModel(String userID, String firstname, String surname, String phone, String meterreading,String meternumber) {
        this.firstname = firstname;
        this.surname = surname;
        this.phone = phone;
        this.meterreading = meterreading;
        this.userID = userID;
        this.meterNumber = meternumber;
    }


    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMeterreading() {
        return meterreading;
    }

    public void setMeterreading(String meterreading) {
        this.meterreading = meterreading;
    }

    public String getMeterNumber() {
        return meterNumber;
    }

    public void setMeterNumber(String meterNumber) {
        this.meterNumber = meterNumber;
    }
}
