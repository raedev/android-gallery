package com.androidx.gallery.internal;

import android.util.Log;

/**
 * @author RAE
 * @date 2022/01/20
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public final class GalleryLog {
    private static final String TAG = "Gallery";

    public static void d(String msg) {
        Log.d(TAG, msg);
    }

    public static void e(String msg, Throwable e) {
        Log.e(TAG, msg, e);
    }
}
