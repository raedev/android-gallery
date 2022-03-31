package com.androidx.gallery.db.room;

import android.content.Context;

import androidx.annotation.StringRes;
import androidx.room.Room;

/**
 * @author RAE
 * @date 2022/01/18
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
abstract class BaseProvider {
    protected final GalleryDatabase mDatabase;
    protected final Context mContext;

    BaseProvider(Context context) {
        mContext = context;
        mDatabase = Room.databaseBuilder(context, GalleryDatabase.class, "gallery").build();
    }

    protected String getString(@StringRes int resId) {
        return mContext.getString(resId);
    }

    protected String getString(@StringRes int resId, String... args) {
        return mContext.getString(resId, args);
    }
}
