package com.maomishen.hidemessage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.igexin.sdk.PushManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.OrderedCollectionChangeSet;
import io.realm.OrderedRealmCollectionChangeListener;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;

public class MainActivity extends Activity {
    private ListView listView;
    SimpleAdapter adapter;
    MainActivity mActivity;
    Button button;
    Button button_clear;
    EditText editText;
    Realm realm;
    RealmResults<SMS> result;
    List<Map<String, String>> list;
    SMS sms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.editText);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(buttonClick);
        findViewById(R.id.button_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mActivity, RegisterServerActivity.class);
                startActivity(intent);
            }
        });
        listView = (ListView) findViewById(R.id.listView);
        mActivity = this;
        realm = Realm.getDefaultInstance();
        this.getData();
        adapter = new SimpleAdapter(this, list, R.layout.item_main,
                new String[]{"phoneNumber","deliveredTime","state"},
                new int[]{R.id.textView_phoneNumber,R.id.textView_deliveredTime,R.id.textView_isPowerOff});
        listView.setAdapter(adapter);
        button_clear = (Button) findViewById(R.id.button_clear);
        button_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (realm == null) {
                    realm = Realm.getDefaultInstance();
                }
                if (result == null) {
                    return;
                }
                button.setEnabled(false);
                realm.beginTransaction();
                result.deleteAllFromRealm();
                realm.commitTransaction();
                button.setEnabled(true);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        PushManager.getInstance().registerPushIntentService(this.getApplicationContext(), com.maomishen.hidemessage.MessageIntentService.class);
        PushManager.getInstance().initialize(this.getApplicationContext(), com.maomishen.hidemessage.MessagePushService.class);
        result.addChangeListener(callback);
    }

    @Override
    protected void onStop() {
        super.onStop();
        result.removeAllChangeListeners();
    }

    private void getData() {
        if (realm == null) {
            realm = Realm.getDefaultInstance();
        }
        if (list != null) {
            list.clear();
        } else {
            list = new ArrayList<>();
        }
        if (result == null){
            RealmQuery<SMS> query = realm.where(SMS.class);
            result = query.findAllSortedAsync("createTime", Sort.DESCENDING);
        }

        for (SMS sms : result) {
            Map<String, String> map = new HashMap<>();
            map.put("phoneNumber", sms.getPhoneNumber());
            if (sms.isError()) {
                map.put("state", "Error");
            } else if (sms.isDelivered()) {
                map.put("state", "Power On");
                map.put("deliveredTime", sms.getDeliveredTime().toString());
            } else if (sms.isSended()) {
                map.put("state", "Send");
            } else {
                map.put("state", "Unknow");
            }
            list.add(map);
        }
    }

    private OrderedRealmCollectionChangeListener<RealmResults<SMS>> callback = new OrderedRealmCollectionChangeListener<RealmResults<SMS>>() {
        @Override
        public void onChange(RealmResults<SMS> smses, OrderedCollectionChangeSet changeSet) {
            mActivity.getData();
            adapter.notifyDataSetChanged();
        }
    };

    View.OnClickListener buttonClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.buttonSendMessage(editText.getText().toString(), mActivity.getApplicationContext(), -1);
        }
    };
}
