package com.nmj.push.pushtest;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import java.util.HashMap;
import java.util.concurrent.Exchanger;

/**
 * Created by neowiztomato on 2016-09-27.
 */
public class InstallReferrerReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context ctx, Intent intent) {
        android.util.Log.d("nmj7", "InstallReferrerReceiver.onReceive");

        String referrer = intent.getStringExtra("referrer");
        if (referrer == null || TextUtils.isEmpty(referrer)) {
            Log.d("nmj7", "referrer empty");
        }

        Log.d("nmj7", "referrer : " + referrer);

        SharedPreferences pref = ctx.getSharedPreferences("nmj", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("REFERRER", referrer);
        editor.putLong("TIMESTAMP", System.currentTimeMillis());
        editor.commit();


        HashMap<String,String> anotherReceiver = getAnotherInstallReferrerReceiver(ctx,
                InstallReferrerReceiver.class);
        android.util.Log.d("nmj7", "anotherReceiver :" + anotherReceiver);
        for (String key : anotherReceiver.keySet()) {
            try {
                invokeReceiver(ctx, intent, anotherReceiver.get(key));
            } catch (Exception e) {
                android.util.Log.d("nmj7", "invoke install referrer receiver error : " + e);
            }
        }

    }

    public static HashMap<String,String> getAnotherInstallReferrerReceiver(Context ctx,
                                                                           Class<?> mainReceiverClz) {
        HashMap<String,String> result = new HashMap<>();
        try {
            ComponentName myReceiver = new ComponentName(ctx, mainReceiverClz);
            ActivityInfo info = ctx.getPackageManager().getReceiverInfo(myReceiver, PackageManager.GET_META_DATA);
            if (info.metaData != null) {
                for (String key : info.metaData.keySet()) {
                    if (!mainReceiverClz.getName().equals(info.metaData.getString(key))) {
                        result.put(key, info.metaData.getString(key));
                    }
                }
            }
        } catch (Exception e) {
            Log.d("nmj7", "next e :" + e);
        }

        return result;
    }



    private static void invokeReceiver(Context ctx, Intent intent, String className)
            throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        if (className.length() == 0)
            return;

        Class<BroadcastReceiver> clazz = (Class<BroadcastReceiver>) Class.forName(className);
        BroadcastReceiver receiver = clazz.newInstance();
        receiver.onReceive(ctx, intent);
    }
}
