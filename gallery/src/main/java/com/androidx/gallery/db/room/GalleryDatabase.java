package com.androidx.gallery.db.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.androidx.gallery.entity.Albums;
import com.androidx.gallery.entity.Photo;
import com.androidx.gallery.entity.PhotoMeta;

/**
 * 内部图库数据库
 * @author RAE
 * @date 2022/01/18
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
@Database(entities = {
        Photo.class,
        Albums.class,
        PhotoMeta.class
}, version = 1)
abstract class GalleryDatabase extends RoomDatabase {

    /**
     * 获取相册数据接口
     * @return 接口
     */
    abstract AlbumsDao getAlbumsDao();

    /**
     * 获取照片接口
     * @return 接口
     */
    abstract PhotoDao getPhotoDao();
}
