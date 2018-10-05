package com.pijupiju.familymode;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.util.ArrayList;

public class WifiReceiver extends BroadcastReceiver {
    private final static String TAG = WifiReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        Log.d(TAG, "-> " + methodName);

        String action = intent.getAction();
        assert action != null;
        if (action.equals("android.net.wifi.STATE_CHANGE")) {
            SharedPreferences preferences = context.getApplicationContext().getSharedPreferences(context.getString(R.string.shared_prefs_file), 0);
            Boolean serviceEnabled = preferences.getBoolean(context.getString(R.string.shared_prefs_service_enabled), false);
            if (serviceEnabled) {
                manageRingerBasedOnSSID(context, true);
            }
        }
    }

    static void manageRingerBasedOnSSID(Context context, Boolean automatic) {
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        Log.d(TAG, "-> " + methodName);

        SharedPreferences preferences = context.getApplicationContext().getSharedPreferences(context.getString(R.string.shared_prefs_file), 0);
        Boolean serviceEnabled = preferences.getBoolean(context.getString(R.string.shared_prefs_service_enabled), false);

        if (serviceEnabled) {

            WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            assert wifiManager != null;
            if (wifiManager.isWifiEnabled()) {

                if (automatic) {
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }

                String currentSSID = SSIDManager.getCurrentSSID(context);
                Log.d(TAG, "-> currentSSID: " + currentSSID);

                ArrayList<String> markedSSIDs = SSIDManager.getMarkedSSIDs(context.getApplicationContext());
                Log.d(TAG, "-> markedSSIDs: " + markedSSIDs);

                if (markedSSIDs != null) {
                    if (markedSSIDs.contains(currentSSID)) {
                        Log.d(TAG, context.getString(R.string.log_wifi_connected_to_marked));
                        RingerManager.disableRinger(context);
                    }
                } else {
                    Log.d(TAG, context.getString(R.string.log_wifi_connected_to_non_marked));
                    RingerManager.enableRinger(context);
                }
            } else {
                Log.d(TAG, context.getString(R.string.log_wifi_not_connected));
                RingerManager.enableRinger(context);
            }
        }
    }
}