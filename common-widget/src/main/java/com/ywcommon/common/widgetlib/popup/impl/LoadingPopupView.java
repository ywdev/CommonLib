package com.ywcommon.common.widgetlib.popup.impl;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.transition.ChangeBounds;
import androidx.transition.Fade;
import androidx.transition.TransitionManager;
import androidx.transition.TransitionSet;

import com.ywcommon.common.widgetlib.R;
import com.ywcommon.common.widgetlib.popup.XPopup;
import com.ywcommon.common.widgetlib.popup.core.CenterPopupView;
import com.ywcommon.common.widgetlib.popup.util.XPopupUtils;

/**
 * Description: 加载对话框
 * Create by dance, at 2018/12/16
 */
public class LoadingPopupView extends CenterPopupView {
    private TextView tv_title;

    /**
     * @param context
     * @param bindLayoutId layoutId 如果要显示标题，则要求必须有id为tv_title的TextView，否则无任何要求
     */
    public LoadingPopupView(@NonNull Context context, int bindLayoutId) {
        super(context);
        this.bindLayoutId = bindLayoutId;
        addInnerContent();
    }

    @Override
    protected int getImplLayoutId() {
        return bindLayoutId != 0 ? bindLayoutId : R.layout._xpopup_center_impl_loading;
    }

    @Override
    protected void initPopupContent() {
        super.initPopupContent();
        tv_title = findViewById(R.id.tv_title);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getPopupImplView().setElevation(10f);
        }
        if (bindLayoutId == 0) {
            getPopupImplView().setBackground(XPopupUtils.createDrawable(Color.parseColor("#CF000000"), popupInfo.borderRadius));
        }
        setup();
    }

    protected void setup() {
        if (tv_title == null) return;
        if (title != null && title.length() != 0) {
            post(new Runnable() {
                @Override
                public void run() {
                    if(tv_title.getVisibility()== View.VISIBLE){
                        TransitionManager.beginDelayedTransition(centerPopupContainer, new TransitionSet()
                                .setDuration(XPopup.getAnimationDuration())
                                .addTransition(new Fade())
                                .addTransition(new ChangeBounds()));
                    }
                    tv_title.setVisibility(VISIBLE);
                    tv_title.setText(title);
                }
            });
        } else {
            post(new Runnable() {
                @Override
                public void run() {
                    if(tv_title.getVisibility()== View.VISIBLE){
                        TransitionManager.beginDelayedTransition(centerPopupContainer, new TransitionSet()
                                .setDuration(XPopup.getAnimationDuration())
                                .addTransition(new Fade())
                                .addTransition(new ChangeBounds()));
                    }
                    tv_title.setVisibility(GONE);
                }
            });
        }
    }

    private CharSequence title;

    public LoadingPopupView setTitle(CharSequence title) {
        this.title = title;
        setup();
        return this;
    }
}
