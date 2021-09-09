package com.ywcommon.common.nestscroll;

import androidx.annotation.Nullable;

import com.ywcommon.common.R;
import com.ywcommon.common.widgetlib.adapter.BaseQuickAdapter;
import com.ywcommon.common.widgetlib.adapter.BaseViewHolder;

import java.util.List;

/**
 * @author yaowang
 * @version 1.0
 * @date：2021/9/9
 * @packagename： com.ywcommon.common.nestscroll
 */
public class NestChildAdapter extends BaseQuickAdapter<MyEntity, BaseViewHolder> {

    public NestChildAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyEntity item) {
        helper.setText(R.id.tv_id, item.getId());
        helper.setText(R.id.tv_title, item.getTitle());
        helper.setText(R.id.tv_content, item.getContent());
    }
}
