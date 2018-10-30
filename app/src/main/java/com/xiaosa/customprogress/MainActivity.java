package com.xiaosa.customprogress;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CustomProgressView customProgressView = (CustomProgressView) findViewById(R.id.cpv);
        customProgressView.startProgress(5000);
    }
}
