package com.ywcommon.common.utiltest;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.arialyy.aria.core.Aria;
import com.ywcommon.common.R;
import com.ywcommon.common.utillib.util.toast.ToastUtils;
import com.ywcommon.common.utiltest.listener.IDownloadListener;

import org.jetbrains.annotations.NotNull;

/**
 * @author yaowang
 * @version 1.0
 * @date：2021/7/19
 * @packagename： com.ywcommon.common.utiltest
 */
public class DownloadFragment2 extends Fragment implements View.OnClickListener, IDownloadListener {
    private ProgressBar progressBar;
    private TextView tvProgress;
    private Button btnStart;
    private Button btnPause;
    private Button btnStop;
    private SingleDownloadService downloadService;

    private SingleDownloadService.DownloadBinder myBinder;
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myBinder = (SingleDownloadService.DownloadBinder) service;
            downloadService = (SingleDownloadService) myBinder.getDownloadService();
            downloadService.setOnDownloadListener(DownloadFragment2.this);
            Log.d("DownloadFragment", "onServiceConnected()");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("DownloadFragment", "onServiceDisconnected()");
        }

    };

    public static DownloadFragment2 newInstance() {
        Bundle args = new Bundle();
        DownloadFragment2 fragment = new DownloadFragment2();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment2_download,container,false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBar = view.findViewById(R.id.progressBar);
        tvProgress = view.findViewById(R.id.tv_progress);
        btnStart = view.findViewById(R.id.btn_start);
        btnPause = view.findViewById(R.id.btn_pause);
        btnStop = view.findViewById(R.id.btn_stop);
        btnStart.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_start:
                break;
            case R.id.btn_pause:
                break;
            case R.id.btn_stop:
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onProgressChange(int progress) {
        ToastUtils.show("下载进度"+progress);
    }
}
