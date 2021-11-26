package com.ywcommon.common;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;

import com.ywcommon.common.Constant;
import com.ywcommon.common.R;
import com.ywcommon.common.demotest.JetpackTestFragment;
import com.ywcommon.common.kotlin.KotlinCoroutinesFragment;
import com.ywcommon.common.nestscroll.NestScrollFragment;
import com.ywcommon.common.saftest.SAFFragment;
import com.ywcommon.common.utillib.util.FragmentUtils;
import com.ywcommon.common.utiltest.AntiShakeFragment;
import com.ywcommon.common.utiltest.DownloadFragment;
import com.ywcommon.common.utiltest.PermissionFragment;
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
                    AntiShakeFragment.newInstance(), R.id.fragment_container);
        }
        if("dialog".equals(title)){
            FragmentUtils.add(getSupportFragmentManager(),
                    DialogSampleFragment.newInstance(), R.id.fragment_container);
        }
        if("download".equals(title)){
            FragmentUtils.add(getSupportFragmentManager(),
                    DownloadFragment.newInstance(), R.id.fragment_container);
        }
        if("jetpack".equals(title)){
            FragmentUtils.add(getSupportFragmentManager(),
                    JetpackTestFragment.newInstance(),R.id.fragment_container);
        }
        if("permission".equals(title)){
            FragmentUtils.add(getSupportFragmentManager(),
                    PermissionFragment.newInstance(), R.id.fragment_container);
        }
        if("nestfunction".equals(title)){
            FragmentUtils.add(getSupportFragmentManager(),
                    NestScrollFragment.newInstance(), R.id.fragment_container);
        }
        if("SAF".equals(title)){
            FragmentUtils.add(getSupportFragmentManager(),
                    SAFFragment.newInstance(), R.id.fragment_container);
        }
        if("kotlinCoroutines".equals(title)){
            FragmentUtils.add(getSupportFragmentManager(),
                    KotlinCoroutinesFragment.newInstance(), R.id.fragment_container);
        }
    }

    @Override
    public void onClick(View v) {
        onBackPressed();
    }
}
