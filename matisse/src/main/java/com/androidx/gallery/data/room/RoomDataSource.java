package com.androidx.gallery.data.room;

import android.content.Context;

import com.androidx.gallery.data.AlbumProvider;
import com.androidx.gallery.data.DataSource;
import com.androidx.gallery.data.MediaProvider;

/**
 * @author RAE
 * @date 2022/01/25
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class RoomDataSource extends DataSource {

    AlbumProvider mAlbumProvider;

    public RoomDataSource(Context context) {
        super(context);
        mAlbumProvider = new RoomAlbumProviderImpl(context);
    }

    @Override
    public AlbumProvider getAlbumProvider() {
        return mAlbumProvider;
    }

    @Override
    public MediaProvider getMediaProvider() {
        return null;
    }
}
