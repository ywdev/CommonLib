package com.ywcommon.common.utiltest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ywcommon.common.R;
import com.ywcommon.common.utillib.constant.PermissionConstants;
import com.ywcommon.common.utillib.util.antishake.AntiShakeUtils;
import com.ywcommon.common.utillib.util.log.LogUtils;
import com.ywcommon.common.utillib.util.permission.DialogHelper;
import com.ywcommon.common.utillib.util.permission.PermissionHelper;
import com.ywcommon.common.utillib.util.permission.PermissionUtils;
import com.ywcommon.common.utillib.util.toast.ToastUtils;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author yaowang
 * @version 1.0
 * @date：2021/9/2
 * @packagename： com.ywcommon.common.utiltest
 */
public class PermissionFragment extends Fragment implements View.OnClickListener {
    private Button btn1;
    private Button btn2;

    public static PermissionFragment newInstance() {
        Bundle args = new Bundle();
        PermissionFragment fragment = new PermissionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_permission, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        btn1 = view.findViewById(R.id.btn1);
        btn2 = view.findViewById(R.id.btn2);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                PermissionHelper.requestStorage(new PermissionHelper.OnPermissionGrantedListener() {
                    @Override
                    public void onPermissionGranted() {
                        ToastUtils.show("hello");
                    }
                });
                break;
            case R.id.btn2:
                DialogHelper.showOpenAppSettingDialog();
                break;
            default:
                break;
        }
    }
}
