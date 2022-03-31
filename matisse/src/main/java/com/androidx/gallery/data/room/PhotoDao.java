package com.androidx.gallery.data.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.room.Update;
import androidx.sqlite.db.SimpleSQLiteQuery;

import com.androidx.gallery.entity.Photo;
import com.androidx.gallery.entity.PhotoMeta;

import java.util.List;

/**
 * 相册数据库操作
 * @author RAE
 * @date 2022/01/18
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
@Dao
interface PhotoDao {

    /**
     * 查询数据
     * @param query SQL查询语句
     * @return 列表
     */
    @RawQuery
    List<Photo> query(SimpleSQLiteQuery query);

    /**
     * 插入照片
     * @param photo 照片
     * @return 结果
     */
    @Insert
    Long insert(Photo photo);

    /**
     * 更新照片
     * @param photo 照片
     * @return 结果
     */
    @Update
    int update(Photo photo);

    /**
     * 删除照片
     * @param photo 照片
     * @return 结果
     */
    @Delete
    int delete(Photo photo);

    /**
     * 插入照片附加信息
     * @param meta 附加信息
     * @return 结果
     */
    @Insert
    Long insert(PhotoMeta meta);

    /**
     * 删除照片附加信息
     * @param id 照片ID
     * @return 结果
     */
    @Query("DELETE FROM METAS WHERE photoId=:id")
    int deletePhotoMeta(Long id);

    /**
     * 删除照片附加信息
     * @param id 照片ID
     * @param name 键
     * @return 结果
     */
    @Query("DELETE FROM METAS WHERE photoId=:id AND metaName=:name")
    int deletePhotoMeta(Long id, String name);

    /**
     * 查询照片的所有附加信息
     * @param id 照片ID
     * @return 附件信息列表
     */
    @Query("SELECT * FROM METAS WHERE photoId=:id")
    List<PhotoMeta> queryPhotoMetas(Long id);

    /**
     * 查询照片总数
     * @return 数量
     */
    @Query("SELECT COUNT(*) FROM PHOTOS")
    int count();
}
