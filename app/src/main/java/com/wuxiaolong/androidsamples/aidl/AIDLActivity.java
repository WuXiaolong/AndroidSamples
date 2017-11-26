package com.wuxiaolong.androidsamples.aidl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.wuxiaolong.aidlservice.Book;
import com.wuxiaolong.aidlservice.IBookManager;
import com.wuxiaolong.androidsamples.R;

import java.util.List;

public class AIDLActivity extends AppCompatActivity {
    private boolean isConnSuccess;
    private IBookManager iBookManager;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d("wxl","onServiceConnected");
            iBookManager = IBookManager.Stub.asInterface(iBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d("wxl","onServiceDisconnected");
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("wxl","onStart");
        //Unable to start service Intent
        Intent intent = new Intent();
        //注意不是服务端的Service所在的包名，而是服务端的app包名。
        //这里多说两句的就是~在4.4之前只需要setAction就可以绑定远程服务了，但是5.0之后就不能够这样绑定了，原因是什么不安全~为了兼容我们也写上setpackage

        intent.setAction("com.wuxiaolong.aidlservice.RemoteService");
        intent.setPackage("com.wuxiaolong.aidlservice");
        isConnSuccess =  bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        Log.d("wxl","isConnSuccess="+isConnSuccess);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl);


        EditText bookId = (EditText) findViewById(R.id.bookId);
        EditText bookName = (EditText) findViewById(R.id.bookName);
        TextView text = (TextView) findViewById(R.id.text);
        findViewById(R.id.addBook).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isConnSuccess&&!TextUtils.isEmpty(bookId.getText()) && !TextUtils.isEmpty(bookName.getText())) {
                    Book book = new Book(Integer.parseInt(bookId.getText().toString()), bookName.getText().toString());
                    try {
                        iBookManager.addBook(book);
                        List<Book> bookList = iBookManager.getBookList();

                        text.setText(bookList.get(0).bookName);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
    }
}
