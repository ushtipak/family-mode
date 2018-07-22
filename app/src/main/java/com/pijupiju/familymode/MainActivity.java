package com.pijupiju.familymode;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = MainActivity.class.getSimpleName();
    Button btnManageService;
    Button btnMarkSSID;
    Button btnManageMarkedSSIDs;
    Intent myIntent;
    Boolean serviceEnabled = false;

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

        btnManageService = (Button) findViewById(R.id.btnManageService);
        btnManageService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manageService();
            }
        });

        btnMarkSSID = (Button) findViewById(R.id.btnMarkSSID);
        btnMarkSSID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                markSSID();
            }
        });

        btnManageMarkedSSIDs = (Button) findViewById(R.id.btnManageMarkedSSIDs);
        btnManageMarkedSSIDs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manageMarkedSSIDs();
            }
        });
    }

    private void manageService() {
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        Log.d(TAG, "-> " + methodName);

        if (serviceEnabled) {
            stopService();
            serviceEnabled = false;
        } else {
            startService();
            serviceEnabled = true;
        }
    }

    private void startService() {
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        Log.d(TAG, "-> " + methodName);

        Intent intent = new Intent(this, MyIntentService.class);
        startService(intent);
        registerReceiver(MyIntentService.broadcastReceiver, new IntentFilter("android.net.wifi.STATE_CHANGE"));
        btnManageService.setText(R.string.btn_manage_service_disable);
    }

    private void stopService() {
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        Log.d(TAG, "-> " + methodName);

        RingerManager.enableRinger(this);
        if (myIntent != null) {
            stopService(myIntent);
        }
        myIntent = null;
        btnManageService.setText(R.string.btn_manage_service_enable);
    }

    private void markSSID() {
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        Log.d(TAG, "-> " + methodName);

        String currentSSID = SSIDManager.getCurrentSSID(this);
        Log.d(TAG, "-> currentSSID: " + currentSSID);

        SSIDManager.markSSID(this, currentSSID);
    }

    private void manageMarkedSSIDs() {
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        Log.d(TAG, "-> " + methodName);

        Intent intent = new Intent(getApplicationContext(), SSIDListActivity.class);
        startActivity(intent);
    }
}
