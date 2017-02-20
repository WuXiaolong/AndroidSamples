package com.wuxiaolong.androidsamples.customview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

import com.wuxiaolong.androidsamples.R;
import com.wuxiaolong.androidutils.library.LogUtil;

public class EmptyViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty_view);
        EmptyView emptyView = (EmptyView) findViewById(R.id.emptyView);
        emptyView.setEmptyText("djhgfkdhjf");
//        emptyView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                LogUtil.d("left=" + emptyView.getLeft() + ",top=" + emptyView.getTop() +
//                        ",right=" + emptyView.getRight() + ",bottom=" + emptyView.getBottom());
//                LogUtil.d("x=" + emptyView.getX() + ",y=" + emptyView.getY());
//            }
//        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogUtil.d("x==" + event.getX() + ",y==" + event.getY());
        LogUtil.d("Rawx=" + event.getRawX() + ",Rawy=" + event.getRawY());
        return true;
    }
}
