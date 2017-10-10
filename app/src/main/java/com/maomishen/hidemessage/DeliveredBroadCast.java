package com.maomishen.hidemessage;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.io.IOException;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmQuery;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by lunagao on 2017/8/13.
 * Project for HideMessage.
 */

public class DeliveredBroadCast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String sendId = intent.getExtras().getString("sendId");
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<SMS> query = realm.where(SMS.class);
        query.equalTo("sendId", sendId);
        SMS sms = query.findFirst();
        if (sms != null) {
            realm.beginTransaction();
            sms.setDelivered(true);
            sms.setDeliveredTime(new Date());
            realm.commitTransaction();
        }
        if (sms.getServer_message_id() == -1) {
            return;
        }
        try {
            run(WebAddressHelper.Address + "SmsGet/getState?code=56ftugybnj&password=rtyuknbk&msgId="+ sms.getServer_message_id() + "&state=3");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void run(String url) throws IOException {
        final Request request = new Request.Builder()
                .url(url)
                .build();
        OkHttpClient client = new OkHttpClient();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
            }
        });
    }

}
