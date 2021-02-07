package com.ywcommon.common;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;

import com.ywcommon.common.Constant;
import com.ywcommon.common.R;
import com.ywcommon.common.utillib.util.FragmentUtils;
import com.ywcommon.common.utiltest.AntiShakeFragment;
import com.ywcommon.common.widgettest.DialogSampleFragment;

public class UtilBaseActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_util_base);
        String title = getIntent().getStringExtra(Constant.EXTRA_TITLE);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(title);
        toolbar.setNavigationOnClickListener(this);
        if("antishake".equals(title)){
            FragmentUtils.add(getSupportFragmentManager(),
                    AntiShakeFragment.newInstance(),R.id.fragment_container);
        }
        if("dialog".equals(title)){
            FragmentUtils.add(getSupportFragmentManager(),
                    DialogSampleFragment.newInstance(),R.id.fragment_container);
        }
    }

    @Override
    public void onClick(View v) {
        onBackPressed();
    }
}
