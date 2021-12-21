package com.ywcommon.common.maptest.xadapter;

import android.content.Context;
import android.view.View;

/**
 * @author yaowang
 * @version 1.0
 * @date：2021/12/21
 * @packagename： com.ywcommon.common.maptest.xadapter
 */
public class AGMapManager {
    private static AGMapManager instance;
    private AGMapManager(){};

    public static AGMapManager getInstance() {
        if(instance==null){
            return new AGMapManager();
        }
        return instance;
    }


    public AGMapView getAGMapView(Context context, AGMapEngine engine){
        return engine.createMapView(context);
    }



}
