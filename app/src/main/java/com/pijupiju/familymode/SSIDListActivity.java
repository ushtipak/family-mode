package com.pijupiju.familymode;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Arrays;

public class SSIDListActivity extends AppCompatActivity {
    private final static String TAG = SSIDListActivity.class.getSimpleName();

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

        String[] allMarkedSSIDs = SSIDManager.getMarkedSSIDs(this);
        if (allMarkedSSIDs != null) {
            Log.d(TAG, "-> allMarkedSSIDs: " + Arrays.toString(allMarkedSSIDs));


            ListAdapter listAdapter = new MyArrayAdapter(this, allMarkedSSIDs);
            ListView listView = (ListView) findViewById(R.id.lvSSIDs);
            listView.setAdapter(listAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String selectedSSID = String.valueOf(parent.getItemAtPosition(position));
                    Toast.makeText(getApplicationContext(), selectedSSID, Toast.LENGTH_LONG).show();
                    SSIDManager.removeSSIDFromMarked(getApplicationContext(), selectedSSID);
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.usr_msg_ssid_list_empty), Toast.LENGTH_LONG).show();
        }
    }
}
