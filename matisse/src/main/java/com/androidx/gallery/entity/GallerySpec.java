package com.androidx.gallery.entity;

/**
 * 图库参数
 * @author RAE
 * @date 2022/01/25
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class GallerySpec {

    /**
     * 系统相册数据
     */
    public static final int DATA_SOURCE_TYPE_SYSTEM = 0;

    /**
     * APP 内部数据源
     */
    public static final int DATA_SOURCE_TYPE_APP = 1;

    /**
     * glide
     */
    public static final int IMAGE_ENGINE_GLIDE = 0;

    /**
     * picasso
     */
    public static final int IMAGE_ENGINE_PICASSO = 1;


    /**
     * 数据来源
     */
    public int datasource;

    /**
     * 默认图片加载器
     */
    public int imageEngine;


}
