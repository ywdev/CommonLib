package com.ywcommon.common.nestscroll;


import com.ywcommon.common.widgetlib.adapter.entity.MultiItemEntity;

/**
 * @author 创建人 ：yaowang
 * @version 1.0
 * @package 包名 ：com.appdev.toolkit.nestscroll
 * @createTime 创建时间 ：2019/4/18
 * @modifyBy 修改人 ：
 * @modifyTime 修改时间 ：
 * @modifyMemo 修改备注：
 */
public class MultipleItem implements MultiItemEntity {

    public static final int TYPE_DETAIL_HEADER = 1;//Head部分
    public static final int TYPE_TABLELAYOUT_AND_VIEWPAGER = 2;//tableLayout与viewPager部分

    private int itemType;
    private Object data;

    public MultipleItem(int itemType, Object data) {
        this.itemType = itemType;
        this.data = data;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
