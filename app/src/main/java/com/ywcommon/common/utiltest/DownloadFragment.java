package com.ywcommon.common.utiltest;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ywcommon.common.R;
import com.ywcommon.common.utillib.util.FragmentUtils;

import org.jetbrains.annotations.NotNull;

/**
 * @author yaowang
 * @version 1.0
 * @date：2021/7/16
 * @packagename： com.ywcommon.common.utiltest
 */
public class DownloadFragment extends Fragment implements View.OnClickListener {
    private Button btnStartService;
    private Button btnStopService;
    private Button btnBindService;
    private Button btnUnBindService;
    private Button btnSingleTask;
    private MyTestService.MyBinder myBinder;
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myBinder = (MyTestService.MyBinder) service;
            myBinder.startDownload();
            Log.d("DownloadFragment", "onServiceConnected()");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("DownloadFragment", "onServiceDisconnected()");
        }

        @Override
        public void onNullBinding(ComponentName name) {
            Log.d("DownloadFragment", "onNullBinding()");
        }

        @Override
        public void onBindingDied(ComponentName name) {
            Log.d("DownloadFragment", "onBindingDied()");
        }
    };

    public static DownloadFragment newInstance() {
        Bundle args = new Bundle();
        DownloadFragment fragment = new DownloadFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_download,container,false);
    }


    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnStartService = view.findViewById(R.id.start_service);
        btnStopService = view.findViewById(R.id.stop_service);
        btnBindService = view.findViewById(R.id.bind_service);
        btnUnBindService = view.findViewById(R.id.unbind_service);
        btnSingleTask = view.findViewById(R.id.single_task);
        btnStartService.setOnClickListener(this);
        btnStopService.setOnClickListener(this);
        btnBindService.setOnClickListener(this);
        btnUnBindService.setOnClickListener(this);
        btnSingleTask.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.start_service:
                Intent startIntent = new Intent(getActivity(),MyTestService.class);
                getActivity().startService(startIntent);
                break;
            case R.id.stop_service:
                Intent stopService = new Intent(getActivity(),MyTestService.class);
                getActivity().stopService(stopService);
                break;
            case R.id.bind_service:
                Intent bindService = new Intent(getActivity(),MyTestService.class);
                getActivity().bindService(bindService, mConnection, Context.BIND_AUTO_CREATE);
                break;
            case R.id.unbind_service:
                getActivity().unbindService(mConnection);
                break;
            case R.id.single_task:
                FragmentUtils.replace(getFragmentManager(),
                        DownloadFragment2.newInstance(),R.id.fragment_container,true);
                break;
        }
    }
}
