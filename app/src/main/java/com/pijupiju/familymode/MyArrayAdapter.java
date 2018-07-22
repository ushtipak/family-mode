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

    static class ViewHolder {
        TextView tvSSIDRow;

        ViewHolder(View v) {
            tvSSIDRow = (TextView) v.findViewById(R.id.tvSSIDRow);
        }
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        Log.d(TAG, "-> " + methodName);

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String SSIDname = getItem(position);
        viewHolder.tvSSIDRow.setText(SSIDname);
        return convertView;
    }
}
