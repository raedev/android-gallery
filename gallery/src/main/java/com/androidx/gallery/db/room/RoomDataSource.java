package com.androidx.gallery.db.room;

import android.content.Context;

import androidx.annotation.NonNull;

import com.androidx.gallery.provider.AlbumProvider;
import com.androidx.gallery.db.GalleryDataSource;
import com.androidx.gallery.provider.PhotoProvider;

/**
 * APP内部提供程序
 * @author RAE
 * @date 2022/01/18
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class RoomDataSource implements GalleryDataSource {

    private final Context mContext;
    private AlbumProvider mAlbumDataProvider;
    private PhotoProvider mPhotoProvider;

    public RoomDataSource(Context context) {
        mContext = context;
    }

    @Override
    @NonNull
    public AlbumProvider getAlbumProvider() {
        if (mAlbumDataProvider == null) {
            mAlbumDataProvider = new AlbumProviderImpl(mContext);
        }
        return mAlbumDataProvider;
    }

    @Override
    @NonNull
    public PhotoProvider getPhotoProvider() {
        if (mPhotoProvider == null) {
            mPhotoProvider = new PhotoProviderImpl(mContext);
        }
        return mPhotoProvider;
    }
}
