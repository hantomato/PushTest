package com.nmj.push.pushtest;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by neowiztomato on 2016-09-30.
 */
public class SecondReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context ctx, Intent intent) {
        android.util.Log.d("nmj77", "SecondReceiver.onReceive");

        String referrer = intent.getStringExtra("referrer");
        if (referrer == null || TextUtils.isEmpty(referrer)) {
            Log.d("nmj77", "referrer empty");
        }

        Log.d("nmj77", "SecondReceiver referrer : " + referrer);

    }
}
