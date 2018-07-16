package com.pijupiju.familymode;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.UUID;

public class SSIDListManager extends AppCompatActivity {
    private final static String TAG = SSIDListManager.class.getSimpleName();

    ListView lvSSIDs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        Log.d(TAG, "-> " + methodName);

        setContentView(R.layout.activity_ssidlist_manager);
        initViews();
        showMarkedSSIDs();
    }

    private void initViews() {
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        Log.d(TAG, "-> " + methodName);

        lvSSIDs = (ListView) findViewById(R.id.lvSSIDs);
    }

    private void showMarkedSSIDs() {
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        Log.d(TAG, "-> " + methodName);

        String[] allMarkedSSIDs = getMarkedSSIDs();
        if (allMarkedSSIDs != null) {
            Log.d(TAG, "-> allMarkedSSIDs: " + Arrays.toString(allMarkedSSIDs));


            ListAdapter listAdapter = new MyAdapter(this, allMarkedSSIDs);
            ListView listView = (ListView) findViewById(R.id.lvSSIDs);
            listView.setAdapter(listAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String selectedSSID = String.valueOf(parent.getItemAtPosition(position));
                    Toast.makeText(getApplicationContext(), selectedSSID, Toast.LENGTH_LONG).show();
                    removeSSIDFromMarked(selectedSSID);
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "THERE ARE NO SAVED WIFIES :D", Toast.LENGTH_LONG).show();
        }
    }

    private String[] getMarkedSSIDs() {
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        Log.d(TAG, "-> " + methodName);

        SharedPreferences pref = getApplicationContext().getSharedPreferences(getString(R.string.shared_prefs_file), 0);
        String markedSSIDsSerialized = pref.getString(getString(R.string.shared_prefs_key_ssid), "");
        Log.d(TAG, "-> markedSSIDsSerialized: " + markedSSIDsSerialized);

        if (!markedSSIDsSerialized.equals("")) {
            String[] markedSSIDs = markedSSIDsSerialized.split(getString(R.string.shared_prefs_key_ssid_separator));
            Log.d(TAG, "-> markedSSIDs: " + Arrays.toString(markedSSIDs));
            for (int i = 0; i < markedSSIDs.length; i++) {
                Log.d(TAG, "markedSSIDs[" + i + "]: " + markedSSIDs[i]);
            }
            return markedSSIDs;
        } else {
            return null;
        }
    }

    private void removeSSIDFromMarked(String SSIDName) {
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        Log.d(TAG, "-> " + methodName);

        SharedPreferences pref = getApplicationContext().getSharedPreferences(getString(R.string.shared_prefs_file), 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(getString(R.string.shared_prefs_key_ssid), UUID.randomUUID().toString()).apply();
    }
}
