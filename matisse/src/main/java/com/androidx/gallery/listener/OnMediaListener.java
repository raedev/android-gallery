package com.androidx.gallery.listener;

import com.androidx.gallery.entity.Photo;

import java.util.List;

/**
 * 媒体信息回调接口
 * @author RAE
 * @date 2022/01/26
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public interface OnMediaListener {

    /**
     * 加载媒体列表成功
     * @param data 列表数据
     */
    void onMediaLoaded(List<Photo> data);

    /**
     * 媒体列表加载失败
     * @param errorCode 错误代码
     * @param message 错误信息
     */
    void onMediaError(int errorCode, String message);


}
