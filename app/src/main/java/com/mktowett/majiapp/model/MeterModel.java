package com.mktowett.majiapp.model;

public class MeterModel {

    String userId;
    String tenantId;
    String meterReading;

    //create  constructer of the model
    public MeterModel(String userId, String tenantId, String meterReading) {
        this.userId = userId;
        this.tenantId = tenantId;
        this.meterReading = meterReading;
    }

    //generate getters and setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getMeterReading() {
        return meterReading;
    }

    public void setMeterReading(String meterReading) {
        this.meterReading = meterReading;
    }
}
