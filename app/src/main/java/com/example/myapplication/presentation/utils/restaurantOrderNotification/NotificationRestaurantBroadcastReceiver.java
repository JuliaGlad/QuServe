package com.example.myapplication.presentation.utils.restaurantOrderNotification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificationRestaurantBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            Intent serviceIntent = new Intent(context, NotificationRestaurantForegroundService.class);
            context.startService(serviceIntent);
        }
    }
}
