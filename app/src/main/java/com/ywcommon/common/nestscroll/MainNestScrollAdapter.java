package com.ywcommon.common.nestscroll;

import android.view.ViewGroup;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.ywcommon.common.R;
import com.ywcommon.common.widgetlib.adapter.BaseMultiItemQuickAdapter;
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
public class MainNestScrollAdapter extends BaseMultiItemQuickAdapter<MultipleItem, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public MainNestScrollAdapter(List<MultipleItem> data) {
        super(data);
        addItemType(MultipleItem.TYPE_DETAIL_HEADER, R.layout.layout_header_detail);
        addItemType(MultipleItem.TYPE_TABLELAYOUT_AND_VIEWPAGER, R.layout.layout_tab_viewpager);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultipleItem item) {
        switch (helper.getItemViewType()){
            case MultipleItem.TYPE_DETAIL_HEADER:
                ViewGroup viewGroup = helper.getView(R.id.rl_main_header);
                loadBanner(viewGroup);
                break;
            case MultipleItem.TYPE_TABLELAYOUT_AND_VIEWPAGER:
                TabLayout indicator = helper.getView(R.id.tab_layout);
                ViewPager viewpager = helper.getView(R.id.vp_viewpager);
                loadFragments(indicator,viewpager);
                break;
        }

    }

    /**
     * 加载Banner
     */
    protected void loadBanner(ViewGroup viewGroup) {

    }

    /**
     * 加载Fragments
     */
    protected void loadFragments(TabLayout tabLayout, ViewPager viewpager) {

    }
}
