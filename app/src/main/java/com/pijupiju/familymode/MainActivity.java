package com.pijupiju.familymode;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = MainActivity.class.getSimpleName();
    Switch swManageService;
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
