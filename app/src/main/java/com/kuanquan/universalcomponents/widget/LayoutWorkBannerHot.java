package com.kuanquan.universalcomponents.widget;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.base.library.utils.LogUtil;
import com.base.library.utils.ToastUtils;
import com.base.library.widget.BannerIndicatorView;
import com.kuanquan.universalcomponents.R;
import com.kuanquan.universalcomponents.bean.BannerBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fei.wang on 2019/4/29.
 */
public class LayoutWorkBannerHot extends FrameLayout implements AppBanner.OnPageClickListener {

    AppBanner mAppBanner;
    LinearLayout mLinearLayout;
    BannerIndicatorView mBannerIndicatorView;
    ArrayList<ImageView> dotsList = new ArrayList<>();
    List<BannerBean> datas = new ArrayList<>();

    public LayoutWorkBannerHot(@NonNull Context context) {
        super(context);
        initView();
    }

    public LayoutWorkBannerHot(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public LayoutWorkBannerHot(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public LayoutWorkBannerHot(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    private void initView() {
        View root = LayoutInflater.from(getContext()).inflate(R.layout.layout_work_banner_hot, this, true);
        mAppBanner = root.findViewById(R.id.app_banner);
        mLinearLayout = root.findViewById(R.id.ll_hot_f);
        mLinearLayout.setVisibility(View.VISIBLE);

       mBannerIndicatorView = root.findViewById(R.id.indicator_view);
    }

    public void setData(List<BannerBean> lists) {
        if (lists != null && lists.size() > 0) {
            datas.clear();
            datas.addAll(lists);
            mAppBanner.setData(lists, this);
            mAppBanner.setScrollSpeed(mAppBanner);

            mBannerIndicatorView.bindWithViewPager(mAppBanner,lists.size());
            mBannerIndicatorView.setCurrentPosition(0);

            dotsList.clear();
            mLinearLayout.removeAllViews();
            for (int i = 0; i < lists.size(); i++) {
                ImageView view = new ImageView(getContext());
                if (i == 0) {
                    view.setImageResource(R.drawable.dots_focus);
                    LogUtil.e("小圆点 = " + i);
                } else {
                    view.setImageResource(R.drawable.dots_normal);
                    LogUtil.e("小圆点 = " + i);
                }
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(18, 18);
                params.setMargins(5, 0, 5, 0);
                LogUtil.e("小圆点view = " + view);
                mLinearLayout.addView(view, params);
                View childAt = mLinearLayout.getChildAt(0);
                LogUtil.e("获取到的子控件 = " + childAt);
                dotsList.add(view);
            }
        }
    }

    @Override
    public void onPageClick(BannerBean info) {
        ToastUtils.showMessage(getContext(), "点击事件");
//        Intent intent = new Intent();
//        intent.setClass(getContext(), HomeModelActivity.class);
//        getContext().startActivity(intent);
    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < dotsList.size(); i++) {
            if (position % dotsList.size() == i) {
                dotsList.get(i).setImageResource(R.drawable.dots_focus);
            } else {
                dotsList.get(i).setImageResource(R.drawable.dots_normal);
            }
        }
    }
}
