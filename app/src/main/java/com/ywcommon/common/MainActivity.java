package com.ywcommon.common;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tbruyelle.rxpermissions3.RxPermissions;
import com.ywcommon.common.demotest.MyViewModel;
import com.ywcommon.common.utillib.constant.PermissionConstants;
import com.ywcommon.common.utillib.util.log.LogUtils;
import com.ywcommon.common.utillib.util.permission.DialogHelper;
import com.ywcommon.common.utillib.util.permission.PermissionUtils;
import com.ywcommon.common.widgetlib.adapter.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BaseQuickAdapter.OnItemClickListener {
    private String [] strings = {
            "common-util",
            "common-widget",
            "common-test"
    };
    private String [] utilList = {
            "antishake",
            "download",
            "permission",
            "cache",
            "check",
            "convert",
            "crash",
            "device",
            "display",
            "encode",
            "encrypt",
            "file",
            "image",
            "json",
            "keyboard",
            "log",
            "net",
            "path",
            "regex",
            "resource",
            "shell",
            "sp",
            "thread",
            "time",
            "toast",
            "uri",
            "vibrate"
    };
    private String [] widgetList = {
            "adapter",
            "dialog"
    };
    private String [] testList = {
            "nestfunction",
            "jetpack",
            "SAF",
            "kotlinCoroutines"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MainAdapter mainAdapter = new MainAdapter(R.layout.item_main_list, Arrays.asList(strings));
        recyclerView.setAdapter(mainAdapter);
        mainAdapter.setOnItemClickListener(this);
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.requestEach(Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(permission -> {
                    if (permission.granted) { // Always true pre-M

                    } else if(permission.shouldShowRequestPermissionRationale){

                    } else {

                    }
                });



    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Intent intent = new Intent(MainActivity.this, FunctionActivity.class);
        if(position == 0){
            intent.putStringArrayListExtra(Constant.EXTRA_FUNCTION_LIST, new ArrayList<>(Arrays.asList(utilList)));
            intent.putExtra(Constant.EXTRA_TITLE, "utils");
        }else if(position == 1){
            intent.putStringArrayListExtra(Constant.EXTRA_FUNCTION_LIST, new ArrayList<>(Arrays.asList(widgetList)));
            intent.putExtra(Constant.EXTRA_TITLE, "widgets");
        }else if(position == 2){
            intent.putStringArrayListExtra(Constant.EXTRA_FUNCTION_LIST, new ArrayList<>(Arrays.asList(testList)));
            intent.putExtra(Constant.EXTRA_TITLE, "tests");
        }
        startActivity(intent);

    }


}
