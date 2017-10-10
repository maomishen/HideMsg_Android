package com.maomishen.hidemessage;

import android.content.Context;
import android.telephony.SmsManager;

import java.io.IOException;
import java.util.Date;

import io.realm.Realm;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by lunagao on 2017/8/21.
 * Project for HideMessage.
 */

public class SendMessage {

    Realm realm;

    public void buttonSendMessage(String phoneNumber, Context context, int message_server_id) {
        if (realm == null) {
            realm = Realm.getDefaultInstance();
        }
        realm.beginTransaction();
        SMS sms = realm.createObject(SMS.class);
        sms.setSendId(java.util.UUID.randomUUID().toString());
        sms.setSended(false);
        sms.setCreateTime(new Date());
        sms.setPhoneNumber(phoneNumber);
        sms.setDelivered(false);
        sms.setRequestTime(new Date());
        sms.setRequestAddress("phone");
        if (message_server_id != -1) {
            sms.setServer_message_id(message_server_id);
        }
        realm.commitTransaction();
        sendMessage(sms, context);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            PermissionHelper ph = new PermissionHelper(context);
//            if (ph.canUseSMS()) {
//                sendMessage();
//            }
//        } else {
//            sendMessage();
//        }
    }

    private void sendMessage(SMS sms, Context context) {
        PendingIntentCreater pic = new PendingIntentCreater(context);
        pic.initPendingIntent(sms.getSendId());
        try {
            SmsManager.getDefault().sendDataMessage(sms.getPhoneNumber(), "", (short) 1025, new byte[1], pic.sendPendingIntent, pic.deliveredPendingIntent);
            if (sms.getServer_message_id() == -1) {
                return;
            }
            try {
                run(WebAddressHelper.Address + "SmsGet/getState?code=56ftugybnj&password=rtyuknbk&msgId="+ sms.getServer_message_id() + "&state=1");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (SecurityException e) {
            realm.beginTransaction();
            sms.setError(true);
            sms.setErrorMessage(e.getMessage());
            realm.commitTransaction();
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

//    /**
//     * Callback received when a permissions request has been completed.
//     */
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
//                                           @NonNull int[] grantResults) {
//
//        if (requestCode == PermissionHelper.REQUEST_SMS) {
//            if (PermissionHelper.verifyPermissions(grantResults)) {
//                sendMessage();
//            } else {
//                Toast.makeText(mActivity, "Can't send message", Toast.LENGTH_SHORT).show();
//                realm.beginTransaction();
//                sms.setError(true);
//                sms.setErrorMessage("Sms permissions were not granted.");
//                realm.commitTransaction();
//            }
//        } else {
//            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        }
//
//    }
}
