package com.wuxiaolong.androidsamples.downloadnotify;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;

import com.wuxiaolong.androidsamples.R;

import java.util.Random;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class DownloadActivity extends DownloadBaseActivity implements View.OnClickListener {

    @InjectView(R.id.btn_download_start)
    Button btn_download_start;
    @InjectView(R.id.btn_download_pause)
    Button btn_download_pause;
    @InjectView(R.id.btn_download_cancel)
    Button btn_download_cancel;
    /**
     * Notification的ID
     */
    int notifyId = 1000;
    /**
     * Notification的进度条数值
     */
    int progress = 0;

    /**
     * 下载线程是否暂停
     */
    public boolean isPause = false;
    /**
     * 通知栏内是否是自定义的
     */
    public boolean isCustom = false;
    DownloadThread downloadThread;
    private Intent contentIntent;
    private PendingIntent contentPendingIntent;
    private NotificationCompat.Builder notifyBuilder;
    private PendingIntent pausePendingIntent, resumePendingIntent, cancelPendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        ButterKnife.inject(this);
//        actionBar.setTitle("下载");
        initView();
        initNotify(true);
        initBroadcastReceiver();
    }

    private void initView() {
        btn_download_start.setOnClickListener(this);
        btn_download_pause.setOnClickListener(this);
        btn_download_cancel.setOnClickListener(this);
    }

    /**
     * 初始化通知栏
     */
    private void initNotify(boolean isPause) {
        notifyBuilder = new NotificationCompat.Builder(this);
        contentIntent = new Intent(this, DownloadingActivity.class);
        contentIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        Random rand = new Random();
        /**
         * 暂停
         */
        Intent pauseIntent = new Intent(ACTION_BUTTON);
        pauseIntent.putExtra(INTENT_BUTTONID_TAG, PAUSE_ID);
        pauseIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        pausePendingIntent = PendingIntent.getBroadcast(this, rand.nextInt(1000),
                pauseIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        /**
         * 继续
         */
        Intent resumeIntent = new Intent(ACTION_BUTTON);
        resumeIntent.putExtra(INTENT_BUTTONID_TAG, RESUME_ID);
        resumeIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        resumePendingIntent = PendingIntent.getBroadcast(this, rand.nextInt(1000),
                resumeIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        /**
         * 取消
         */
        Intent cancelIntent = new Intent(ACTION_BUTTON);
        cancelIntent.putExtra(INTENT_BUTTONID_TAG, CANCEL_ID);
        cancelIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        cancelPendingIntent = PendingIntent.getBroadcast(this, rand.nextInt(1000),
                cancelIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        contentPendingIntent = PendingIntent.getActivity(this, 0, contentIntent, 0);
        if (isPause) {
            notifyBuilder
                    .addAction(R.mipmap.ic_action_coffee, "暂停", pausePendingIntent);
        } else {
            notifyBuilder
                    .addAction(R.mipmap.ic_action_rocket, "继续", resumePendingIntent);
        }
        notifyBuilder
                .addAction(R.mipmap.ic_action_cancel, "停止", cancelPendingIntent)
                .setContentIntent(contentPendingIntent).setWhen(System.currentTimeMillis())// 通知产生的时间，会在通知信息里显示
                // .setNumber(number)//显示数量
                .setPriority(Notification.PRIORITY_DEFAULT)// 设置该通知优先级
                        // .setAutoCancel(true)//设置这个标志当用户单击面板就可以让通知将自动取消
                .setOngoing(false)// ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
                        // .setDefaults(Notification.DEFAULT_VIBRATE)//
                        // 向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合：
                        // Notification.DEFAULT_ALL Notification.DEFAULT_SOUND 添加声音 //
                        // requires VIBRATE permission
                .setSmallIcon(R.mipmap.ic_launcher);// .setSmallIcon(android.R.drawable.stat_sys_download)
    }


    /**
     * 开始下载
     */
    public void startDownloadNotify() {
        isPause = false;

        if (downloadThread != null && downloadThread.isAlive()) {
            // downloadThread.start();
        } else {
            downloadThread = new DownloadThread();
            downloadThread.start();
        }
    }

    /**
     * 暂停下载
     */
    public void pauseDownloadNotify() {
        isPause = true;
        if (!isCustom) {
            notifyBuilder.setContentTitle("已暂停");
            setNotify(progress);
        } else {
            showCustomProgressNotify("已暂停");
        }
    }

    /**
     * 取消下载
     */
    public void stopDownloadNotify() {
        if (downloadThread != null) {
            downloadThread.interrupt();
        }
        downloadThread = null;
        if (!isCustom) {
            notifyBuilder.setContentTitle("下载已取消").setProgress(0, 0, false);
            mNotificationManager.notify(notifyId, notifyBuilder.build());
        } else {
            showCustomProgressNotify("下载已取消");
        }
    }

    /**
     * 设置下载进度
     */
    public void setNotify(int progress) {
        // 这个方法是显示进度条
        notifyBuilder.setContentTitle("下载中");
        notifyBuilder.setProgress(100, progress, false);

        mNotificationManager.notify(notifyId, notifyBuilder.build());
    }

    /**
     * 显示自定义的带进度条通知栏
     */
    private void showCustomProgressNotify(String status) {
        RemoteViews mRemoteViews = new RemoteViews(getPackageName(),
                R.layout.view_custom_progress);
        mRemoteViews.setImageViewResource(R.id.custom_progress_icon,
                R.mipmap.ic_launcher);
        mRemoteViews.setTextViewText(R.id.tv_custom_progress_title, "小尛龙");
        mRemoteViews.setTextViewText(R.id.tv_custom_progress_status, status);
        if (progress >= 100 || downloadThread == null) {
            mRemoteViews.setProgressBar(R.id.custom_progressbar, 0, 0, false);
            mRemoteViews.setViewVisibility(R.id.custom_progressbar, View.GONE);
        } else {
            mRemoteViews.setProgressBar(R.id.custom_progressbar, 100, progress,
                    false);
            mRemoteViews.setViewVisibility(R.id.custom_progressbar,
                    View.VISIBLE);
        }
        // 点击的事件处理
        Intent closeIntent = new Intent(ACTION_BUTTON);
        /* 上一首按钮 */
        closeIntent.putExtra(INTENT_BUTTONID_TAG, CLOSE_ID);
        // 这里加了广播，所及INTENT的必须用getBroadcast方法
        PendingIntent intent_close = PendingIntent.getBroadcast(this, 1,
                closeIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        mRemoteViews.setOnClickPendingIntent(R.id.close, intent_close);


        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(this);
        notifyBuilder.setContent(mRemoteViews).setContentIntent(getDefalutIntent(Notification.FLAG_ONGOING_EVENT))
                .setTicker("开始下载")
                .setWhen(System.currentTimeMillis())// 通知产生的时间，会在通知信息里显示
                        // .setNumber(number)//显示数量
                .setPriority(Notification.PRIORITY_DEFAULT)// 设置该通知优先级
                        // .setAutoCancel(true)//设置这个标志当用户单击面板就可以让通知将自动取消
                .setOngoing(false)// ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
                        // .setDefaults(Notification.DEFAULT_VIBRATE)//
                        // 向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合：
                        // Notification.DEFAULT_ALL Notification.DEFAULT_SOUND 添加声音 //
                        // requires VIBRATE permission
                .setSmallIcon(R.mipmap.ic_launcher);// .setSmallIcon(android.R.drawable.stat_sys_download)

        Notification notify = notifyBuilder.build();
        notify.flags = Notification.FLAG_ONGOING_EVENT;
        mNotificationManager.notify(notifyId, notify);
    }

    /**
     * 下载线程
     */
    class DownloadThread extends Thread {

        @Override
        public void run() {
            int now_progress = 0;

            // Do the "lengthy" operation 20 times
            while (now_progress <= 100) {
                // Sets the progress indicator to a max value, the
                // current completion percentage, and "determinate"
                if (downloadThread == null) {
                    break;
                }
                if (!isPause) {
                    progress = now_progress;
                    if (isCustom) {
                        showCustomProgressNotify("下载中");

                    } else {

                        setNotify(progress);
                    }
                    now_progress += 1;
                }
                try {
                    // Sleep for 1 seconds
                    Thread.sleep(1 * 1000);
                } catch (InterruptedException e) {
                }
            }
            // When the loop is finished, updates the notification
            if (downloadThread != null) {
                if (isCustom) {
                    showCustomProgressNotify("下载完成");
                } else {
                    notifyBuilder.setContentText("下载完成")
                            // Removes the progress bar
                            .setProgress(0, 0, false);
                    notifyBuilder.setContentTitle("下载OK");
                    mNotificationManager.notify(notifyId, notifyBuilder.build());
                }
            }
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_download_start:
                startDownloadNotify();

                break;
            case R.id.btn_download_pause:
                pauseDownloadNotify();
                break;
            case R.id.btn_download_cancel:
                stopDownloadNotify();
                break;
            default:
                break;
        }
    }


    public MyBroadcastReceiver myBroadcastReceiver;

    /**
     * 带按钮的通知栏点击广播接收
     */
    public void initBroadcastReceiver() {
        myBroadcastReceiver = new MyBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_BUTTON);
        registerReceiver(myBroadcastReceiver, intentFilter);
    }

    //通知栏按钮点击事件对应的ACTION

    public final static String ACTION_BUTTON = "com.notifications.intent.action.ButtonClick";
    public final static String INTENT_BUTTONID_TAG = "ButtonId";
    public final static int CLOSE_ID = 1;
    public final static int PAUSE_ID = 2;
    public final static int CANCEL_ID = 3;
    public final static int RESUME_ID = 4;

    /**
     * 广播监听按钮点击时间
     */
    public class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(ACTION_BUTTON)) {
                // 通过传递过来的ID判断按钮点击属性或者通过getResultCode()获得相应点击事件
                int buttonId = intent.getIntExtra(INTENT_BUTTONID_TAG, 0);
                switch (buttonId) {
                    case CLOSE_ID:
                        stopDownloadNotify();
                        mNotificationManager.cancelAll();
                        break;
                    case PAUSE_ID:
                        initNotify(false);
                        pauseDownloadNotify();
                        break;
                    case RESUME_ID:
                        initNotify(true);
                        startDownloadNotify();
                        break;
                    case CANCEL_ID:
                        stopDownloadNotify();
                        mNotificationManager.cancel(notifyId);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        if (myBroadcastReceiver != null) {
            unregisterReceiver(myBroadcastReceiver);
        }
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.download, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        stopDownloadNotify();
        progress = 0;
        mNotificationManager.cancelAll();

        if (id == R.id.action_default_progress) {

            isCustom = false;
            return true;
        }
        if (id == R.id.action_custom_progress) {
            isCustom = true;
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
