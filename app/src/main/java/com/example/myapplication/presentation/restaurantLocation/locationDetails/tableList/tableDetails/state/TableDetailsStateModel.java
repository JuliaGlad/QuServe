package com.example.myapplication.presentation.restaurantLocation.locationDetails.tableList.tableDetails.state;

import android.net.Uri;

public class TableDetailsStateModel {
    private final String number;
    private final String orderId;
    private final Uri qrCode;

    public TableDetailsStateModel(String number, String orderId, Uri qrCode) {
        this.number = number;
        this.orderId = orderId;
        this.qrCode = qrCode;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getNumber() {
        return number;
    }

    public Uri getQrCode() {
        return qrCode;
    }
}
