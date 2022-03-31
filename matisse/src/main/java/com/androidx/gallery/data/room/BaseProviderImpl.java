package com.androidx.gallery.data.room;

import android.content.Context;

import androidx.annotation.StringRes;

/**
 * @author RAE
 * @date 2022/01/18
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
abstract class BaseProviderImpl {

    protected final DbRoom mDatabase;
    protected final Context mContext;

    BaseProviderImpl(Context context) {
        mContext = context;
        mDatabase = DbRoom.create(context);
    }

    protected String getString(@StringRes int resId) {
        return mContext.getString(resId);
    }

    protected String getString(@StringRes int resId, String... args) {
        return mContext.getString(resId, args);
    }
}
