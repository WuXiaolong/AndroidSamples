package com.wuxiaolong.androidsamples.designpatterns;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.wuxiaolong.androidsamples.R;
import com.wuxiaolong.androidutils.library.LogUtil;

/**
 * 职责链模式
 */
public class ChainOfResponsibilityActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chain_of_responsibility);
        Handler projectManager = new ProjectManager(3);
        Handler departmentManager = new DepartmentManager(5);
        Handler generalManager = new GeneralManager(15);

        //创建职责链
        projectManager.setNextHandler(departmentManager);
        departmentManager.setNextHandler(generalManager);

        //发起一次请求
        projectManager.handleRequest(10);
    }

    /**
     * 抽象处理者
     */
    public static abstract class Handler {
        private Handler nextHandler;
        public int maxDay;// 当前领导能审批通过的最多天数

        protected Handler(int maxDay) {
            this.maxDay = maxDay;
        }

        //设置责任链中下一个处理请求的对象
        public void setNextHandler(Handler handler) {
            nextHandler = handler;
        }

        protected void handleRequest(int day) {
            if (day <= maxDay) {
                reply(day);
            } else {
                if (nextHandler != null) {
                    //审批权限不够，继续上报
                    nextHandler.handleRequest(day);
                } else {
                    LogUtil.d("没有更高的领导审批了");
                }
            }
        }

        protected abstract void reply(int day);

    }

    /**
     * 项目经理
     */
    class ProjectManager extends Handler {
        public ProjectManager(int day) {
            super(day);
        }

        @Override
        protected void reply(int day) {
            LogUtil.d(day + "天请假，项目经理直接审批通过");
        }

    }

    /**
     * 部门经理
     */
    class DepartmentManager extends Handler {
        public DepartmentManager(int day) {
            super(day);
        }


        @Override
        protected void reply(int day) {
            LogUtil.d(day + "天请假，部门经理审批通过");
        }

    }

    /**
     * 总经理
     */
    class GeneralManager extends Handler {
        public GeneralManager(int day) {
            super(day);
        }

        @Override
        protected void reply(int day) {
            LogUtil.d(day + "天请假，总经理审批通过");
        }
    }

}
