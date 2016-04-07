package com.wuxiaolong.androidsamples;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

public class CustomInputActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_input);
        final EditText phoneFormat = (EditText) findViewById(R.id.phoneFormat);
        final EditText timeFormat = (EditText) findViewById(R.id.timeFormat);
        phoneFormat(phoneFormat);
        timeFormat(timeFormat);
    }

    void phoneFormat(EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (charSequence == null || charSequence.length() == 0)
                    return;
                StringBuilder stringBuilder = new StringBuilder();
//                for (int i = 0; i < charSequence.length(); i++) {
//                    if (i != 3 && i != 8 && charSequence.charAt(i) == ' ') {
//                        continue;
//                    } else {
//                        stringBuilder.append(charSequence.charAt(i));
//                        if ((stringBuilder.length() == 4 || stringBuilder.length() == 9)
//                                && stringBuilder.charAt(stringBuilder.length() - 1) != ' ') {
//                            stringBuilder.insert(stringBuilder.length() - 1, ' ');
//                        }
//                    }
//                }
                for (int i = 0; i < charSequence.length(); i++) {
//                    if (i == 3 && i == 8 && charSequence.charAt(i) != ' ') {
//                        continue;
//                    } else {
                    stringBuilder.append(charSequence.charAt(i));
                    if ((stringBuilder.length() == 4 || stringBuilder.length() == 9)
                            && stringBuilder.charAt(stringBuilder.length() - 1) != ' ') {
                        stringBuilder.insert(stringBuilder.length() - 1, ' ');
                    }
//                    }
                }
                Log.d("wxl", "start=" + start + ",before=" + before + "---stringBuilder.toString()=" + stringBuilder.toString() + ",charSequence.toString()=" + charSequence.toString());
                if (!stringBuilder.toString().equals(charSequence.toString())) {
                    int index = start + 1;//start 为开始变化位置的索引，从0开始计数；
                    if (stringBuilder.charAt(start) == ' ') {
                        if (before == 0) {//charSequence由1变为12，before为0
                            index++;
                        } else {
                            index--;
                        }
                    } else {
                        if (before == 1) {//charSequence由12变为1，before为0
                            index--;
                        }
                    }
                    Log.d("wxl", "index=" + index);
                    editText.setText(stringBuilder.toString());
                    editText.setSelection(index);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    void timeFormat(EditText editText) {
//        List<String> charSequenceList = new ArrayList<>();
//        editText.addTextChangedListener(
//                new TextWatcher() {
//                    @Override
//                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                    }
//
//                    @Override
//                    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
//                        if (charSequence == null || charSequence.length() == 0)
//                            return;
//                        charSequenceList.removeAll(charSequenceList);
//                        StringBuilder stringBuilder = new StringBuilder();
//                        for (int i = 0; i < charSequence.length(); i++) {
////                            stringBuilder.append(charSequence.charAt(i));
////                            if ((stringBuilder.length() == 3)) {
////                                stringBuilder.append(" ");
////                            }
//                            if (i == 2) {
//                                charSequenceList.add(":");
//                            } else {
//                                charSequenceList.add(charSequence.charAt(i) + "");
//                            }
//                        }
//                        for (int i = 0; i < charSequenceList.size(); i++) {
//                            stringBuilder.append(charSequenceList.get(i));
//                        }
//
//                        Log.d("wxl", "start=" + start + ",before=" + before + "---stringBuilder.toString()=" + stringBuilder.toString() + ",charSequence.toString()=" + charSequence.toString());
//                        if (!stringBuilder.toString().equals(charSequence.toString())) {
//                            int index = before;//start 为开始变化位置的索引，从0开始计数；
//                            if (before == 0) {//charSequence由1变为12，before为0
//                                editText.setSelection(3);
//                            } else if (before == 1) {
//                                editText.setSelection(2);
//                            }
//
////                            Log.d("wxl", "index=" + index);
//                            editText.setText(stringBuilder.toString());
//
//                        }
//                    }
//
//                    @Override
//                    public void afterTextChanged(Editable s) {
//
//                    }
//                }
//
//        );
    }
}
