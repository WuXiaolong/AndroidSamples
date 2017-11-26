package com.wuxiaolong.androidsamples.objecttosdcard;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;

import com.wuxiaolong.androidsamples.R;
import com.wuxiaolong.androidutils.library.LogUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ObjectToSDCardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object_to_sdcard);
        /**
         * 目标文件存储的文件夹路径
         */
        String destFileDir = Environment.getExternalStorageDirectory() + "/ObjectToSDCard/";
        /**
         * 目标文件存储的文件名
         */
        String destFileName = "qq";
        TestModel testModel = new TestModel();
        testModel.setA(1);
        writeSDCard(testModel, destFileDir, destFileName);

        testModel = (TestModel) readSDCard(destFileDir, destFileName);
        LogUtil.d("testModel=" + testModel.toString());
    }

    /**
     * 序列化
     */
    private void writeSDCard(Object obj, String destFileDir, String destFileName) {

        File destFile = new File(destFileDir);
        if (!destFile.exists()) {
            destFile.mkdirs();
        }
        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(new File(destFileDir, destFileName));
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(obj);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (objectOutputStream != null) {
                    objectOutputStream.close();
                    objectOutputStream = null;
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                    fileOutputStream = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    /**
     * 反序列化
     */
    private Object readSDCard(String destFileDir, String destFileName) {
        FileInputStream fileInputStream = null;

        Object object = null;
        ObjectInputStream objectInputStream = null;

        try {
            fileInputStream = new FileInputStream(new File(destFileDir,destFileName));
            objectInputStream = new ObjectInputStream(fileInputStream);
            object = objectInputStream.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (objectInputStream != null) {
                    objectInputStream.close();
                    objectInputStream = null;
                }
                if (fileInputStream != null) {
                    fileInputStream.close();
                    fileInputStream = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return object;

    }


}
