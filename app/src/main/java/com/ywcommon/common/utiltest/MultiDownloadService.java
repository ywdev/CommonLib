package com.ywcommon.common.utiltest;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

/**
 * @author yaowang
 * @version 1.0
 * @date：2021/7/22
 * @packagename： com.ywcommon.common.utiltest
 */
public class MultiDownloadService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    class MultiDownloadBinder extends Binder {

        public Service getMultiDownloadService() {
            return MultiDownloadService.this;
        }

    }
}
