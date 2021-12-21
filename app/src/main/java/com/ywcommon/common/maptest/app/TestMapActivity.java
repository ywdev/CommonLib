package com.ywcommon.common.maptest.app;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.ywcommon.common.maptest.ui.BaseMapActivity;
import com.ywcommon.common.maptest.wengine.ArcgisMapEngine;
import com.ywcommon.common.maptest.wengine.OsmMapEngine;
import com.ywcommon.common.maptest.xadapter.AGMapEngine;

/**
 * @author yaowang
 * @version 1.0
 * @date：2021/12/21
 * @packagename： com.ywcommon.common.maptest.app
 */
public class TestMapActivity extends BaseMapActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    protected AGMapEngine getMapEngine() {
        return new ArcgisMapEngine();
    }
}
