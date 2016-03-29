package com.wuxiaolong.androidsamples.xscrollview;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.wuxiaolong.androidsamples.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class XScrollViewActivity extends AppCompatActivity {
    XScrollView mScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xscroll_view);
        mScrollView = (XScrollView) findViewById(R.id.mXScrollView);
        mScrollView.setPullRefreshEnable(true);
        mScrollView.setPullLoadEnable(true);
//        mScrollView.setAutoLoadEnable(true);
        mScrollView.setIXScrollViewListener(new XScrollView.IXScrollViewListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mScrollView.stopRefresh();
                    }
                }, 5000);
            }

            @Override
            public void onLoadMore() {
                Log.d("wxl", "onLoadMore");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mScrollView.stopLoadMore();
                    }
                }, 5000);
            }
        });
        mScrollView.setRefreshTime(new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA).format(new Date()));
        View content = LayoutInflater.from(this).inflate(R.layout.xscrollview_content, null);
        mScrollView.setView(content);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_xscroll_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
