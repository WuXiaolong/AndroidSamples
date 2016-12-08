package com.wuxiaolong.androidsamples.itemtouchhelper;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wuxiaolong.androidsamples.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ItemTouchHelperActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Context context;
    private ItemTouchHelper itemTouchHelper;
    private List<String> mItems = new ArrayList<>();
    private RecyclerViewAdatper recyclerViewAdatper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_touch_helper);
        context = this;
        recyclerView = (RecyclerView) findViewById(R.id.recycleerView);
        recyclerViewAdatper = new RecyclerViewAdatper();
        recyclerView.setAdapter(recyclerViewAdatper);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
        //recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, OrientationHelper.VERTICAL));
        ItemTouchHelperCallback itemTouchHelperCallback = new ItemTouchHelperCallback(recyclerViewAdatper);
        itemTouchHelper = new ItemTouchHelper(itemTouchHelperCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    public class RecyclerViewAdatper extends RecyclerView.Adapter<RecyclerViewAdatper.ViewHolder> implements ItemTouchHelperAdapter {


        RecyclerViewAdatper() {
            mItems.addAll(Arrays.asList(context.getResources().getStringArray(R.array.dummy_items)));
        }

        @Override
        public boolean onItemMove(int fromPosition, int toPosition) {
            //互换列表中指定位置的数据
            Collections.swap(mItems, fromPosition, toPosition);
            notifyItemMoved(fromPosition, toPosition);
            return true;
        }

        @Override
        public void onItemDismiss(int position) {
            mItems.remove(position);
            notifyItemRemoved(position);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.textView.setText(mItems.get(position));
            holder.handleView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                        itemTouchHelper.startDrag(holder);
                    }
                    return false;
                }
            });
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {
            public TextView textView;
            public ImageView handleView;

            public ViewHolder(View itemView) {
                super(itemView);
                textView = (TextView) itemView.findViewById(R.id.text);
                handleView = (ImageView) itemView.findViewById(R.id.handle);
            }

            @Override
            public void onItemSelected() {
                itemView.setBackgroundColor(Color.LTGRAY);
            }

            @Override
            public void onItemClear() {
                itemView.setBackgroundColor(0);
            }
        }
    }

}
