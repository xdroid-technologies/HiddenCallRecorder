package com.abc.recorder.utils;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public abstract class BaseUtils extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();

    }

    protected void init() {
        initView();
        initData();
        initEvent();
    }

    protected abstract void initView();


    protected abstract void initData();


    protected abstract void initEvent();

}
