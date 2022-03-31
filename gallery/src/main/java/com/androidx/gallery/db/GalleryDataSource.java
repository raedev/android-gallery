package com.androidx.gallery.db;

import androidx.annotation.NonNull;

import com.androidx.gallery.provider.AlbumProvider;
import com.androidx.gallery.provider.PhotoProvider;

/**
 * 图库数据源，提供者模式
 * @author RAE
 * @date 2022/01/18
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public interface GalleryDataSource {

    /**
     * 相册数据提供者
     * @return 提供者
     */
    @NonNull
    AlbumProvider getAlbumProvider();


    /**
     * 照片数据提供者
     * @return 提供者
     */
    @NonNull
    PhotoProvider getPhotoProvider();
}
