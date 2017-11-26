package com.wuxiaolong.androidsamples;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.folioreader.activity.FolioActivity;

public class EPubActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_epub);
        Intent intent = new Intent(this, FolioActivity.class);
        intent.putExtra(FolioActivity.INTENT_EPUB_SOURCE_TYPE, FolioActivity.EpubSourceType.ASSESTS);
        intent.putExtra(FolioActivity.INTENT_EPUB_SOURCE_PATH, "laorenyuhai.epub");
        startActivity(intent);
    }
}
