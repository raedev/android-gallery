package com.androidx.gallery.callback;

import com.androidx.gallery.entity.Photo;

/**
 * 照片点击回调
 * @author RAE
 * @date 2022/01/24
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public interface PhotoItemClickListener {

    /**
     * 照片点击
     * @param position 所在索引
     * @param photo 照片
     */
    void onPhotoItemClick(int position, Photo photo);
}
