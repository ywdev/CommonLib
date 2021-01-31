package com.ywcommon.common;

import android.app.Application;

import com.ywcommon.common.utillib.util.toast.ToastUtils;

public class CommonUtilApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ToastUtils.init(this);
    }
}
