package com.ywcommon.common.maptest.wengine;

import android.content.Context;

import com.ywcommon.common.maptest.xadapter.AGMapEngine;
import com.ywcommon.common.maptest.xadapter.AGMapUtils;
import com.ywcommon.common.maptest.xadapter.AGMapView;

/**
 * @author yaowang
 * @version 1.0
 * @date：2021/12/21
 * @packagename： com.ywcommon.common.maptest.wengine
 */
public class OsmMapEngine implements AGMapEngine {
    protected AGMapView agMapView;

    @Override
    public AGMapView createMapView(Context context) {
        //具体创建osm地图MapView
        if(agMapView == null){
            agMapView = new OsmMapView(context);
        }
        return agMapView;
    }

    @Override
    public AGMapUtils createMapUtils(Context context) {
        return null;
    }


}
