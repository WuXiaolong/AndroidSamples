package com.wuxiaolong.androidsamples;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class RetrolambdaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrolambda);
        findViewById(R.id.onclick).setOnClickListener(v -> Toast.makeText(RetrolambdaActivity.this, "clicked", Toast.LENGTH_LONG).show());
    }
}
