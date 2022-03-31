package com.androidx.gallery.data.system;

import android.content.Context;

import com.androidx.gallery.data.AlbumProvider;
import com.androidx.gallery.data.DataSource;
import com.androidx.gallery.data.MediaProvider;

/**
 * 系统数据源
 * @author RAE
 * @date 2022/01/25
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class AndroidDataSource extends DataSource {
    public AndroidDataSource(Context context) {
        super(context);
    }

    @Override
    public AlbumProvider getAlbumProvider() {
        return null;
    }

    @Override
    public MediaProvider getMediaProvider() {
        return null;
    }
}
