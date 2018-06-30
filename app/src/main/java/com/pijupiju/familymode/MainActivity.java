package com.pijupiju.familymode;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = MainActivity.class.getSimpleName();
    Button btnStartService;
    Intent myIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        Log.d(TAG, "-> " + methodName);

        setContentView(R.layout.activity_main);
        initViews();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        Log.d(TAG, "-> " + methodName);

        stopService();
    }

    private void initViews() {
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        Log.d(TAG, "-> " + methodName);

        btnStartService = (Button) findViewById(R.id.btnStartService);
        btnStartService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startService();
            }
        });
    }

    private void startService() {
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        Log.d(TAG, "-> " + methodName);

        Intent intent = new Intent(this, MyIntentService.class);
        startService(intent);
        registerReceiver(broadcastReceiver, new IntentFilter("android.net.wifi.STATE_CHANGE"));
    }

    private void stopService() {
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        Log.d(TAG, "-> " + methodName);

        if (myIntent != null) {
            stopService(myIntent);
        }
        myIntent = null;
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String methodName = new Object() {
            }.getClass().getEnclosingMethod().getName();
            Log.d(TAG, "-> " + methodName);

            WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            assert wifiManager != null;
            WifiInfo info = wifiManager.getConnectionInfo();
            if (info != null) {
                String wifiName = info.getSSID();
                Log.d(TAG, "wifiName: " + wifiName);
            }
        }
    };

}
