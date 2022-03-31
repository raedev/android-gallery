package com.androidx.gallery.listener;

import com.androidx.gallery.entity.Albums;

import java.util.List;

/**
 * 相册数据回调
 * @author RAE
 * @date 2022/01/26
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public interface AlbumProviderListener {

    /**
     * 相册列表加载完成
     * @param page 页码
     * @param data 数据
     */
    void onAlbumLoaded(int page, List<Albums> data);
}
