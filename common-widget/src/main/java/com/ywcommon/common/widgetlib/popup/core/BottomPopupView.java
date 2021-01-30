package com.ywcommon.common.widgetlib.popup.core;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.ywcommon.common.widgetlib.R;
import com.ywcommon.common.widgetlib.popup.animator.PopupAnimator;
import com.ywcommon.common.widgetlib.popup.enums.PopupStatus;
import com.ywcommon.common.widgetlib.popup.util.KeyboardUtils;
import com.ywcommon.common.widgetlib.popup.util.XPopupUtils;
import com.ywcommon.common.widgetlib.popup.widget.SmartDragLayout;

/**
 * Description: 在底部显示的Popup
 * Create by lxj, at 2018/12/11
 */
public class BottomPopupView extends BasePopupView {
    protected SmartDragLayout bottomPopupContainer;
    public BottomPopupView(@NonNull Context context) {
        super(context);
        bottomPopupContainer = findViewById(R.id.bottomPopupContainer);
    }

    protected void addInnerContent(){
        View contentView = LayoutInflater.from(getContext()).inflate(getImplLayoutId(), bottomPopupContainer, false);
        bottomPopupContainer.addView(contentView);
    }

    @Override
    protected int getPopupLayoutId() {
        return R.layout._xpopup_bottom_popup_view;
    }


    @Override
    protected void initPopupContent() {
        super.initPopupContent();
        if(bottomPopupContainer.getChildCount()==0){
            addInnerContent();
        }
        bottomPopupContainer.enableDrag(popupInfo.enableDrag);
        bottomPopupContainer.dismissOnTouchOutside(popupInfo.isDismissOnTouchOutside);
        bottomPopupContainer.isThreeDrag(popupInfo.isThreeDrag);

        getPopupImplView().setTranslationX(popupInfo.offsetX);
        getPopupImplView().setTranslationY(popupInfo.offsetY);

        XPopupUtils.applyPopupSize((ViewGroup) getPopupContentView(), getMaxWidth(), getMaxHeight()
        ,getPopupWidth(), getPopupHeight(), null);

        bottomPopupContainer.setOnCloseListener(new SmartDragLayout.OnCloseListener() {
            @Override
            public void onClose() {
                beforeDismiss();
                if(popupInfo.xPopupCallback!=null) popupInfo.xPopupCallback.beforeDismiss(BottomPopupView.this);
                doAfterDismiss();
            }

            @Override
            public void onDrag(int value, float percent, boolean isScrollUp) {
                if(popupInfo.xPopupCallback!=null) popupInfo.xPopupCallback.onDrag(BottomPopupView.this, value, percent,isScrollUp);
                if (popupInfo.hasShadowBg) setBackgroundColor(shadowBgAnimator.calculateBgColor(percent));
            }

            @Override
            public void onOpen() {
                BottomPopupView.super.doAfterShow();
            }
        });

        bottomPopupContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @Override
    protected void doAfterShow() { }

    @Override
    public void doShowAnimation() {
        bottomPopupContainer.open();
    }

    @Override
    public void doDismissAnimation() {
        bottomPopupContainer.close();
    }

    /**
     * 动画是跟随手势发生的，所以不需要额外的动画器，因此动画时间也清零
     *
     * @return
     */
    @Override
    public int getAnimationDuration() {
        return 0;
    }

    @Override
    protected PopupAnimator getPopupAnimator() {
        return null;
    }

    @Override
    public void dismiss() {
        if(popupInfo==null) return;
        if (popupStatus == PopupStatus.Dismissing) return;
        popupStatus = PopupStatus.Dismissing;
        if (popupInfo.autoOpenSoftInput) KeyboardUtils.hideSoftInput(this);
        clearFocus();
        bottomPopupContainer.close();
    }

    /**
     * 具体实现的类的布局
     *
     * @return
     */
    protected int getImplLayoutId() {
        return 0;
    }

    protected int getMaxWidth() {
        return popupInfo.maxWidth == 0 ? XPopupUtils.getWindowWidth(getContext())
                : popupInfo.maxWidth;
    }

}
