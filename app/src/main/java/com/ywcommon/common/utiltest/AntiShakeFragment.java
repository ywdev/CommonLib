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
import com.ywcommon.common.utillib.util.antishake.AntiShakeUtils;
import com.ywcommon.common.utillib.util.toast.ToastUtils;

public class AntiShakeFragment extends Fragment implements View.OnClickListener {
    private Button btn1;
    private Button btn2;

    public static AntiShakeFragment newInstance() {
        Bundle args = new Bundle();
        AntiShakeFragment fragment = new AntiShakeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_anti_shake, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        btn1 = view.findViewById(R.id.btn1);
        btn2 = view.findViewById(R.id.btn2);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                if(AntiShakeUtils.isValid(v)){
                    ToastUtils.show("点击激活了");
                }else {
                    ToastUtils.show("手抖了");
                }
                break;
            case R.id.btn2:
                if(AntiShakeUtils.isValid(v,500)){
                    ToastUtils.show("点击激活了");
                }else {
                    ToastUtils.show("手抖了");
                }
                break;
            default:
                break;
        }
    }
}
