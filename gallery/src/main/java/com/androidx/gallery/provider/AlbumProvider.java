package com.androidx.gallery.provider;

import com.androidx.gallery.entity.Albums;
import com.androidx.gallery.entity.OrderType;

import java.util.List;

/**
 * 相册数据提供者
 * @author RAE
 * @date 2022/01/18
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public interface AlbumProvider {

    /**
     * 查询所有相册
     * @param page 分页
     * @param pageSize 数量
     * @param orderBy 排序字段
     * @param orderType 排序类型
     * @return 相册列表
     */
    List<Albums> queryAlbums(int page, int pageSize, String orderBy, OrderType orderType);


    /**
     * 新增一个相册
     * @param album 相册
     * @return 是否成功
     */
    boolean insert(Albums album);

    /**
     * 更新一个相册
     * @param album 相册
     * @return 是否成功
     */
    boolean update(Albums album);

    /**
     * 删除一个相册
     * @param album 相册
     * @return 是否成功
     */
    boolean delete(Albums album);

}
