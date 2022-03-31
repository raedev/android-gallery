package com.androidx.gallery.provider.impl;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.androidx.gallery.R;
import com.androidx.gallery.provider.ImageLoader;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.androidx.matisse.engine.ImageEngine;
import com.androidx.matisse.engine.impl.GlideEngine;
import com.androidx.matisse.internal.entity.SelectionSpec;

/**
 * Glide 默认加载器
 * @author RAE
 * @date 2022/01/18
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class GlideImageLoader implements ImageLoader {

    /**
     * 使用 Matisse Glide的加载
     */
    private final ImageEngine mEngine;
    private final Context mContext;

    public GlideImageLoader(Context context) {
        mEngine = new GlideEngine();
        mContext = context;
        SelectionSpec.getInstance().imageEngine = mEngine;
    }

    protected Drawable getPlaceholderDrawable() {
        return mContext.getResources().getDrawable(R.drawable.gallery_holder_img);
    }

    @Override
    public void loadThumbnail(Context context, int resize, ImageView imageView, Uri uri) {
        Glide.with(context)
                .asBitmap()
                .load(uri)
                .apply(new RequestOptions()
                        .override(resize, resize)
                        .placeholder(getPlaceholderDrawable())
                        .centerCrop())
                .centerCrop()
                .into(imageView);
    }

    @Override
    public void loadImage(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView) {
        mEngine.loadImage(context, -1, -1, imageView, Uri.parse(url));
    }

    @Override
    public void loadGif(Context context, String url, ImageView view) {
        mEngine.loadGifThumbnail(context, -1, null, view, Uri.parse(url));
    }
}
