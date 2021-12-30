package com.ywcommon.common.maptest.wengine;

import android.content.Context;
import android.view.View;

import com.ywcommon.common.maptest.xadapter.AGMapView;

/**
 * @author yaowang
 * @version 1.0
 * @date：2021/12/22
 * @packagename： com.ywcommon.common.maptest.wengine
 */
public class OsmMapView implements AGMapView {

    protected MapView mapView;
    protected Context context;

    public OsmMapView(Context context) {
        this.context = context;
    }

    @Override
    public View getMapView() {
        if(mapView == null){
            mapView = new MapView(context);
        }
        return mapView;
    }



}
