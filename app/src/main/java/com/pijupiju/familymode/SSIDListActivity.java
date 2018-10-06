package com.pijupiju.familymode;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SSIDListActivity extends AppCompatActivity {
    private final static String TAG = SSIDListActivity.class.getSimpleName();
    MyArrayAdapter myArrayAdapter = null;
    ArrayList<String> allMarkedSSIDs = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        Log.d(TAG, "-> " + methodName);

        setContentView(R.layout.activity_ssidlist);
        initViews();
    }

    private void initViews() {
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        Log.d(TAG, "-> " + methodName);

        ListView lvSSIDs = (ListView) findViewById(R.id.lvSSIDs);
        allMarkedSSIDs = SSIDManager.getMarkedSSIDs(this);
        myArrayAdapter = new MyArrayAdapter(SSIDListActivity.this, R.layout.list_row, allMarkedSSIDs);
        lvSSIDs.setAdapter(myArrayAdapter);
    }

    public class MyArrayAdapter extends ArrayAdapter<String> {
        ArrayList<String> arrayList;

        MyArrayAdapter(Context context, int textViewResourceId, ArrayList<String> objects) {
            super(context, textViewResourceId, objects);
            this.arrayList = objects;
        }

        class ViewHolder {
            TextView tvSSID;
            Button btnRemove;

            ViewHolder(View v) {
                tvSSID = (TextView) v.findViewById(R.id.tvSSID);
                btnRemove = (Button) v.findViewById(R.id.btnRemove);
            }
        }

        @NonNull
        @Override
        public View getView(final int position, View convertView, @NonNull final ViewGroup parent) {
            String methodName = new Object() {
            }.getClass().getEnclosingMethod().getName();
            Log.d(TAG, "-> " + methodName);

            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent, false);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            String targetSSID = getItem(position);
            viewHolder.tvSSID.setText(targetSSID);
            viewHolder.btnRemove.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    final String selectedSSID = arrayList.get(position);

                    AlertDialog.Builder builder = new AlertDialog.Builder(SSIDListActivity.this);
                    builder.setMessage(String.format(getString(R.string.dialog_remove_ssid_inquiry), selectedSSID))
                            .setPositiveButton(R.string.dialog_remove_ssid_yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    arrayList.remove(position);
                                    myArrayAdapter.notifyDataSetChanged();
                                    Toast.makeText(getApplicationContext(), getString(R.string.msg_ssid_removed), Toast.LENGTH_SHORT).show();
                                    SSIDManager.removeMarkedSSID(getApplicationContext(), selectedSSID);
                                    WifiReceiver.manageRingerBasedOnSSID(getApplicationContext(), false);
                                }
                            })
                            .setNegativeButton(R.string.dialog_negative, null);
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            });

            return convertView;
        }
    }
}
