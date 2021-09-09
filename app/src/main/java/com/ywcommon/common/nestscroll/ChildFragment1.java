package com.ywcommon.common.nestscroll;

import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;

import com.ywcommon.common.R;
import com.ywcommon.common.widgetlib.adapter.BaseQuickAdapter;
import com.ywcommon.common.widgetlib.adapter.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yaowang
 * @version 1.0
 * @date：2021/9/9
 * @packagename： com.ywcommon.common.nestscroll
 */
public class ChildFragment1 extends ScrollableChild<MyEntity>{

    public static ChildFragment1 newInstance() {
        Bundle args = new Bundle();
        ChildFragment1 fragment = new ChildFragment1();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public RecyclerView getChildRecyclerView() {
        return mChildRecyclerView;
    }

    @Override
    protected void loadData() {
        List<MyEntity> data = new ArrayList<>();
        for (int i = 0;i<100 ;i++){
            MyEntity myEntity = new MyEntity();
            myEntity.setId(i+1+"");
            myEntity.setTitle("第"+(i+1)+"个");
            myEntity.setContent("刘备");
            data.add(myEntity);
        }
        mAdapter.setNewData(data);
    }

    @Override
    protected BaseQuickAdapter<MyEntity, BaseViewHolder> initAdapter() {
        return new NestChildAdapter(R.layout.item_child_recyclerview);
    }


}
