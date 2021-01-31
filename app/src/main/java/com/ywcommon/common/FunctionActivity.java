package com.ywcommon.common;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ywcommon.common.utiltest.UtilBaseActivity;
import com.ywcommon.common.widgetlib.adapter.BaseQuickAdapter;

import java.util.ArrayList;

public class FunctionActivity extends AppCompatActivity implements BaseQuickAdapter.OnItemClickListener, View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function);
        ArrayList<String> functionList = getIntent().getStringArrayListExtra(Constant.EXTRA_FUNCTION_LIST);
        String title = getIntent().getStringExtra(Constant.EXTRA_TITLE);
        RecyclerView recyclerView = findViewById(R.id.recyclerView_function);
        Toolbar toolbar = findViewById(R.id.toolbar_function);
        toolbar.setNavigationOnClickListener(this);
        toolbar.setTitle(title==null?"":title);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MainAdapter mainAdapter = new MainAdapter(R.layout.item_main_list, null);
        recyclerView.setAdapter(mainAdapter);
        mainAdapter.setNewData(functionList);
        mainAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Intent intent = new Intent(this, UtilBaseActivity.class);
        intent.putExtra(Constant.EXTRA_TITLE,(String)adapter.getItem(position));
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        onBackPressed();
    }
}
