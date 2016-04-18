package com.calcubaator.intz.calcubaator;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private TextView textView;
    public static String btnInput = "";
    public static String answer = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.inputOutput);

        if (savedInstanceState != null){
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "Restoring state");
            }
            setTextSize(answer);
            textView.setText(answer);
        }
    }

    public void onClick(View view) {
        Button btn = (Button) view;
        String idAsString = btn.getResources().getResourceName(btn.getId());

        if (BuildConfig.DEBUG) {
            Log.d(TAG, "Button pressed :" + idAsString);
        }

        btnInput = btn.getText().toString();

        Intent intent = new Intent();
        intent.setAction("com.calcserver.intz.CALCULATE");
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        intent.putExtra("Input", btnInput);
        sendOrderedBroadcast(intent, null, new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                answer = getResultData();
                setTextSize(answer);
                textView.setText("");
                textView.setText(answer);
            }
        }, null, Activity.RESULT_OK, null, null);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "onSaveInstanceState called");
        }
        super.onSaveInstanceState(savedInstanceState);
    }

    public void setTextSize(String input) {
        Configuration config = getResources().getConfiguration();
        int orientation = config.orientation;

        if(orientation == 1) { //if portrait
            if(input.length() >= 16) {
                textView.setTextSize(30);
            } else if(input.length() >= 12) {
                textView.setTextSize(40);
            } else if(input.length() > 8) {
                textView.setTextSize(50);
            } else {
                textView.setTextSize(70);
            }
        } else { //else landscape
            if(input.length() >= 26) {
                textView.setTextSize(30);
            } else if(input.length() >= 20) {
                textView.setTextSize(40);
            } else if(input.length() >= 14) {
                textView.setTextSize(50);
            } else {
                textView.setTextSize(70);
            }
        }
    }
}
