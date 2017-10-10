package com.maomishen.hidemessage;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.igexin.sdk.PushManager;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RegisterServerActivity extends Activity {

    OkHttpClient client = new OkHttpClient();
    Activity mActivity;
    String clientId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_server);

        clientId = PushManager.getInstance().getClientid(this);

        if (clientId == null || clientId == "") {
            findViewById(R.id.button_register_to_server).setEnabled(false);
        } else {
            findViewById(R.id.button_register_to_server).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        run(WebAddressHelper.Address + "DevicesSgin/Sgin?SginCode=luna&password=12345ssdlh&clientId="+ clientId);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        mActivity = this;
    }

    private void run(String url) throws IOException {
        final Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(mActivity, e.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String rString;
                try {
                    rString = response.body().string();
                } catch (Exception ex) {
                    showToast("UnKnow Error");
                    return;
                }
                showToast(rString);
            }
        });
    }

    private void showToast(String value) {
        Log.e("error", value);
    }
}
