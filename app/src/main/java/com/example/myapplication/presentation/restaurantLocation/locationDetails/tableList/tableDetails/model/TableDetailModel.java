package com.example.myapplication.presentation.restaurantLocation.locationDetails.tableList.tableDetails.model;

import android.net.Uri;

public class TableDetailModel {
    private final String number;
    private final Uri qrCode;

    public TableDetailModel(String number, Uri qrCode) {
        this.number = number;
        this.qrCode = qrCode;
    }

    public String getNumber() {
        return number;
    }

    public Uri getQrCode() {
        return qrCode;
    }
}
