package com.wuxiaolong.androidsamples.toast;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.wuxiaolong.androidsamples.R;

public class ToastActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toast);
        Toast.makeText(ToastActivity.this, "Toast test", Toast.LENGTH_LONG).show();

        customToast();
    }

    void customToast() {
        Toast toast=new Toast(ToastActivity.this);
        View view=View.inflate(ToastActivity.this,R.layout.toast_view,null);
        toast.setView(view);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }
}
