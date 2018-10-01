package com.pijupiju.familymode;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class DiscloseActivity extends AppCompatActivity {
    private final static String TAG = DiscloseActivity.class.getSimpleName();
    Button btnToggleService;
    Boolean serviceEnabled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        Log.d(TAG, "-> " + methodName);

        setContentView(R.layout.activity_disclose);
        initViews();
    }

    private void initViews() {
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        Log.d(TAG, "-> " + methodName);

        btnToggleService = (Button) findViewById(R.id.btnToggleService);
        btnToggleService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manageService();
            }
        });
    }

    private void manageService() {
        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        Log.d(TAG, "-> " + methodName);

    }


    }
