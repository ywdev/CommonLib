package com.ywcommon.common.widgetlib.popup.impl;

import android.content.Context;
import android.graphics.Rect;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import com.ywcommon.common.widgetlib.R;
import com.ywcommon.common.widgetlib.popup.animator.PopupAnimator;
import com.ywcommon.common.widgetlib.popup.animator.TranslateAnimator;
import com.ywcommon.common.widgetlib.popup.core.BasePopupView;
import com.ywcommon.common.widgetlib.popup.enums.PopupAnimation;
import com.ywcommon.common.widgetlib.popup.enums.PopupPosition;
import com.ywcommon.common.widgetlib.popup.interfaces.OnClickOutsideListener;
import com.ywcommon.common.widgetlib.popup.util.XPopupUtils;
import com.ywcommon.common.widgetlib.popup.widget.PartShadowContainer;

/**
 * Description: 局部阴影的弹窗，类似于淘宝商品列表的下拉筛选弹窗
 * Create by dance, at 2018/12/21
 */
public abstract class PartShadowPopupView extends BasePopupView {
    protected PartShadowContainer attachPopupContainer;
    public PartShadowPopupView(@NonNull Context context) {
        super(context);
        attachPopupContainer = findViewById(R.id.attachPopupContainer);
    }

    @Override
    protected int getPopupLayoutId() {
        return R.layout._xpopup_partshadow_popup_view;
    }
    protected void addInnerContent() {
        View contentView = LayoutInflater.from(getContext()).inflate(getImplLayoutId(), attachPopupContainer, false);
        attachPopupContainer.addView(contentView);
    }

    @Override
    protected void initPopupContent() {
        if (attachPopupContainer.getChildCount() == 0) addInnerContent();
        // 指定阴影动画的目标View
        if (popupInfo.hasShadowBg) {
            shadowBgAnimator.targetView = getPopupContentView();
        }
        XPopupUtils.applyPopupSize((ViewGroup) getPopupContentView(), getMaxWidth(), getMaxHeight(),
                getPopupWidth(), getPopupHeight(), new Runnable() {
            @Override
            public void run() {
                doAttach();
            }
        });
    }


    protected void initAndStartAnimation(){
        initAnimator();
        doShowAnimation();
        doAfterShow();
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        post(new Runnable() {
            @Override
            public void run() {
                doAttach();
            }
        });
    }

    public boolean isShowUp;
    public void doAttach() {
        if (popupInfo.getAtView() == null)
            throw new IllegalArgumentException("atView must not be null for PartShadowPopupView！");

        //1. apply width and height
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) getPopupContentView().getLayoutParams();
        params.width = getMeasuredWidth();

        //1. 获取atView在屏幕上的位置
        int[] locations = new int[2];
        popupInfo.getAtView().getLocationOnScreen(locations);
        Rect rect = new Rect(locations[0], locations[1], locations[0] + popupInfo.getAtView().getMeasuredWidth(),
                locations[1] + popupInfo.getAtView().getMeasuredHeight());

        //水平居中
        if (popupInfo.isCenterHorizontal && getPopupImplView() != null) {
//            getPopupImplView().setTranslationX(XPopupUtils.getWindowWidth(getContext()) / 2f - getPopupContentView().getMeasuredWidth() / 2f);
            //参考目标View居中，而不是屏幕居中
            int tx = (rect.left + rect.right)/2 - getPopupImplView().getMeasuredWidth()/2;
            getPopupImplView().setTranslationX(tx);
        }else {
            int tx = rect.left + popupInfo.offsetX;
            if(tx + getPopupImplView().getMeasuredWidth() > XPopupUtils.getWindowWidth(getContext())){
                //右边超出屏幕了，往左移动
                tx -= (tx + getPopupImplView().getMeasuredWidth()-XPopupUtils.getWindowWidth(getContext()));
            }
            getPopupImplView().setTranslationX(tx);
        }

        int centerY = rect.top + rect.height() / 2;
        if ((centerY > getMeasuredHeight() / 2 || popupInfo.popupPosition == PopupPosition.Top) && popupInfo.popupPosition != PopupPosition.Bottom) {
            // 说明atView在Window下半部分，PartShadow应该显示在它上方，计算atView之上的高度
            params.height = rect.top;
            isShowUp = true;
            // 同时自定义的impl View应该Gravity居于底部
            View implView = ((ViewGroup) getPopupContentView()).getChildAt(0);
            FrameLayout.LayoutParams implParams = (FrameLayout.LayoutParams) implView.getLayoutParams();
            implParams.gravity = Gravity.BOTTOM;
            if (getMaxHeight() != 0)
                implParams.height = Math.min(implView.getMeasuredHeight(), getMaxHeight());
            implView.setLayoutParams(implParams);
        } else {
            // atView在上半部分，PartShadow应该显示在它下方，计算atView之下的高度
            params.height = getMeasuredHeight() - rect.bottom;
            isShowUp = false;
            params.topMargin = rect.bottom;
            // 同时自定义的impl View应该Gravity居于顶部
            View implView = ((ViewGroup) getPopupContentView()).getChildAt(0);
            FrameLayout.LayoutParams implParams = (FrameLayout.LayoutParams) implView.getLayoutParams();
            implParams.gravity = Gravity.TOP;
            if (getMaxHeight() != 0)
                implParams.height = Math.min(implView.getMeasuredHeight(), getMaxHeight());
            implView.setLayoutParams(implParams);
        }
        getPopupContentView().setLayoutParams(params);
        getPopupImplView().setTranslationY(popupInfo.offsetY);

        attachPopupContainer.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (popupInfo.isDismissOnTouchOutside) dismiss();
                return false;
            }
        });
        attachPopupContainer.setOnClickOutsideListener(new OnClickOutsideListener() {
            @Override
            public void onClickOutside() {
                if (popupInfo.isDismissOnTouchOutside) dismiss();
            }
        });
        initAndStartAnimation();
    }
    @Override
    protected PopupAnimator getPopupAnimator() {
        return new TranslateAnimator(getPopupImplView(), isShowUp ?
                PopupAnimation.TranslateFromBottom : PopupAnimation.TranslateFromTop);
    }

}
