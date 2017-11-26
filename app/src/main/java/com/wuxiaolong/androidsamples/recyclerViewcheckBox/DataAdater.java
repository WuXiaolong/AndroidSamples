package com.wuxiaolong.androidsamples.recyclerViewcheckBox;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.wuxiaolong.androidsamples.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WuXiaolong on 2017/9/25.
 * 个人博客：http：//wuxiaolong.me
 */

public class DataAdater extends RecyclerView.Adapter<DataAdater.ViewHolder> {
    private Context context;


    public DataAdater(Context context) {
        this.context = context;
    }

    private List<DataModel.DataBean> dataBeanList = new ArrayList<>();


    public void addAll(List<DataModel.DataBean> dataBeanList) {
        this.dataBeanList.addAll(dataBeanList);
        notifyDataSetChanged();
    }

    public void clear() {
        dataBeanList.clear();
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_check_box_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DataModel.DataBean dataBean = dataBeanList.get(position);
        holder.title.setText(dataBean.getTitle());
        //设置 CheckBox，默认 false
        holder.checkBox.setChecked(dataBean.isChecked());
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //当 CheckBox 状态发生变化时，更改数据源，保存 CheckBox 状态
                dataBeanList.get(holder.getLayoutPosition()).setChecked(isChecked);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataBeanList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private CheckBox checkBox;
        private TextView title;

        public ViewHolder(View itemView) {
            super(itemView);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkBox);
            title = (TextView) itemView.findViewById(R.id.title);
        }
    }
}

