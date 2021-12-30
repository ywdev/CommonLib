package com.ywcommon.common.maptest.ui;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ywcommon.common.maptest.xadapter.AGMapEngine;
import com.ywcommon.common.maptest.xadapter.AGMapManager;
import com.ywcommon.common.maptest.xadapter.AGMapView;

/**
 * @author yaowang
 * @version 1.0
 * @date：2021/12/21
 * @packagename： com.ywcommon.common.maptest.ui
 */
public class BaseMapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AGMapView agMapView = AGMapManager.getInstance().getAGMapView(this, getMapEngine());
        View mapView = agMapView.getMapView();



    }

    protected AGMapEngine getMapEngine() {
        return null;
    }
}
