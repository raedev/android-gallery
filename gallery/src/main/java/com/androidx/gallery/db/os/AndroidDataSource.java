package com.androidx.gallery.db.os;

import android.app.Application;

import androidx.annotation.NonNull;

import com.androidx.gallery.db.GalleryDataSource;
import com.androidx.gallery.provider.AlbumProvider;
import com.androidx.gallery.provider.PhotoProvider;

/**
 * Android系统数据源
 * @author RAE
 * @date 2022/01/20
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class AndroidDataSource implements GalleryDataSource {
    AlbumProvider mAlbumProvider;
    PhotoProvider mPhotoProvider;

    public AndroidDataSource(Application context) {
        mPhotoProvider = new PhotoProviderImpl(context);
        mAlbumProvider = new AlbumProviderImpl(context);
    }

    @NonNull
    @Override
    public AlbumProvider getAlbumProvider() {
        return mAlbumProvider;
    }

    @NonNull
    @Override
    public PhotoProvider getPhotoProvider() {
        return mPhotoProvider;
    }
}
