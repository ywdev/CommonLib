package com.ywcommon.common.utiltest;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.arialyy.annotations.Download;
import com.arialyy.aria.core.task.DownloadTask;
import com.ywcommon.common.utillib.util.Utils;
import com.ywcommon.common.utillib.util.toast.ToastUtils;

/**
 * @author yaowang
 * @version 1.0
 * @date：2021/7/19
 * @packagename： com.ywcommon.common.utiltest
 */
public class MyTestService extends Service {
    public static final String TAG = "MyTestService";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate() executed");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand() executed");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() executed");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    class MyBinder extends Binder {

        public void startDownload(){
            Log.d(TAG, "startDownload() executed");
        }

    }

}
