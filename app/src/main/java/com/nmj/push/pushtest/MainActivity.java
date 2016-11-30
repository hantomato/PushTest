package com.nmj.push.pushtest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkReferrer();


        ((TextView)findViewById(R.id.textView)).setText("version : " + BuildConfig.VERSION_CODE);

        ((Button)findViewById(R.id.button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent("com.nmj.push.pushtest.SecondReceiver");
                sendBroadcast(i);
            }
        });

        ((Button)findViewById(R.id.btn_billing)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, BillingActivity.class));
            }
        });
    }

    private void checkReferrer() {
        SharedPreferences pref = getSharedPreferences("nmj", Context.MODE_PRIVATE);
        String savedReferrer = pref.getString("REFERRER", "");
        long savedTime = pref.getLong("TIMESTAMP", 0);
        if (!TextUtils.isEmpty(savedReferrer)) {
            Log.d("nmj", "saved referrer : " + savedReferrer);
            Log.d("nmj", "saved time : " + savedTime);
            Toast.makeText(this, "saved referrer : " + savedReferrer + ", saved time : " + savedTime, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "is not exist referrer", Toast.LENGTH_LONG).show();
        }
    }
}
