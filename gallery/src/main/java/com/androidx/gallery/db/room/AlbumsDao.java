package com.androidx.gallery.db.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.androidx.gallery.entity.Albums;

/**
 * 相册数据库操作
 * @author RAE
 * @date 2022/01/18
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
@Dao
interface AlbumsDao {

    /**
     * 根据主键查询相册
     * @param id 相册ID
     * @return 相册
     */
    @Query("SELECT * FROM ALBUMS WHERE id = :id")
    Albums queryAlbums(Long id);

    /**
     * 根据编码查询相册
     * @param code 程序编码
     * @return 相册
     */
    @Query("SELECT * FROM ALBUMS WHERE code = :code")
    Albums queryAlbums(String code);

    /**
     * 根据程序编码和业务编码查询相册
     * @param code 程序编码
     * @param relationCode 业务编码
     * @return 相册
     */
    @Query("SELECT * FROM ALBUMS WHERE code =:code AND relationCode=:relationCode")
    Albums queryAlbums(String code, String relationCode);


    /**
     * 查询指定类型的相册
     * @param type 相册类型
     * @return 相册列表
     */
    @Query("SELECT * FROM ALBUMS WHERE albumType = :type LIMIT 1")
    Albums queryAlbumsByType(String type);


    /**
     * 插入相册
     * @param albums 相册
     * @return 是否成功
     */
    @Insert
    Long insert(Albums albums);

    /**
     * 更新相册
     * @param albums 相册
     * @return 是否成功
     */
    @Update
    int update(Albums albums);

    /**
     * 删除相册
     * @param albums 相册
     * @return 是否成功
     */
    @Delete
    int delete(Albums albums);
}
