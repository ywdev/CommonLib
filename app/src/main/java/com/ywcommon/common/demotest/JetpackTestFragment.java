package com.ywcommon.common.demotest;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ywcommon.common.R;

public class JetpackTestFragment extends Fragment implements View.OnClickListener {
    private Button btn1;

    public static JetpackTestFragment newInstance() {
        Bundle args = new Bundle();
        JetpackTestFragment fragment = new JetpackTestFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_jetpack_test, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        btn1 = view.findViewById(R.id.view_model);
        btn1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.view_model) {
            Intent intent = new Intent(getContext(), CountActivity.class);
            getActivity().startActivity(intent);
        }
    }
}
