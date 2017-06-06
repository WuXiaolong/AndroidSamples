package com.wuxiaolong.androidsamples.html5;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.wuxiaolong.androidsamples.R;
import com.wuxiaolong.androidsamples.html5.Html5View;

public class Html5Activity extends AppCompatActivity {
    Html5View html5View;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_html5);
        html5View= (Html5View) findViewById(R.id.html5View);
        html5View.loadDataWithBaseURL("<script language='JavaScript' type='text/javascript'>var iWidth=900;var iHeight=600;var iLeft = (window.screen.availWidth-10-iWidth)/2;var iTop = (window.screen.availHeight-30-iHeight)/2;\tfunction openwin(iWidth,iHeight,iLeft,iTop) {var openWindow = window.open ('', 'newwindow', 'height=' + iHeight + ',width='+iWidth+',top='+iTop+',left='+iLeft+',toolbar =no,menubar=no,titlebar=no,scrollbars=no,resizable=no,location=no,status=no');openWindow.document.write(\"<div id='1470' style='width:900px;height:600px;overflow:hidden;border:solid #333333 1px;background:#999999;display: -webkit-flex;display: flex; -webkit-align-items: center;align-items: center;-webkit-justify-content: center;justify-content: center;'></div>\");openWindow.document.close();}window.onload=function(){openwin(iWidth,iHeight,iLeft,iTop);}</script>");

    }


}
