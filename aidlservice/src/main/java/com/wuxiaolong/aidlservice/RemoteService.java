package com.wuxiaolong.aidlservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by wuxiaolong on 2017/6/30.
 * 个人博客：http：//wuxiaolong.me
 */

public class RemoteService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    //支持并发读写
    private CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<>();



    private final IBookManager.Stub mBinder=new IBookManager.Stub(){

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public List<Book> getBookList() throws RemoteException {
            return mBookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            mBookList.add(book);
        }
    };
}
