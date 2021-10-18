package com.ywcommon.common;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.ywcommon.common.utillib.util.toast.ToastUtils;

public class CommonUtilApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ToastUtils.init(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
