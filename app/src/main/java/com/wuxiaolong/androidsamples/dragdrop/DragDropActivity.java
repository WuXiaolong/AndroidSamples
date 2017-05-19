package com.wuxiaolong.androidsamples.dragdrop;

import android.content.ClipData;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.wuxiaolong.androidsamples.BaseActivity;
import com.wuxiaolong.androidsamples.R;

public class DragDropActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_drop);
        findViewById(R.id.button).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                // 设置数据,会被拖动目标ViewGroup所接收,即用来传递信息
                ClipData data = ClipData.newPlainText("", "");

                // 设置拖动阴影,即你所拖动view(这也同时说明,你拖动的不过是一个影分身,本尊其实并没有移动)
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);
                return true;
            }
        });
        findViewById(R.id.dropZones).setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch (event.getAction()) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        // Do nothing
                        break;
                    case DragEvent.ACTION_DRAG_ENTERED:
                        //拖进拖放区
                        v.setBackgroundColor(ActivityCompat.getColor(DragDropActivity.this,R.color.colorPrimary));
                        break;
                    case DragEvent.ACTION_DRAG_EXITED:
                        //拖出拖放区
                        v.setBackgroundColor(ActivityCompat.getColor(DragDropActivity.this,R.color.white));
                        break;
                    case DragEvent.ACTION_DROP:
                        // 拖拽处理
                        //获取拖拽的view
                        View dragView = (View) event.getLocalState();
                        ViewGroup owner = (ViewGroup) dragView.getParent();
                        owner.removeView(dragView);
                        //拖放区view
                        RelativeLayout container = (RelativeLayout) v;
                        container.addView(dragView);
                        break;
                    case DragEvent.ACTION_DRAG_ENDED:
                        //拖出拖放区，释放
                        v.setBackgroundColor(ActivityCompat.getColor(DragDropActivity.this,R.color.colorPrimaryDark));
                    default:
                        break;
                }
                return true;
            }
        });
    }
}
