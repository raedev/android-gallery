package com.androidx.gallery;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.androidx.gallery.data.DataSource;
import com.androidx.gallery.data.room.RoomDataSource;
import com.androidx.gallery.data.system.AndroidDataSource;
import com.androidx.gallery.entity.GallerySpec;
import com.androidx.matisse.engine.ImageEngine;
import com.androidx.matisse.engine.impl.GlideEngine;
import com.androidx.matisse.engine.impl.PicassoEngine;

import java.lang.ref.WeakReference;
import java.util.Objects;

/**
 * 图库管理器，图库的主入口
 * @author RAE
 * @date 2022/01/25
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
@SuppressWarnings("unused")
public final class GalleryManager {

    /**
     * 实例化
     */
    public static GalleryManager from(Context context) {
        return new GalleryManager(new WeakReference<>(context.getApplicationContext()));
    }

    // region 成员变量

    private final WeakReference<Context> mContextReference;
    private final GallerySpec mSpec = new GallerySpec();
    @Nullable
    private DataSource mDataSource;
    @Nullable
    private ImageEngine mImageEngine;

    private GalleryManager(WeakReference<Context> contextReference) {
        mContextReference = contextReference;
    }

    // endregion

    // region 私有方法

    private Context getContext() {
        return Objects.requireNonNull(mContextReference.get(), "context can't be null");
    }

    /**
     * 根据配置项创建数据源
     */
    private DataSource createDataSource() {
        if (mSpec.datasource == GallerySpec.DATA_SOURCE_TYPE_SYSTEM) {
            return new AndroidDataSource(getContext());
        }
        return new RoomDataSource(getContext());
    }

    // endregion


    /**
     * 设置图片加载方式
     * @param engine 图片加载实现类
     */
    public void setImageEngine(@Nullable ImageEngine engine) {
        mImageEngine = engine;
    }

    /**
     * 获取图库数据接口
     */
    @NonNull
    public DataSource getDataSource() {
        if (mDataSource != null) {
            return mDataSource;
        }
        mDataSource = createDataSource();
        return mDataSource;
    }

    /**
     * 获取图片加载引擎
     */
    @NonNull
    public ImageEngine getImageEngine() {
        if (mImageEngine != null) {
            return mImageEngine;
        }
        // 默认是图片加载引擎
        if (mSpec.imageEngine == GallerySpec.IMAGE_ENGINE_GLIDE) {
            mImageEngine = new GlideEngine();
        } else {
            mImageEngine = new PicassoEngine();
        }
        return mImageEngine;
    }
}
