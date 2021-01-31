package com.ywcommon.common.utiltest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;

import com.ywcommon.common.Constant;
import com.ywcommon.common.R;
import com.ywcommon.common.utillib.util.FragmentUtils;

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
                    AntiShakeFragment.newInstance(),R.id.fragment_container,false,false);
        }
    }

    @Override
    public void onClick(View v) {
        onBackPressed();
    }
}
