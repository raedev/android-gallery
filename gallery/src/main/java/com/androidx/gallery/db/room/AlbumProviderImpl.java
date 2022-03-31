package com.androidx.gallery.db.room;

import android.content.Context;

import com.androidx.gallery.provider.AlbumProvider;
import com.androidx.gallery.entity.Albums;
import com.androidx.gallery.entity.OrderType;

import java.util.List;

/**
 * 内部相册提供程序
 * @author RAE
 * @date 2022/01/18
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
class AlbumProviderImpl extends BaseProvider implements AlbumProvider {

    AlbumProviderImpl(Context context) {
        super(context);
    }

    @Override
    public List<Albums> queryAlbums(int page, int pageSize, String orderBy, OrderType orderType) {
        return null;
    }

    @Override
    public boolean insert(Albums album) {
        return false;
    }

    @Override
    public boolean update(Albums album) {
        return false;
    }

    @Override
    public boolean delete(Albums album) {
        return false;
    }
}
