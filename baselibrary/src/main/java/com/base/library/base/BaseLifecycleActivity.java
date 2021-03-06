package com.base.library.base;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;

import com.base.library.base.constant.StateConstants;
import com.base.library.base.viewmodel.NetWorkBaseViewModel;
import com.base.library.utils.LogUtil;
import com.base.library.utils.ReflectUtil;
import com.base.library.widget.TopNavigationLayout;

public abstract class BaseLifecycleActivity<T extends NetWorkBaseViewModel> extends BaseActivity {

    protected T mViewModel;
    protected TopNavigationLayout mTopNavigationLayout;

    @Override
    public void initView() {
        LogUtil.d("初始化 BaseLifecycleActivity");
        mViewModel = createViewModel(this, (Class<T>) ReflectUtil.getInstance(this, 0));
        if (null != mViewModel) {
            LogUtil.d("初始化 BaseLifecycleActivity   mViewModel 不为空");
            MutableLiveData loadState = mViewModel.loadState;
            loadState.observe(this, observer);
            dataObserver();
        }
    }

    /**
     * 创建 自定义的 ViewModel
     */
    protected <T extends ViewModel> T createViewModel(FragmentActivity activity, @NonNull Class<T> modelClass) {
        ViewModelProvider viewModelProvider = ViewModelProviders.of(activity);
        return viewModelProvider.get(modelClass);
    }

    /**
     *  LiveData 观察者回调实现的方法 (一般网络回调等等)
     */
    protected abstract void dataObserver();

    // lifecycle 中 liveData的监听者
    protected Observer<String> observer = new Observer<String>() {
        @Override
        public void onChanged(@Nullable String state) {
            dismissProgressDialog();
            if (!TextUtils.isEmpty(state)) {
                if (StateConstants.ERROR_STATE.equals(state)) {
                    showToast("加载错误");
                } else if (StateConstants.NET_WORK_STATE.equals(state)) {
                    showToast("网络不好，请稍后重试");
                } else if (StateConstants.LOADING_STATE.equals(state)) {
                    showToast("加载中");
                } else if (StateConstants.SUCCESS_STATE.equals(state)) {
//                    showToast("加载成功");
                } else {
                    showToast(state);
                }
            }
        }
    };
}
