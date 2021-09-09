package com.ywcommon.common.nestscroll;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.ywcommon.common.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static androidx.fragment.app.FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;
import static androidx.fragment.app.FragmentPagerAdapter.BEHAVIOR_SET_USER_VISIBLE_HINT;

public class NestScrollFragment extends Fragment {
    private HomeRecyclerView mParentRecyclerView;
    private List<MultipleItem> multipleItems = new ArrayList<>();
    private HomeRecyclerView.OnScrollableChildCallback onScrollableChildCallback;
    private ArrayList<String> mTitles = new ArrayList<>();
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private MainNestScrollAdapter mAdapter;
    private MyPageAdapter mPageAdapter;


    public static NestScrollFragment newInstance() {
        Bundle args = new Bundle();
        NestScrollFragment fragment = new NestScrollFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        public void onScrollStateChanged(RecyclerView recyclerView, int newStatus) {
            if (newStatus == RecyclerView.SCROLL_STATE_IDLE) {
                if (mParentRecyclerView != null) {
                    onScrollableChildCallback.onScrollableChildChangedToIdle(recyclerView);
                }
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_main_recyclerview, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        initArguments();
        initView(view);
    }

    private void initArguments() {
        mTitles.add("刘备");
        mTitles.add("关羽");
        mTitles.add("张飞");

    }

    private void initView(View view) {
        mParentRecyclerView = view.findViewById(R.id.hrv_recyclerView);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mParentRecyclerView.setLayoutManager(mLayoutManager);
        multipleItems.add(new MultipleItem(MultipleItem.TYPE_DETAIL_HEADER,null));
        multipleItems.add(new MultipleItem(MultipleItem.TYPE_TABLELAYOUT_AND_VIEWPAGER,null));
        onScrollableChildCallback = mParentRecyclerView.getOnScrollableChildCallback();
        setMyAdapter();
    }

    private void setMyAdapter() {
        mAdapter = new MainNestScrollAdapter(multipleItems){
            @Override
            protected void loadBanner(ViewGroup viewGroup) {

            }

            @Override
            protected void loadFragments(TabLayout tabLayout, ViewPager viewpager) {
                ChildFragment1 fragment1 = ChildFragment1.newInstance();
                fragment1.setOnScrollListener(onScrollListener);
                mFragments.add(fragment1);
                ChildFragment2 fragment2 = ChildFragment2.newInstance();
                fragment2.setOnScrollListener(onScrollListener);
                mFragments.add(fragment2);
                ChildFragment3 fragment3 = ChildFragment3.newInstance();
                fragment3.setOnScrollListener(onScrollListener);
                mFragments.add(fragment3);
                mPageAdapter = new MyPageAdapter(getParentFragmentManager(),BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,
                        mTitles, mFragments);
                viewpager.setOffscreenPageLimit(mFragments.size());
                tabLayout.setupWithViewPager(viewpager);
                viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    }

                    @Override
                    public void onPageSelected(int position) {
                        pageSelected(viewpager,position);
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });
                viewpager.setAdapter(mPageAdapter);
                viewpager.setCurrentItem(0);
                pageSelected(viewpager,0);
            }

            private void pageSelected(ViewPager viewPager, int position) {
                Fragment fragment = ((FragmentPagerAdapter) viewPager.getAdapter()).getItem(position);
                if (fragment instanceof ScrollableChild) {
                    onScrollableChildCallback.onScrollableChildSelected((ScrollableChild) fragment);
                }
            }
        };
        mParentRecyclerView.setAdapter(mAdapter);
    }

    protected class MyPageAdapter extends FragmentPagerAdapter {
        private List<String> _titles;
        private List<? extends Fragment> _fragments;

        public MyPageAdapter(@NonNull @NotNull FragmentManager fm, int behavior,
                             List<String> titles, List<? extends Fragment> fragments) {
            super(fm, behavior);
            _titles = titles;
            _fragments = fragments;
        }


        @Override
        public Fragment getItem(int position) {
            return _fragments.get(position);
        }

        @Override
        public int getCount() {
            return _fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return _titles.get(position);
        }
    }
}
