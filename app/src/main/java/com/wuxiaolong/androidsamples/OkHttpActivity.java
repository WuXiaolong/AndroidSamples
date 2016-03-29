package com.wuxiaolong.androidsamples;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpActivity extends Activity {
    OkHttpClient mOkHttpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http);
        mOkHttpClient = new OkHttpClient();

        run("http://wuxiaolong.me/");
    }

    void run(String url) {
        Log.d("wxl", "run");
        new Thread(new Runnable() {
            @Override
            public void run() {
                Request request = new Request.Builder()
                        .url(url)
                        .build();

                Response response = null;
                try {
                    response = mOkHttpClient.newCall(request).execute();
                    Log.d("wxl", "response==" + response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
