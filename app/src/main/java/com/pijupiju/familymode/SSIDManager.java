package com.pijupiju.familymode;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

import java.util.Arrays;
import java.util.UUID;

import static android.content.Context.MODE_PRIVATE;

class SSIDManager {
    private final static String TAG = SSIDManager.class.getSimpleName();

    static String getCurrentSSID(Context context) {
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        assert wifiManager != null;
        WifiInfo info = wifiManager.getConnectionInfo();
        if (info != null) {
            String currentSSID = info.getSSID();
            Log.d(TAG, "-> currentSSID: " + currentSSID);

            if (!currentSSID.equals("<unknown ssid>")) {
                return currentSSID;
            } else {
                Log.d(TAG, context.getString(R.string.usr_msg_wifi_not_connected));
            }
        }
        return "";
    }

    static void markHomeSSID(Context context, String currentSSID) {
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        Log.d(TAG, "-> " + methodName);

        SharedPreferences preferences = context.getApplicationContext().getSharedPreferences(context.getString(R.string.shared_prefs_file), 0);
        String allMarkedSSIDs = preferences.getString(context.getString(R.string.shared_prefs_key_ssid), "");
        Log.d(TAG, "-> allMarkedSSIDs: " + allMarkedSSIDs);

        if (allMarkedSSIDs.contains(currentSSID)) {
            Log.d(TAG, context.getString(R.string.usr_msg_ssid_already_registered));
            Toast.makeText(context.getApplicationContext(), context.getString(R.string.usr_msg_ssid_already_registered), Toast.LENGTH_LONG).show();
        } else {
            if (!allMarkedSSIDs.equals("")) {
                allMarkedSSIDs += context.getString(R.string.shared_prefs_key_ssid_separator);
                Log.d(TAG, "-> allMarkedSSIDs: " + allMarkedSSIDs);
            }
            allMarkedSSIDs += currentSSID;
            Log.d(TAG, "-> allMarkedSSIDs: " + allMarkedSSIDs);
            context.getSharedPreferences(context.getString(R.string.shared_prefs_file), MODE_PRIVATE).edit().putString(context.getString(R.string.shared_prefs_key_ssid), allMarkedSSIDs).apply();
        }
    }

    static String[] getMarkedSSIDs(Context context) {
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        Log.d(TAG, "-> " + methodName);

        SharedPreferences pref = context.getApplicationContext().getSharedPreferences(context.getString(R.string.shared_prefs_file), 0);
        String markedSSIDsSerialized = pref.getString(context.getString(R.string.shared_prefs_key_ssid), "");
        Log.d(TAG, "-> markedSSIDsSerialized: " + markedSSIDsSerialized);

        if (!markedSSIDsSerialized.equals("")) {
            String[] markedSSIDs = markedSSIDsSerialized.split(context.getString(R.string.shared_prefs_key_ssid_separator));
            Log.d(TAG, "-> markedSSIDs: " + Arrays.toString(markedSSIDs));
            for (int i = 0; i < markedSSIDs.length; i++) {
                Log.d(TAG, "markedSSIDs[" + i + "]: " + markedSSIDs[i]);
            }
            return markedSSIDs;
        } else {
            return null;
        }
    }

    static void removeSSIDFromMarked(Context context, String SSIDName) {
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        Log.d(TAG, "-> " + methodName);

        SharedPreferences pref = context.getApplicationContext().getSharedPreferences(context.getString(R.string.shared_prefs_file), 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(context.getString(R.string.shared_prefs_key_ssid), UUID.randomUUID().toString()).apply();
    }

}
