package com.androidx.gallery.data.room;

import android.content.Context;

import com.androidx.gallery.data.AlbumProvider;
import com.androidx.gallery.entity.Albums;
import com.androidx.gallery.entity.OrderType;

import java.util.List;

/**
 * 应用内相册接口实现
 * @author RAE
 * @date 2022/01/26
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class RoomAlbumProviderImpl extends BaseProviderImpl implements AlbumProvider {

    RoomAlbumProviderImpl(Context context) {
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
