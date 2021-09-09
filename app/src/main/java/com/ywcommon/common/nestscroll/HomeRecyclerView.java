package com.ywcommon.common.nestscroll;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.OverScroller;

import androidx.core.view.NestedScrollingParent;
import androidx.core.view.NestedScrollingParentHelper;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public class HomeRecyclerView extends RecyclerView implements NestedScrollingParent {
    private static final int SCROLL_DIRECTION_DOWN = -1;
    private static final int SCROLL_DIRECTION_UP = 1;

    private final String TAG = "HomeRecyclerView";
    private View mCurrentFlingView;
    private float mLastMotionX;
    private float mLastMotionY;
    private OnScrollableChildCallback mOnScrollableChildCallback = new OnScrollableChildCallback() {
        public void onScrollableChildChangedToIdle(RecyclerView recyclerView) {
            if ((HomeRecyclerView.this.mCurrentFlingView == recyclerView) && (HomeRecyclerView.this.mScroller != null) && (!HomeRecyclerView.this.mScroller.isFinished())) {
                HomeRecyclerView.this.fling(0, (int) HomeRecyclerView.this.mScroller.getCurrVelocity() * HomeRecyclerView.this.mScrollDirection);
                HomeRecyclerView.this.mScroller.abortAnimation();
            }
        }

        public void onScrollableChildSelected(ScrollableChild paramAnonymousScrollableChild) {
            HomeRecyclerView.this.mScrollableChild = paramAnonymousScrollableChild;
            HomeRecyclerView.this.mScrollableView = mScrollableChild.getChildRecyclerView();
        }
    };
    private NestedScrollingParentHelper mParentHelper;
    private int mScrollDirection = SCROLL_DIRECTION_UP;
    private ScrollableChild mScrollableChild;
    private RecyclerView mScrollableView;
    private OverScroller mScroller;
    private int mTouchSlop;

    public HomeRecyclerView(Context paramContext) {
        this(paramContext, null);
    }

    public HomeRecyclerView(Context paramContext, AttributeSet paramAttributeSet) {
        this(paramContext, paramAttributeSet, 0);
    }

    public HomeRecyclerView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
        super(paramContext, paramAttributeSet, paramInt);
        setOverScrollMode(OVER_SCROLL_NEVER);
        this.mParentHelper = new NestedScrollingParentHelper(this);
        this.mScroller = new OverScroller(paramContext);
        this.mTouchSlop = ViewConfiguration.get(paramContext).getScaledTouchSlop();
        addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if ((HomeRecyclerView.this.mCurrentFlingView == recyclerView) && (HomeRecyclerView.this.mScroller != null) && (!HomeRecyclerView.this.mScroller.isFinished())) {
                        if (HomeRecyclerView.this.isScrollableViewShown()) {
                            HomeRecyclerView.this.debugLog("onScrollStateChanged: Parent fling " + HomeRecyclerView.this.mScroller.getCurrVelocity());
                            HomeRecyclerView.this.mScrollableView.fling(0, (int) HomeRecyclerView.this.mScroller.getCurrVelocity() * HomeRecyclerView.this.mScrollDirection);
                        }
                        HomeRecyclerView.this.debugLog("onScrollStateChanged  abortAnimation ");
                        HomeRecyclerView.this.mScroller.abortAnimation();
                    }
                }
            }
        });
    }

    private void debugLog(String paramString) {
        Log.d("yaowang", paramString);
    }

    private void startFling(View target, float velocityY) {
        if (velocityY > 0) {
            this.mScrollDirection = SCROLL_DIRECTION_UP;
        } else {
            this.mScrollDirection = SCROLL_DIRECTION_DOWN;
        }
        this.mCurrentFlingView = target;
        debugLog("Scroller.fling velocityY " + velocityY + " minY: " + -Integer.MAX_VALUE + " maxY: " + Integer.MAX_VALUE);
        this.mScroller.fling(0, 0, 0, (int) Math.abs(velocityY), -Integer.MAX_VALUE, Integer.MAX_VALUE, -Integer.MAX_VALUE, Integer.MAX_VALUE);
        computeScroll();
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (this.mScroller.computeScrollOffset()) {
            postInvalidate();
        }
    }

    @Override
    public boolean dispatchNestedFling(float paramFloat1, float paramFloat2, boolean paramBoolean) {
        debugLog("dispatchNestedFling: Child " + paramFloat2);
        startFling(this, paramFloat2);
        return super.dispatchNestedFling(paramFloat1, paramFloat2, paramBoolean);
    }

    /**
     * @param velocityX 水平速度，以像素每秒为单位。
     * @param velocityY 竖直速度，以像素每秒为单位。
     * @return 如果一个嵌套滚动的父元素消耗了这个元素，则返回true。
     */
    @Override
    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        debugLog("dispatchNestedPreFling: Child" + velocityY);
        if ((velocityY < 0.0F) && (isScrollableViewShown()) && (!isScrollableChildReachTop())) {
            startFling(this.mScrollableView, velocityY);
            this.mScrollableView.fling(0, (int) velocityY);
            return true;
        }
        return super.dispatchNestedPreFling(velocityX, velocityY);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow, int type) {
        if ((isScrollableViewShown()) && (((dy > 0) && (isReachBottom()) && (!isScrollableViewReachBottom())) || ((dy < 0) && (!isScrollableChildReachTop())))) {
            debugLog("dispatchNestedPreScroll: Child 22 " + dy);
            consumed[1] = dy;
            this.mScrollableView.scrollBy(0, dy);
            return true;
        }
        debugLog("dispatchNestedPreScroll: Child 33 " + dy);
        return super.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow, type);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent paramMotionEvent) {
        this.mScroller.abortAnimation();
        this.mLastMotionX = paramMotionEvent.getX();
        this.mLastMotionY = paramMotionEvent.getY();
        return super.dispatchTouchEvent(paramMotionEvent);
    }

    public OnScrollableChildCallback getOnScrollableChildCallback() {
        return this.mOnScrollableChildCallback;
    }

    /**
     * @return 该布局是否到达底部
     */
    public boolean isReachBottom() {
        return computeVerticalScrollExtent() + computeVerticalScrollOffset() >= computeVerticalScrollRange();
    }

    /**
     * @return 子布局是否到达顶部
     */
    public boolean isScrollableChildReachTop() {
        Object localObject = this.mScrollableView.getLayoutManager();
        View localView = this.mScrollableView.getChildAt(0);
        boolean b = true;
        if ((localObject instanceof LinearLayoutManager)) {
            b = ((localView == null) || ((((LinearLayoutManager) localObject).findFirstVisibleItemPosition() <= 0) && (localView.getTop() == 0)));
        } else if (localObject instanceof StaggeredGridLayoutManager) {
            b = ((localView == null) || (((StaggeredGridLayoutManager) localObject).findFirstVisibleItemPositions(null)[0] <= 0) && (localView.getTop() == 0));
        }
        return b;
    }

    /**
     * @return 子布局是否到达底部
     */
    public boolean isScrollableViewReachBottom() {
        return (this.mScrollableView == null) || (this.mScrollableView.computeVerticalScrollExtent() + this.mScrollableView.computeVerticalScrollOffset() >= this.mScrollableView.computeVerticalScrollRange());
    }

    /**
     * @return 子布局是否可见
     */
    public boolean isScrollableViewShown() {
        if (this.mScrollableChild == null) {
            return false;
        }
        this.mScrollableView = this.mScrollableChild.getChildRecyclerView();
        return this.mScrollableView != null && this.mScrollableView.isShown();
    }

    /**
     * 获取嵌套滑动的轴
     *
     * @see ViewCompat#SCROLL_AXIS_HORIZONTAL 垂直
     * @see ViewCompat#SCROLL_AXIS_VERTICAL 水平
     * @see ViewCompat#SCROLL_AXIS_NONE 都支持
     */
    @Override
    public int getNestedScrollAxes() {
        return ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    /**
     * 嵌套滑动的子View在fling之后报告过来的fling情况
     *
     * @param target    具体嵌套滑动的那个子类
     * @param velocityX 水平方向速度
     * @param velocityY 垂直方向速度
     * @param consumed  子view是否fling了
     * @return true 父View是否消耗了fling
     */
    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        debugLog("onNestedFling: Parent " + velocityY);
        startFling(target, velocityY);
        return false;
    }

    /**
     * 在嵌套滑动的子View未fling之前告诉过来的准备fling的情况
     *
     * @param target    具体嵌套滑动的那个子类
     * @param velocityX 水平方向速度
     * @param velocityY 垂直方向速度
     * @return true 父View是否消耗了fling
     */
    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        debugLog("onNestedPreFling: Parent " + velocityY);
        if ((velocityY > 0.0F) && (isScrollableViewShown()) && (!isReachBottom())) {
            startFling(this, velocityY);
            fling(0, (int) velocityY);
            return true;
        }
        return false;
    }

    /**
     * 在嵌套滑动的子View未滑动之前告诉过来的准备滑动的情况
     *
     * @param target   具体嵌套滑动的那个子类
     * @param dx       水平方向嵌套滑动的子View想要变化的距离
     * @param dy       垂直方向嵌套滑动的子View想要变化的距离
     * @param consumed 这个参数要我们在实现这个函数的时候指定，回头告诉子View当前父View消耗的距离
     *                 consumed[0] 水平消耗的距离，consumed[1] 垂直消耗的距离 好让子view做出相应的调整
     */
    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        debugLog("onNestedPreScroll: Parent");
        if ((!isScrollableViewShown()) || (this.mScrollableView.getScrollState() != 1) || (((dy <= 0) || (isReachBottom())) && ((dy >= 0) || (!isScrollableChildReachTop())))) {
            return;
        }
        consumed[1] = dy;
        scrollBy(0, dy);
    }

    /**
     * 嵌套滑动的子View在滑动之后报告过来的滑动情况
     *
     * @param target       具体嵌套滑动的那个子类
     * @param dxConsumed   水平方向嵌套滑动的子View滑动的距离(消耗的距离)
     * @param dyConsumed   垂直方向嵌套滑动的子View滑动的距离(消耗的距离)
     * @param dxUnconsumed 水平方向嵌套滑动的子View未滑动的距离(未消耗的距离)
     * @param dyUnconsumed 垂直方向嵌套滑动的子View未滑动的距离(未消耗的距离)
     */
    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed,
                               int dxUnconsumed, int dyUnconsumed) {
        debugLog("onNestedScroll: Parent");
    }

    /**
     * 该父View接受了嵌套滑动的请求该函数调用。onStartNestedScroll返回true该函数会被调用。
     * 参数和onStartNestedScroll一样
     */
    @Override
    public void onNestedScrollAccepted(View child, View target, int nestedScrollAxes) {
        debugLog("onNestedScrollAccepted: Parent");
        this.mParentHelper.onNestedScrollAccepted(child, target, nestedScrollAxes);
    }

    /**
     * 有嵌套滑动到来了，问下该父View是否接受嵌套滑动
     *
     * @param child            嵌套滑动对应的父类的子类(因为嵌套滑动对于的父View不一定是一级就能找到的，可能挑了两级父View的父View，child的辈分>=target)
     * @param target           具体嵌套滑动的那个子类
     * @param nestedScrollAxes 支持嵌套滚动轴。水平方向，垂直方向，或者不指定
     * @return 是否接受该嵌套滑动
     */
    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        debugLog("onStartNestedScroll: Parent");
        return true;
    }

    /**
     * 停止嵌套滑动
     *
     * @param target 具体嵌套滑动的那个子类
     */
    @Override
    public void onStopNestedScroll(View target) {
        debugLog("onStopNestedScroll: Parent");
        this.mParentHelper.onStopNestedScroll(target);
    }

    public interface OnScrollableChildCallback {

        void onScrollableChildChangedToIdle(RecyclerView paramRecyclerView);

        void onScrollableChildSelected(ScrollableChild paramScrollableChild);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent) {
        return ((paramMotionEvent.getAction() != MotionEvent.ACTION_MOVE) || (!shouldScrollHorizontal(paramMotionEvent))) && (super.onInterceptTouchEvent(paramMotionEvent));
    }

    private boolean shouldScrollHorizontal(MotionEvent paramMotionEvent) {
        if (getScrollState() != 1) {
            float f1 = Math.abs(paramMotionEvent.getX() - this.mLastMotionX);
            float f2 = Math.abs(paramMotionEvent.getY() - this.mLastMotionY);
            if ((f1 > this.mTouchSlop) && (f1 > f2)) {
                int k = getChildCount();
                int i = 1;
                int j = 0;
                if (f1 > 0.0F) {
                    i = -1;
                    j = 0;
                }
                for (; ; ) {
                    if (j >= k) {
                        break;
                    }
                    if (ce.b(getChildAt(j), i, this.mLastMotionX, this.mLastMotionY)) {
                        return true;
                    }
                    j += 1;
                }
            }
        }
        return false;
    }

    public static class ce {
        private static final Rect dfa = new Rect();

        private static boolean b(View paramView, float paramFloat1, float paramFloat2) {
            return (paramView != null) && (paramView.getVisibility() == View.VISIBLE) && (c(paramView, paramFloat1, paramFloat2));
        }

        public static boolean b(View paramView, int paramInt, float paramFloat1, float paramFloat2) {
            if (b(paramView, paramFloat1, paramFloat2)) {
                if (paramView.canScrollHorizontally(paramInt)) {
                    return true;
                }
                if ((paramView instanceof ViewGroup)) {
                    int i = ((ViewGroup) paramView).getChildCount() - 1;
                    for (; ; ) {
                        if (i < 0) {
                            break;
                        }
                        if (b(((ViewGroup) paramView).getChildAt(i), paramInt, paramFloat1, paramFloat2)) {
                            break;
                        }
                        i -= 1;
                    }
                }
            }
            return false;
        }

        public static boolean c(View paramView, float paramFloat1, float paramFloat2) {
            dfa.setEmpty();
            if (paramView.getGlobalVisibleRect(dfa)) {
                return dfa.contains((int) paramFloat1, (int) paramFloat2);
            }
            return false;
        }
    }

}
