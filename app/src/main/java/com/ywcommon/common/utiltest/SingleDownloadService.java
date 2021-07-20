package com.ywcommon.common.utiltest;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.arialyy.annotations.Download;
import com.arialyy.aria.core.Aria;
import com.arialyy.aria.core.task.DownloadTask;
import com.ywcommon.common.utillib.util.toast.ToastUtils;
import com.ywcommon.common.utiltest.listener.IDownloadListener;

/**
 * @author yaowang
 * @version 1.0
 * @date：2021/7/19
 * @packagename： com.ywcommon.common.utiltest
 */
public class SingleDownloadService extends Service {
    private String mUrl = "https://testfile-1258218961.cos.ap-guangzhou.myqcloud.com/agmobile_test.zip";
    private DownloadBinder binder = new DownloadBinder();
    private IDownloadListener listener;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Aria.download(this).register();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Aria.download(this).unRegister();
    }

    class DownloadBinder extends Binder{

        public Service getDownloadService(){
            return SingleDownloadService.this;
        }

    }

    public void startDownload(){
        Aria.download(SingleDownloadService.this)
                .load(mUrl)
                .setFilePath(Environment.getExternalStorageDirectory() + "/Download/test.zip");
    }

    public void setOnDownloadListener(IDownloadListener listener){
        this.listener = listener;
    }


    @Download.onNoSupportBreakPoint
    protected void onNoSupportBreakPoint(DownloadTask task) {
        ToastUtils.show("onNoSupportBreakPoint");
    }

    @Download.onTaskStart
    protected void onTaskStart(DownloadTask task) {
        ToastUtils.show("onTaskStart");
    }


    @Download.onTaskStop
    protected void onTaskStop(DownloadTask task){
        ToastUtils.show("onTaskStop");
    }

    @Download.onTaskCancel
    public void onTaskCancel(DownloadTask task) {
        ToastUtils.show("onTaskCancel");
    }

    @Download.onTaskFail
    public void onTaskFail(DownloadTask task) {
        ToastUtils.show("onTaskFail");
    }


    @Download.onTaskRunning
    protected void onTaskRunning(DownloadTask task) {
        ToastUtils.show("onTaskRunning");
        listener.onProgressChange(task.getPercent());
    }

    @Download.onTaskComplete
    protected void onTaskComplete(DownloadTask task) {
        ToastUtils.show("onTaskComplete");
    }

}
