package com.wuxiaolong.androidsamples.objecttosdcard;

import java.io.Serializable;

/**
 * Created by wuxiaolong on 2017/6/23.
 * 个人博客：http：//wuxiaolong.me
 */

public class TestModel implements Serializable {
    @Override
    public boolean equals(Object obj) {
        return true;
    }

    private  int a;

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    @Override
    public int hashCode() {
        return 1;
    }
}
