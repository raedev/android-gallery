package com.androidx.gallery.data;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;

import com.androidx.gallery.listener.OnMediaListener;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * 图库数据源，只要来自该接口提供的方法、包括子方法都需要运行在线程中。
 * @author RAE
 * @date 2022/01/25
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public abstract class DataSource implements LifecycleEventObserver {

    @Nullable
    private WeakReference<Activity> mActivityWeakReference;
    private final Context mContext;

    /**
     * 回调列表
     */
    private final List<OnMediaListener> mOnMediaListenerList = new ArrayList<>();

    public DataSource(Context context) {
        mContext = context;
    }

    /**
     * 绑定Activity
     * @param activity Activity
     */
    public void bindActivity(FragmentActivity activity) {
        mActivityWeakReference = new WeakReference<>(activity);
        activity.getLifecycle().addObserver(this);
    }

    protected Context getContext() {
        return mContext;
    }

    /**
     * 添加媒体回调监听
     * @param listener 回调
     */
    public void addOnMediaListener(OnMediaListener listener) {
        if (!mOnMediaListenerList.contains(listener)) {
            mOnMediaListenerList.add(listener);
        }
    }

    /**
     * 移除媒体回调监听
     * @param listener 回调
     */
    public void removeOnMediaListener(OnMediaListener listener) {
        mOnMediaListenerList.remove(listener);
    }

    @Override
    public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
        if (event == Lifecycle.Event.ON_DESTROY) {
            onDestroy();
        }
    }

    /**
     * 释放操作
     */
    protected void onDestroy() {
        // 清除回调
        mOnMediaListenerList.clear();
        // 清除Activity引用
        if (mActivityWeakReference != null) {
            mActivityWeakReference.clear();
            mActivityWeakReference = null;
        }
    }

    // region 抽象接口

    /**
     * 获取相册数据接口
     * @return 数据接口
     */
    public abstract AlbumProvider getAlbumProvider();

    /**
     * 获取媒体数据接口
     * @return 数据接口
     */
    public abstract MediaProvider getMediaProvider();

    // endregion
}
