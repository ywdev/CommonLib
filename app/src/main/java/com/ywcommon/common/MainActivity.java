package com.ywcommon.common;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ywcommon.common.widgetlib.adapter.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements BaseQuickAdapter.OnItemClickListener {
    private String [] strings = {
            "common-util",
            "common-widget"
    };
    private String [] utilList = {
            "antishake",
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
            "permission",
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MainAdapter mainAdapter = new MainAdapter(R.layout.item_main_list, Arrays.asList(strings));
        recyclerView.setAdapter(mainAdapter);
        mainAdapter.setOnItemClickListener(this);
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
        }
        startActivity(intent);

    }


}
