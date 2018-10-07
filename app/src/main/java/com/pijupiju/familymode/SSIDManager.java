package com.pijupiju.familymode;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import static android.content.Context.MODE_PRIVATE;

class SSIDManager {
    private final static String TAG = SSIDManager.class.getSimpleName();

    static String getCurrentSSID(Context context) {
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        Log.d(TAG, "-> " + methodName);

        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        assert wifiManager != null;
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        if (wifiInfo != null) {
            String supplicantState = wifiInfo.getSupplicantState().toString();
            String currentSSID = wifiInfo.getSSID();
            currentSSID = currentSSID.substring(1, currentSSID.length() - 1);
            if (!currentSSID.equals("unknown ssid") && !supplicantState.equals("DISCONNECTED")) {
                return currentSSID;
            }
        }
        return "";
    }

    static void markSSID(Context context, String currentSSID) {
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        Log.d(TAG, "-> " + methodName);

        SharedPreferences preferences = context.getApplicationContext().getSharedPreferences(context.getString(R.string.shared_prefs_file), 0);
        String markedSSIDBundle = preferences.getString(context.getString(R.string.shared_prefs_key_ssid), "");
        Log.d(TAG, "-> markedSSIDBundle: " + markedSSIDBundle);

        if (!currentSSID.equals("")) {
            if (markedSSIDBundle.contains(currentSSID)) {
                Log.d(TAG, context.getString(R.string.msg_ssid_already_registered));
                Toast.makeText(context.getApplicationContext(), context.getString(R.string.msg_ssid_already_registered), Toast.LENGTH_SHORT).show();
            } else {
                if (!markedSSIDBundle.equals("")) {
                    markedSSIDBundle += context.getString(R.string.shared_prefs_key_ssid_separator);
                    Log.d(TAG, "-> markedSSIDBundle: " + markedSSIDBundle);
                }
                markedSSIDBundle += currentSSID;
                Log.d(TAG, "-> markedSSIDBundle: " + markedSSIDBundle);
                context.getSharedPreferences(context.getString(R.string.shared_prefs_file), MODE_PRIVATE).edit().putString(context.getString(R.string.shared_prefs_key_ssid), markedSSIDBundle).apply();
                Toast.makeText(context.getApplicationContext(), context.getString(R.string.msg_ssid_marked), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context.getApplicationContext(), context.getString(R.string.msg_wifi_not_connected), Toast.LENGTH_SHORT).show();
        }
    }

    static ArrayList<String> getMarkedSSIDs(Context context) {
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        Log.d(TAG, "-> " + methodName);

        SharedPreferences pref = context.getApplicationContext().getSharedPreferences(context.getString(R.string.shared_prefs_file), 0);
        String markedSSIDBundle = pref.getString(context.getString(R.string.shared_prefs_key_ssid), "");
        Log.d(TAG, "-> markedSSIDBundle: " + markedSSIDBundle);

        if (!markedSSIDBundle.equals("")) {
            ArrayList<String> markedSSIDs = new ArrayList<>(Arrays.asList(markedSSIDBundle.split(context.getString(R.string.shared_prefs_key_ssid_separator))));
            Log.d(TAG, "-> markedSSIDs: " + markedSSIDs);
            return markedSSIDs;
        } else {
            return new ArrayList<>();
        }
    }

    static void removeMarkedSSID(Context context, String targetSSID) {
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        Log.d(TAG, "-> " + methodName);

        ArrayList<String> markedSSIDs = SSIDManager.getMarkedSSIDs(context);
        Log.d(TAG, "-> markedSSIDs: " + markedSSIDs);

        if (markedSSIDs != null) {
            if (markedSSIDs.contains(targetSSID)) {
                ArrayList<String> preservedSSIDs = new ArrayList<>();
                for (String markedSSID : markedSSIDs) {
                    if (!markedSSID.equals(targetSSID)) {
                        preservedSSIDs.add(markedSSID);
                    }
                }
                Log.d(TAG, "-> preservedSSIDs: " + preservedSSIDs);

                StringBuilder markedSSIDBundle = new StringBuilder();
                for (int i = 0; i < preservedSSIDs.size(); i++) {
                    markedSSIDBundle.append(preservedSSIDs.get(i));
                    if (i != preservedSSIDs.size() - 1) {
                        markedSSIDBundle.append(context.getString(R.string.shared_prefs_key_ssid_separator));
                    }
                }

                Log.d(TAG, "-> markedSSIDBundle: " + markedSSIDBundle);
                context.getSharedPreferences(context.getString(R.string.shared_prefs_file), MODE_PRIVATE).edit().putString(context.getString(R.string.shared_prefs_key_ssid), String.valueOf(markedSSIDBundle)).apply();
            }
        }
    }

    static Boolean isSSIDMarked(Context context, String currentSSID) {
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        Log.d(TAG, "-> " + methodName);

        Boolean isMarked = false;

        SharedPreferences preferences = context.getApplicationContext().getSharedPreferences(context.getString(R.string.shared_prefs_file), 0);
        String markedSSIDBundle = preferences.getString(context.getString(R.string.shared_prefs_key_ssid), "");
        Log.d(TAG, "-> markedSSIDBundle: " + markedSSIDBundle);

        if (!currentSSID.equals("")) {
            if (markedSSIDBundle.contains(currentSSID)) {
                isMarked = true;
            }
        }

        return isMarked;
    }

}
