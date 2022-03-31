package com.androidx.gallery.provider;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.androidx.gallery.entity.Albums;
import com.androidx.gallery.entity.OrderType;
import com.androidx.gallery.entity.Photo;
import com.androidx.gallery.entity.PhotoMeta;

import java.util.List;

/**
 * 照片数据提供者
 * @author RAE
 * @date 2022/01/18
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public interface PhotoProvider {

    /**
     * 绑定Activity（系统图库的时候需要自行调用该方法）
     */
    void onBindActivity(FragmentActivity activity);

    /**
     * 全部照片数量
     * @return 数量
     */
    int queryCount();

    /**
     * 分页查询照片
     * @param page 页码
     * @param pageSize 数量
     * @param keyword 查询关键字
     * @param orderBy 排序字段
     * @param orderType 排序类型
     * @return 照片列表
     */
    List<Photo> queryPhotos(int page,
                            int pageSize,
                            @Nullable String keyword,
                            @Nullable String orderBy,
                            @Nullable OrderType orderType);


    /**
     * 插入一张照片
     * @param photo 照片信息
     * @return 是否成功
     */
    boolean insert(Photo photo);

    /**
     * 更新照片信息
     * @param photo 照片
     * @return 是否成功
     */
    boolean update(Photo photo);

    /**
     * 删除照片
     * @param photo 照片
     * @return 是否成功
     */
    boolean delete(Photo photo);

    /**
     * 批量移动照片
     * @param photos 移动的照片列表
     * @param album 移动到的相册
     * @return 是否成功
     */
    boolean move(List<Photo> photos, Albums album);

    /**
     * 添加照片附加信息
     * <p>没有设计更新方法，更新可以先删除后添加</p>
     * @param photo 照片
     * @param name 字段
     * @param value 取值
     * @return 是否成功
     */
    boolean addMeta(Photo photo, String name, String value);

    /**
     * 删除照片信息
     * @param photo 照片
     * @param name 如果不指定键，将删除所有
     * @return 是否成功
     */
    boolean deleteMeta(Photo photo, @Nullable String name);

    /**
     * 查询照片附加信息
     * @param photo 照片
     * @return 附件信息列表
     */
    List<PhotoMeta> queryMetas(Photo photo);

}
