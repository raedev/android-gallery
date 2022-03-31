package com.androidx.gallery.adapter.holder;

import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Base RecyclerView.ViewHolder
 * @author RAE
 * @date 2022/01/20
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class GalleryViewHolder extends RecyclerView.ViewHolder {

    private final SparseArray<View> mViewList = new SparseArray<>();

    public GalleryViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    @SuppressWarnings("unchecked")
    public <T> T findViewById(@IdRes int id) {
        View view = mViewList.get(id);
        if (view == null) {
            view = itemView.findViewById(id);
            if (view == null) {
                return null;
            }
            mViewList.put(id, view);
        }
        return (T) view;
    }

}
