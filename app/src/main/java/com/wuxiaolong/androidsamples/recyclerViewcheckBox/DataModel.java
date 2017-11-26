package com.wuxiaolong.androidsamples.recyclerViewcheckBox;

import java.util.List;

/**
 * Created by WuXiaolong on 2017/9/25.
 * 个人博客：http：//wuxiaolong.me
 */

public class DataModel {
    private int code;
    private List<DataBean> dataBeanList;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<DataBean> getDataBeanList() {
        return dataBeanList;
    }

    public void setDataBeanList(List<DataBean> dataBeanList) {
        this.dataBeanList = dataBeanList;
    }

    public class DataBean {
        private String title;
        //自己新增标识，用来记录 CheckBox 状态
        private boolean checked;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public boolean isChecked() {
            return checked;
        }

        public void setChecked(boolean checked) {
            this.checked = checked;
        }
    }
}
