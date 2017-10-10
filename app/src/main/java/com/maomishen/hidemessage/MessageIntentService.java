package com.maomishen.hidemessage;

import android.content.Context;
import android.util.Log;

import com.igexin.sdk.GTIntentService;
import com.igexin.sdk.message.GTCmdMessage;
import com.igexin.sdk.message.GTTransmitMessage;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by lunagao on 2017/8/20.
 * Project for HideMessage.
 */

public class MessageIntentService extends GTIntentService {

    public MessageIntentService() {
    }

    @Override
    public void onReceiveServicePid(Context context, int pid) {
    }

    @Override
    public void onReceiveMessageData(Context context, GTTransmitMessage msg) {
        String message = new String(msg.getPayload());
        Log.e(TAG, "onReceiveMessageData->" + message);
        String[] messageData = message.split(",");
        SendMessage sendMessage = new SendMessage();
        sendMessage.buttonSendMessage(messageData[0], getApplicationContext(), Integer.parseInt(messageData[1]));
        try {
            run(WebAddressHelper.Address + "SmsGet/getState?code=56ftugybnj&password=rtyuknbk&msgId="+ messageData[1] + "&state=0");
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

    @Override
    public void onReceiveClientId(Context context, String clientid) {
        Log.e(TAG, "onReceiveClientId -> " + "clientid = " + clientid);
    }

    @Override
    public void onReceiveOnlineState(Context context, boolean online) {
    }

    @Override
    public void onReceiveCommandResult(Context context, GTCmdMessage cmdMessage) {
    }
}