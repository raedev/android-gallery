package com.androidx.gallery.provider;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import androidx.annotation.NonNull;

/**
 * 图片加载器
 * @author RAE
 * @date 2022/01/18
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public interface ImageLoader {


    /**
     * 加载预览图
     * @param context
     * @param resize 预览图大小
     * @param imageView
     * @param uri
     */
    void loadThumbnail(Context context, int resize, ImageView imageView, Uri uri);

    /**
     * 加载图片
     * @param context Context
     * @param url 图片地址
     * @param imageView ImageView
     */
    void loadImage(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView);

    /**
     * 加载GIF
     * @param context Context
     * @param url Url
     * @param view View
     */
    void loadGif(Context context, String url, ImageView view);
}
