package com.wuxiaolong.androidsamples.marqueetextview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;

import com.wuxiaolong.androidsamples.R;

public class MarqueeTextViewActivity extends AppCompatActivity {
    MarqueeTextView marqueeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marquee_text_view);
        marqueeTextView = (MarqueeTextView) findViewById(R.id.marqueeTextView);
        marqueeTextView.setMovementMethod(ScrollingMovementMethod.getInstance());
    }
}
