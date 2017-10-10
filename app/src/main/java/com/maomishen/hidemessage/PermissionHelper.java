package com.maomishen.hidemessage;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

/**
 * Created by lunagao on 2017/8/13.
 * Project for HideMessage.
 */

public class PermissionHelper {

    MainActivity mainActivity;
    public static final int REQUEST_SMS = 1;

    public PermissionHelper(MainActivity activity) {
        this.mainActivity = activity;
    }

    private static String[] PERMISSION_SMS = {
            Manifest.permission.SEND_SMS,
            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.READ_SMS,
            Manifest.permission.RECEIVE_WAP_PUSH,
            Manifest.permission.RECEIVE_MMS
    };

    public boolean canUseSMS() {
        if (ActivityCompat.checkSelfPermission(this.mainActivity, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this.mainActivity, Manifest.permission.RECEIVE_SMS)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this.mainActivity, Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this.mainActivity, Manifest.permission.RECEIVE_WAP_PUSH)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this.mainActivity, Manifest.permission.RECEIVE_MMS)
                != PackageManager.PERMISSION_GRANTED) {
            requestSmsPermissions();
            return false;
        } else {
            return true;
        }
    }

    private void requestSmsPermissions() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this.mainActivity,
                Manifest.permission.SEND_SMS)
                || ActivityCompat.shouldShowRequestPermissionRationale(this.mainActivity,
                Manifest.permission.RECEIVE_SMS)
                || ActivityCompat.shouldShowRequestPermissionRationale(this.mainActivity,
                Manifest.permission.READ_SMS)
                || ActivityCompat.shouldShowRequestPermissionRationale(this.mainActivity,
                Manifest.permission.RECEIVE_WAP_PUSH)
                || ActivityCompat.shouldShowRequestPermissionRationale(this.mainActivity,
                Manifest.permission.RECEIVE_MMS)) {
            ActivityCompat.requestPermissions(this.mainActivity, PERMISSION_SMS, REQUEST_SMS);
        } else {
            // Sms permissions have not been granted yet. Request them directly.
            ActivityCompat.requestPermissions(this.mainActivity, PERMISSION_SMS, REQUEST_SMS);
        }
    }

    public static boolean verifyPermissions(int[] grantResults) {
        // At least one result must be checked.
        if (grantResults.length < 1) {
            return false;
        }

        // Verify that each required permission has been granted, otherwise return false.
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }
}
