package com.ywcommon.common;

import androidx.annotation.Nullable;

import com.ywcommon.common.widgetlib.adapter.BaseQuickAdapter;
import com.ywcommon.common.widgetlib.adapter.BaseViewHolder;

import java.util.List;

public class MainAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    MainAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_name, item);
    }
}
