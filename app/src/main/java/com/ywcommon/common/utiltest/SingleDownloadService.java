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

import static com.arialyy.aria.core.inf.IEntity.STATE_CANCEL;
import static com.arialyy.aria.core.inf.IEntity.STATE_COMPLETE;
import static com.arialyy.aria.core.inf.IEntity.STATE_FAIL;
import static com.arialyy.aria.core.inf.IEntity.STATE_RUNNING;
import static com.arialyy.aria.core.inf.IEntity.STATE_STOP;
import static com.arialyy.aria.core.inf.IEntity.STATE_WAIT;

/**
 * @author yaowang
 * @version 1.0
 * @date：2021/7/19
 * @packagename： com.ywcommon.common.utiltest
 */
public class SingleDownloadService extends Service {
//    private String mUrl = "https://testfile-1258218961.cos.ap-guangzhou.myqcloud.com/agmobile_test.zip";
//    private String mUrl = "http://219.142.101.168:8081/file/offline/110000/110100/110111/阎村镇-房屋调查-59951d2a60931ede43839d17bdeb99bf.zjbrc";
    private String mUrl = "http://192.168.3.9:8080/downloads/阎村镇-房屋调查-59951d2a60931ede43839d17bdeb99bf.zjbrc";
    private DownloadBinder binder = new DownloadBinder();
    private IDownloadListener listener;
    private long taskId;
    private int mState = STATE_WAIT;


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

    class DownloadBinder extends Binder {

        public Service getDownloadService() {
            return SingleDownloadService.this;
        }

    }

    public void startDownload() {
        Aria.get(SingleDownloadService.this).getDownloadConfig().setMaxSpeed(512);
        taskId = Aria.download(SingleDownloadService.this)
                .load(mUrl)
                .setFilePath(getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS) + "/test3.zip")
                .create();
    }

    public void stopDownload() {
        Aria.download(SingleDownloadService.this).load(taskId).stop();
    }

    public void cancelDownload(){
        Aria.download(SingleDownloadService.this).load(taskId).cancel();
    }

    public void resumeDownload(){
        Aria.download(SingleDownloadService.this).load(taskId).resume();
    }

    public void setOnDownloadListener(IDownloadListener listener) {
        this.listener = listener;
    }

    public int getState(){
        return mState;
    }


    @Download.onNoSupportBreakPoint
    protected void onNoSupportBreakPoint(DownloadTask task) {
        ToastUtils.show("onNoSupportBreakPoint");
    }

    @Download.onWait
    protected void onWait(DownloadTask task) {
        ToastUtils.show("onWait");
    }


    @Download.onTaskStart
    protected void onTaskStart(DownloadTask task) {
        ToastUtils.show("onTaskStart");
    }


    @Download.onTaskStop
    protected void onTaskStop(DownloadTask task) {
        ToastUtils.show("onTaskStop");
        mState = STATE_STOP;
    }

    @Download.onTaskCancel
    public void onTaskCancel(DownloadTask task) {
        ToastUtils.show("onTaskCancel");
        mState = STATE_CANCEL;
    }

    @Download.onTaskFail
    public void onTaskFail(DownloadTask task) {
        ToastUtils.show("onTaskFail");
        mState = STATE_FAIL;
    }


    @Download.onTaskRunning
    protected void onTaskRunning(DownloadTask task) {
        ToastUtils.show("onTaskRunning");
        listener.onProgressChange(task.getPercent());
        mState = STATE_RUNNING;
    }

    @Download.onTaskComplete
    protected void onTaskComplete(DownloadTask task) {
        ToastUtils.show("onTaskComplete");
        mState = STATE_COMPLETE;
    }

}
