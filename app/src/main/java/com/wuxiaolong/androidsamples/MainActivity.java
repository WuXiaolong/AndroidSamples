package com.wuxiaolong.androidsamples;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wuxiaolong.androidsamples.itemtouchhelper.ItemTouchHelperActivity;
import com.wuxiaolong.androidsamples.notification.NotificationActivity;
import com.wuxiaolong.androidsamples.retrofit.RetrofitActivity;
import com.wuxiaolong.androidsamples.videoplay.VideoPlayViewActivity;
import com.wuxiaolong.androidsamples.viewdraghelper.ViewDragActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class MainActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private RecyclerViewAdatper recyclerViewAdatper;
    private List<String> textList = new ArrayList<>();
    private List<Class> classList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //设置是否滑动退出
        setSwipeBackEnable(false);
        initToolbarAsHome("吴小龙同學");
        initData();
        recyclerView = (RecyclerView) findViewById(R.id.recycleerView);
        recyclerViewAdatper = new RecyclerViewAdatper();
        recyclerView.setAdapter(recyclerViewAdatper);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
    }

    private void initData() {

        textList.add("Notification");
        textList.add("ViewDrag");
        textList.add("VideoPlay");
        textList.add("Retrofit");
        textList.add("ItemTouchHelper");
        Collections.sort(textList);

        classList.add(NotificationActivity.class);
        classList.add(ViewDragActivity.class);
        classList.add(VideoPlayViewActivity.class);
        classList.add(RetrofitActivity.class);
        classList.add(ItemTouchHelperActivity.class);
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
