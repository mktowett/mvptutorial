package com.mktowett.majiapp.model;

public class ClientModel {
    String firstname;
    String surname;
    String phone;
    String meterreading;
    String userID;


    public ClientModel(String userID,String firstname, String surname, String phone, String meterreading) {
        this.firstname = firstname;
        this.surname = surname;
        this.phone = phone;
        this.meterreading = meterreading;
        this.userID = userID;
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

}
