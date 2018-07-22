package com.pijupiju.familymode;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Arrays;

public class MyIntentService extends IntentService {
    private final static String TAG = MyIntentService.class.getSimpleName();

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        Log.d(TAG, "-> " + methodName);
    }

    static BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String methodName = new Object() {
            }.getClass().getEnclosingMethod().getName();
            Log.d(TAG, "-> " + methodName);

            String currentSSID = SSIDManager.getCurrentSSID(context);
            Log.d(TAG, "-> currentSSID: " + currentSSID);

            String[] markedSSIDs = SSIDManager.getMarkedSSIDs(context.getApplicationContext());
            Log.d(TAG, "-> markedSSIDs: " + Arrays.toString(markedSSIDs));

            if (markedSSIDs != null) {
                if (Arrays.asList(markedSSIDs).contains(currentSSID)) {
                    Log.d(TAG, context.getString(R.string.msg_wifi_connected_to_marked));
                    RingerManager.disableRinger(context);
                } else {
                    Log.d(TAG, context.getString(R.string.msg_wifi_connected_to_non_marked));
                    RingerManager.enableRinger(context);
                }
            }
        }
    };

}
