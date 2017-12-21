package com.wuxiaolong.androidsamples;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wuxiaolong.androidsamples.appmemory.AppMemoryActivity;
import com.wuxiaolong.androidsamples.constraintlayout.ConstraintLayoutActivity;
import com.wuxiaolong.androidsamples.customview.EmptyViewActivity;
import com.wuxiaolong.androidsamples.designpatterns.ChainOfResponsibilityActivity;
import com.wuxiaolong.androidsamples.dragdrop.DragDropActivity;
import com.wuxiaolong.androidsamples.glide.GlideActivity;
import com.wuxiaolong.androidsamples.html5.Html5Activity;
import com.wuxiaolong.androidsamples.itemtouchhelper.ItemTouchHelperActivity;
import com.wuxiaolong.androidsamples.notification.NotificationActivity;
import com.wuxiaolong.androidsamples.objecttosdcard.ObjectToSDCardActivity;
import com.wuxiaolong.androidsamples.observer.ConcreteObservable;
import com.wuxiaolong.androidsamples.observer.ObserverActivity;
import com.wuxiaolong.androidsamples.recyclerViewcheckBox.RecyclerViewCheckBoxActivity;
import com.wuxiaolong.androidsamples.retrofit.RetrofitActivity;
import com.wuxiaolong.androidsamples.runtimepermission.RuntimePermissionActivity;
import com.wuxiaolong.androidsamples.videoplay.VideoPlayViewActivity;
import com.wuxiaolong.androidsamples.viewdraghelper.ViewDragActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
/**
 * Created by WuXiaolong on 2017/7/17.
 * 个人博客：http：//wuxiaolong.me
 */

public class MainActivity extends BaseActivity implements Observer {
    private RecyclerView recyclerView;
    private RecyclerViewAdatper recyclerViewAdatper;
    private List<String> textList = new ArrayList<>();
    private List<Class> classList = new ArrayList<>();
    private ConcreteObservable concreteObservable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);

        setContentView(R.layout.activity_main);
        //设置是否滑动退出
        setSwipeBackEnable(false);
        initToolbarAsHome("吴小龙同學");
        initData();
        recyclerView = (RecyclerView) findViewById(R.id.recycleerView);
        recyclerViewAdatper = new RecyclerViewAdatper();
        recyclerView.setAdapter(recyclerViewAdatper);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
//        Trace.beginSection("beginSection");
//        Trace.endSection();

        concreteObservable = ConcreteObservable.getInstance();
        //concreteObservable.addObserver(this);
        //concreteObservable.post();
    }

    @Override
    public void update(Observable o, Object arg) {
        Log.d("wxl", "MainActivity");
        ConcreteObservable concreteObservable = (ConcreteObservable) o;
        Log.d("wxl", "data=" + concreteObservable.getData());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //删除从一组对象的观察者的观察者
        concreteObservable.deleteObserver(this);
    }

    private void initData() {

        textList.add("Notification");
        textList.add("ViewDrag");
        textList.add("VideoPlay");
        textList.add("Retrofit");
        textList.add("ItemTouchHelper");
        textList.add("EmptyView");
        textList.add("RuntimePermission");
        textList.add("AppMemory");
        textList.add("Glide");
        textList.add("ChainOfResponsibility");
        textList.add("DragDrop");
        textList.add("Html5");
        textList.add("Observer");
        textList.add("ObjectToSDCard");
        textList.add("EPub");
        textList.add("ConstraintLayout");
        textList.add("RecyclerView CheckBox");

        classList.add(RecyclerViewCheckBoxActivity.class);
        classList.add(ConstraintLayoutActivity.class);
        classList.add(ObserverActivity.class);
        classList.add(EPubActivity.class);
        classList.add(ChainOfResponsibilityActivity.class);
        classList.add(NotificationActivity.class);
        classList.add(ViewDragActivity.class);
        classList.add(VideoPlayViewActivity.class);
        classList.add(RetrofitActivity.class);
        classList.add(ItemTouchHelperActivity.class);
        classList.add(EmptyViewActivity.class);
        classList.add(RuntimePermissionActivity.class);
        classList.add(AppMemoryActivity.class);
        classList.add(GlideActivity.class);
        classList.add(DragDropActivity.class);
        classList.add(Html5Activity.class);
        classList.add(ObjectToSDCardActivity.class);

        Collections.sort(textList);

        Collections.sort(classList, new Comparator<Class>() {
            @Override
            public int compare(Class c1, Class c2) {
                return c1.getSimpleName().compareTo(c2.getSimpleName());
            }
        });

    }


    public class RecyclerViewAdatper extends RecyclerView.Adapter<RecyclerViewAdatper.ViewHolder> {


        @Override
        public RecyclerViewAdatper.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, parent, false);
            return new RecyclerViewAdatper.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerViewAdatper.ViewHolder holder, int position) {
            holder.textView.setText(textList.get(position));

        }

        @Override
        public int getItemCount() {
            return textList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            public TextView textView;

            public ViewHolder(View itemView) {
                super(itemView);
                textView = (TextView) itemView.findViewById(R.id.text);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(MainActivity.this, classList.get(getLayoutPosition())));
                    }
                });
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.github) {
            Uri uri = Uri.parse("https://github.com/WuXiaolong/AndroidSamples");
            Intent it = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(it);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
