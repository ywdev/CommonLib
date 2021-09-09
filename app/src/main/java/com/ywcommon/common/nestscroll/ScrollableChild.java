package com.ywcommon.common.nestscroll;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ywcommon.common.R;
import com.ywcommon.common.widgetlib.adapter.BaseQuickAdapter;
import com.ywcommon.common.widgetlib.adapter.BaseViewHolder;

import java.util.List;


/**
 * @author 创建人 ：yaowang
 * @version 1.0
 * @package 包名 ：com.appdev.toolkit.nestscroll
 * @createTime 创建时间 ：2019/4/18
 * @modifyBy 修改人 ：
 * @modifyTime 修改时间 ：
 * @modifyMemo 修改备注：
 */
public abstract class ScrollableChild<T> extends Fragment implements BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemLongClickListener {
    protected RecyclerView.OnScrollListener onScrollListener;
    protected RecyclerView mChildRecyclerView;
    protected BaseQuickAdapter<T, BaseViewHolder> mAdapter;

    public abstract RecyclerView getChildRecyclerView();

    public void setOnScrollListener(RecyclerView.OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_recyclerview, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initArguments();
        initView(view);
    }

    protected void initArguments() {

    }

    protected void initView(View view) {
        mChildRecyclerView = view.findViewById(R.id.rv_recyclerView);
        mAdapter = initAdapter();
        mChildRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mChildRecyclerView.setFocusable(false);
        mChildRecyclerView.addOnScrollListener(onScrollListener);
        mChildRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnItemLongClickListener(this);
        loadData();
    }

    protected abstract void loadData();


    /**
     * 子类在此方法中初始化列表所需Adapter
     *
     * @return adapter
     */
    protected abstract BaseQuickAdapter<T, BaseViewHolder> initAdapter();


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
        return false;
    }
}
