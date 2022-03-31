package com.androidx.gallery.exception;

/**
 * 图库异常
 * @author RAE
 * @date 2022/01/18
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class GalleryException extends RuntimeException {

    public GalleryException(String message) {
        super(message);
    }

    public GalleryException(String message, Throwable cause) {
        super(message, cause);
    }
}
