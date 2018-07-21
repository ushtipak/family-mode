package com.pijupiju.familymode;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MyArrayAdapter extends ArrayAdapter<String> {
    private final static String TAG = MyArrayAdapter.class.getSimpleName();

    MyArrayAdapter(@NonNull Context context, String[] values) {
        super(context, R.layout.row_layout, values);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        Log.d(TAG, "-> " + methodName);

        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View myView = layoutInflater.inflate(R.layout.row_layout, parent, false);
        String SSIDname = getItem(position);
        TextView tvSSIDRow = (TextView) myView.findViewById(R.id.tvSSIDRow);
        tvSSIDRow.setText(SSIDname);
        return myView;
    }
}
