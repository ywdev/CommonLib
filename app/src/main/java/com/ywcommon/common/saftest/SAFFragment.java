package com.ywcommon.common.saftest;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.ywcommon.common.R;
import com.ywcommon.common.utillib.util.device.SDCardUtils;
import com.ywcommon.common.widgetlib.ucrop.util.SdkUtils;

import org.jetbrains.annotations.NotNull;

import java.io.File;

/**
 * @author yaowang
 * @version 1.0
 * @date：2021/10/18
 * @packagename： com.ywcommon.common.saftest
 */
public class SAFFragment extends Fragment implements View.OnClickListener {
    private Button btnDocument;
    private Button btnZip;

    public static SAFFragment newInstance() {
        Bundle args = new Bundle();
        SAFFragment fragment = new SAFFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_saf_test, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        btnDocument = view.findViewById(R.id.document_manager);
        btnZip = view.findViewById(R.id.open_zip_file);

        btnDocument.setOnClickListener(this);
        btnZip.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.document_manager:
                intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                intent.setType("image/*");//无类型限制
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent, 1);
                break;
            case R.id.open_zip_file:
                String path = SDCardUtils.getSDCardPathByEnvironment()+"/Download/我的压缩文件.rar";
                File file = new File(path);
                intent.setAction(android.content.Intent.ACTION_VIEW);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    Uri uri = FileProvider.getUriForFile(getActivity(), getActivity().getPackageName() + ".utilcode.provider", file);
                    intent.setDataAndType(uri, "application/x-gzip");
                } else {
                    intent.setDataAndType(Uri.fromFile(file), "application/x-gzip");
                }
                startActivityForResult(intent,2);
                break;
        }

    }
}
