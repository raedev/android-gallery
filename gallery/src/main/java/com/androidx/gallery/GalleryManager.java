package com.androidx.gallery;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.androidx.gallery.config.GalleryConfig;
import com.androidx.gallery.db.GalleryDataSource;
import com.androidx.gallery.db.os.AndroidDataSource;
import com.androidx.gallery.provider.ImageLoader;
import com.androidx.gallery.provider.impl.GlideImageLoader;
import com.androidx.gallery.utils.GalleryUtils;
import com.google.gson.Gson;
import com.androidx.matisse.engine.impl.GlideEngine;
import com.androidx.matisse.internal.entity.SelectionSpec;

import java.util.Objects;

/**
 * 图库管理器
 * @author RAE
 * @date 2022/01/18
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public final class GalleryManager {

    // region 初始化

    private static GalleryManager sManager;

    /**
     * 初始化图库
     * @param context ApplicationContext
     */
    public static void init(Context context) {
        if (!(context instanceof Application)) {
            throw new IllegalArgumentException("初始化图库实例的Context应为Application");
        }
        if (sManager == null) {
            sManager = new GalleryManager((Application) context);
        }
    }

    /**
     * 获取图库实例
     * @return 图库
     */
    public static GalleryManager getInstance() {
        return Objects.requireNonNull(sManager, "请先初始化图库管理器");
    }

    // endregion

    private final Application mContext;
    @Nullable
    private GalleryConfig mConfig;
    private GalleryDataSource mDataSource;
    private ImageLoader mImageLoader;


    private GalleryManager(Application context) {
        mContext = context;
        mImageLoader = new GlideImageLoader(context);
    }

    /**
     * 设置数据源
     * @param dataSource 数据源
     */
    public void setDataSource(GalleryDataSource dataSource) {
        mDataSource = Objects.requireNonNull(dataSource, "图库数据源不能为空");
    }

    /**
     * 获取数据源
     * @return 数据源
     */
    @NonNull
    public GalleryDataSource getDataSource() {
        if (mDataSource == null) {
            // 如果数据源为空，默认使用APP内部的
            mDataSource = new AndroidDataSource(mContext);
        }
        return Objects.requireNonNull(mDataSource, "图库数据源没有提供");
    }

    /**
     * 设置配置项
     */
    public void setConfig(@Nullable GalleryConfig config) {
        mConfig = config;
    }

    /**
     * 获取配置项
     */
    @NonNull
    public GalleryConfig getConfig() {
        if (mConfig != null) {
            return mConfig;
        }
        // 从配置文件中加载
        String json = GalleryUtils.readerConfig(mContext);
        if (json != null) {
            mConfig = new Gson().fromJson(json, GalleryConfig.class);
        }
        if (mConfig == null) {
            mConfig = new GalleryConfig();
        }
        return mConfig;
    }

    /**
     * 设置图片加载器
     */
    public void setImageLoader(ImageLoader imageLoader) {
        mImageLoader = imageLoader;
    }

    /**
     * 获取图片加载器
     */
    public ImageLoader getImageLoader() {
        return mImageLoader;
    }
}
