package com.wuxiaolong.androidsamples.recyclerViewcheckBox;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.wuxiaolong.androidsamples.BaseActivity;
import com.wuxiaolong.androidsamples.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewCheckBoxActivity extends BaseActivity {
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_check_box);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        DataAdater dataAdater = new DataAdater(getApplicationContext());
        recyclerView.setAdapter(dataAdater);
        List<DataModel.DataBean> dataBeanList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            DataModel dataModel = new DataModel();

            DataModel.DataBean dataBean = dataModel.new DataBean();
            dataBean.setTitle("Test" + i);
            dataBeanList.add(dataBean);
        }
        dataAdater.addAll(dataBeanList);

    }
}
