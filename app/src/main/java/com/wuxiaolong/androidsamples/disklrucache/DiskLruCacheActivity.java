package com.wuxiaolong.androidsamples.disklrucache;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jakewharton.disklrucache.DiskLruCache;
import com.wuxiaolong.androidsamples.R;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * https://github.com/JakeWharton/DiskLruCache
 */
public class DiskLruCacheActivity extends Activity {
    private DiskLruCache mDiskLruCache;
    private String IMGIP = "http://f.hiphotos.baidu.com/image/pic/item/58ee3d6d55fbb2fbfe951a134d4a20a44623dc71.jpg";
    ImageView imageView;
    TextView cacheSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disk_lru_cache);
        imageView = (ImageView) findViewById(R.id.imageView);
        cacheSize = (TextView) findViewById(R.id.cacheSize);
        initDiskLruCache();
    }

    private void initDiskLruCache() {
        try {
            File cacheDir = getDiskCacheDir(this, "bitmap");
            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }
            mDiskLruCache = DiskLruCache.open(cacheDir, getAppVersion(this), 1,
                    10 * 1024 * 1024);
        } catch (
                IOException e
                )

        {
            e.printStackTrace();
        }

    }

    public void onCache(View view) {
        switch (view.getId()) {
            case R.id.cacheImageView:
                cacheImageView();
                break;
            case R.id.showCacheImageView:
                showCacheImageView();
                break;
            case R.id.deleteCacheImageView:
                clearCache();
                break;
            case R.id.deleteAllCacheImageView:
                deleteAll();
                break;
        }
    }

    private void getCacheSize() {
        cacheSize.setText(mDiskLruCache.size() + "");
    }

    private void cacheImageView() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                String key = MD5Util.md5(IMGIP);
                Log.d("wxl", "key=" + key);
                try {
                    DiskLruCache.Editor editor = mDiskLruCache.edit(key);
                    if (editor != null) {
                        OutputStream out = editor.newOutputStream(0);
                        if (downloadImg(IMGIP, out)) {
                            //提交
                            editor.commit();
                        } else {
                            //撤销操作
                            editor.abort();
                        }
                    }
                    /**
                     * 这个方法用于将内存中的操作记录同步到日志文件（也就是journal文件）当中。
                     * 这个方法非常重要，因为DiskLruCache能够正常工作的前提就是要依赖于journal文件中的内容。
                     * 并不是每次写入缓存都要调用一次flush()方法的，频繁地调用并不会带来任何好处，
                     * 只会额外增加同步journal文件的时间。
                     * 比较标准的做法就是在Activity的onPause()方法中去调用一次flush()方法就可以了
                     */
                    mDiskLruCache.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void showCacheImageView() {
        String key = MD5Util.md5(IMGIP);
        try {
            DiskLruCache.Snapshot snapShot = mDiskLruCache.get(key);
            if (snapShot != null) {
                InputStream is = snapShot.getInputStream(0);
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                imageView.setImageBitmap(bitmap);
                getCacheSize();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void clearCache() {
        String key = MD5Util.md5(IMGIP);
        try {
            mDiskLruCache.remove(key);
            getCacheSize();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteAll() {
        /**
         * 这个方法用于将所有的缓存数据全部删除，比如说网易新闻中的那个手动清理缓存功能，
         * 其实只需要调用一下DiskLruCache的delete()方法就可以实现了。
         * 会删除包括日志文件在内的所有文件
         */
        try {
            mDiskLruCache.delete();
            getCacheSize();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        //如果sd卡存在并且没有被移除
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }

    private int getAppVersion(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }

    private boolean downloadImg(final String urlStr, final OutputStream outputStream) {
        HttpURLConnection conn = null;
        BufferedOutputStream out = null;
        BufferedInputStream in = null;
        try {
            URL url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(conn.getInputStream(), 8 * 1024);
            out = new BufferedOutputStream(outputStream, 8 * 1024);
            int len = 0;
            while ((len = in.read()) != -1) {
                out.write(len);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (conn != null)
                conn.disconnect();
            try {
                if (out != null)
                    out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (in != null)
                    in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            mDiskLruCache.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /**
         * 这个方法用于将DiskLruCache关闭掉，是和open()方法对应的一个方法。
         * 关闭掉了之后就不能再调用DiskLruCache中任何操作缓存数据的方法，
         * 通常只应该在Activity的onDestroy()方法中去调用close()方法。
         */
        try {
            mDiskLruCache.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
