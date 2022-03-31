package com.androidx.gallery.db.os;

import android.content.Context;

import com.androidx.gallery.entity.Albums;
import com.androidx.gallery.entity.OrderType;
import com.androidx.gallery.provider.AlbumProvider;

import java.util.List;

/**
 * 相册
 * @author RAE
 * @date 2022/01/20
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class AlbumProviderImpl implements AlbumProvider {
    public AlbumProviderImpl(Context context) {

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
