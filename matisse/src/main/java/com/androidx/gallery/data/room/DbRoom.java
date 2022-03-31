package com.androidx.gallery.data.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
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
abstract class DbRoom extends RoomDatabase {

    /**
     * 获取相册数据接口
     * @return 接口
     */
    abstract AlbumDao getAlbumDao();

    /**
     * 获取照片接口
     * @return 接口
     */
    abstract PhotoDao getPhotoDao();


    /**
     * 创建数据库实例
     * @param context Context
     * @return 数据库
     */
    static DbRoom create(Context context) {
        return Room.databaseBuilder(context, DbRoom.class, "gallery.db").build();
    }
}
