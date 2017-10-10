package com.maomishen.hidemessage;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Date;

/**
 * Created by lunagao on 2017/8/13.
 * Project for HideMessage.
 */

public class PendingIntentCreater {
    private String mSendString = "SMS_SENT";
    private String mdeliveredString = "SMS_DELIVERED";
    PendingIntent sendPendingIntent;
    PendingIntent deliveredPendingIntent;
    private Context mContent;

    public PendingIntentCreater(Context context) {
        this.mContent = context;
    }

    public void initPendingIntent(String sendId) {
        int requestCode = (int) new Date().getTime();
        Intent sendIntent = new Intent(this.mSendString);
        sendIntent.putExtra("sendId", sendId);
        this.sendPendingIntent = PendingIntent.getBroadcast(this.mContent, requestCode, sendIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Intent deliveredIntent = new Intent(this.mdeliveredString);
        deliveredIntent.putExtra("sendId", sendId);
        this.deliveredPendingIntent = PendingIntent.getBroadcast(this.mContent, requestCode, deliveredIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
