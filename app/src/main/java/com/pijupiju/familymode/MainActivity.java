package com.pijupiju.familymode;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = MainActivity.class.getSimpleName();
    Switch swManageService;
    Boolean serviceEnabled = false;
    TextView tvWiFiState;
    TextView tvWiFiSSID;
    TextView tvWiFiMarked;
    TextView tvRingerState;
    TextView tvRingerMarked;

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
    public boolean onCreateOptionsMenu(Menu menu) {
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        Log.d(TAG, "-> " + methodName);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        Log.d(TAG, "-> " + methodName);

        switch (item.getItemId()) {
            case R.id.actionMarkSSID:
                markSSID();
                return true;
            case R.id.actionManageMarkedSSIDs:
                manageMarkedSSIDs();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        Log.d(TAG, "-> " + methodName);

        WifiReceiver.manageRingerBasedOnSSID(getApplicationContext(), false);
    }

    private void initViews() {
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        Log.d(TAG, "-> " + methodName);

        swManageService = (Switch) findViewById(R.id.swManageService);
        SharedPreferences preferences = getApplicationContext().getSharedPreferences(getString(R.string.shared_prefs_file), 0);
        serviceEnabled = preferences.getBoolean(getString(R.string.shared_prefs_service_enabled), false);
        swManageService.setChecked(serviceEnabled);
        swManageService.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                manageService();
            }
        });

        tvWiFiState = (TextView) findViewById(R.id.tvWiFiState);
        tvWiFiSSID = (TextView) findViewById(R.id.tvWiFiSSID);
        tvWiFiMarked = (TextView) findViewById(R.id.tvWiFiMarked);
        tvRingerState = (TextView) findViewById(R.id.tvRingerState);
        tvRingerMarked = (TextView) findViewById(R.id.tvRingerMarked);
        updateStats();
    }

    private void updateStats() {
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        Log.d(TAG, "-> " + methodName);

        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        assert wifiManager != null;

        Integer wifiState = wifiManager.getWifiState();
        //        int WIFI_STATE_DISABLED = 1;
        //        int WIFI_STATE_DISABLING = 0;
        //        int WIFI_STATE_ENABLED = 3;
        //        int WIFI_STATE_ENABLING = 2;
        //        int WIFI_STATE_UNKNOWN = 4;

        if (wifiState.equals(1) || wifiState.equals(0)) {
            tvWiFiState.setText(R.string.tv_wifi_state_disabled);
            tvWiFiSSID.setVisibility(View.INVISIBLE);
        } else {
            String currentSSID = SSIDManager.getCurrentSSID(this);
            tvWiFiState.setText(R.string.tv_wifi_state_enabled);
            tvWiFiSSID.setText(String.format(getString(R.string.tv_wifi_ssid_placeholder), currentSSID));
            tvWiFiSSID.setVisibility(View.VISIBLE);
        }
    }

    private void manageService() {
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        Log.d(TAG, "-> " + methodName);

        SharedPreferences preferences = getApplicationContext().getSharedPreferences(getString(R.string.shared_prefs_file), 0);
        serviceEnabled = preferences.getBoolean(getString(R.string.shared_prefs_service_enabled), false);

        if (serviceEnabled) {
            RingerManager.enableRinger(this);
            getSharedPreferences(getString(R.string.shared_prefs_file), MODE_PRIVATE).edit().putBoolean(getString(R.string.shared_prefs_service_enabled), false).apply();
        } else {
            getSharedPreferences(getString(R.string.shared_prefs_file), MODE_PRIVATE).edit().putBoolean(getString(R.string.shared_prefs_service_enabled), true).apply();
        }
        WifiReceiver.manageRingerBasedOnSSID(getApplicationContext(), false);
    }

    private void markSSID() {
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        Log.d(TAG, "-> " + methodName);

        String currentSSID = SSIDManager.getCurrentSSID(this);
        Log.d(TAG, "-> currentSSID: " + currentSSID);

        SSIDManager.markSSID(this, currentSSID);
        WifiReceiver.manageRingerBasedOnSSID(getApplicationContext(), false);
    }

    private void manageMarkedSSIDs() {
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        Log.d(TAG, "-> " + methodName);

        Intent intent = new Intent(getApplicationContext(), SSIDListActivity.class);
        startActivity(intent);
    }
}
