package com.androidx.gallery.adapter;

import android.widget.ImageView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;

import com.androidx.gallery.R;
import com.androidx.gallery.adapter.holder.GalleryViewHolder;
import com.androidx.gallery.callback.PhotoItemClickListener;
import com.androidx.gallery.entity.Photo;
import com.androidx.gallery.ui.widget.MediaGrid;
import com.androidx.matisse.internal.entity.Item;
import com.androidx.matisse.internal.entity.SelectionSpec;
import com.androidx.matisse.internal.ui.widget.CheckView;

/**
 * 照片Item适配器
 * @author RAE
 * @date 2022/01/20
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class PhotoItemAdapter extends BasicAdapter<Photo> implements MediaGrid.OnMediaGridClickListener {

    protected final GridLayoutManager mLayoutManager;
    protected boolean mEnableCheck;
    protected PhotoItemClickListener mOnPhotoItemClickListener;

    public PhotoItemAdapter(GridLayoutManager layoutManager) {
        mLayoutManager = layoutManager;
    }

    /**
     * 是否显示选择框
     * @param enableCheck 是否可以选择
     */
    public void setEnableCheck(boolean enableCheck) {
        mEnableCheck = enableCheck;
    }

    /**
     * 图片点击
     * @param photoItemClickListener 回调
     */
    public void setOnPhotoItemClickListener(PhotoItemClickListener photoItemClickListener) {
        mOnPhotoItemClickListener = photoItemClickListener;
    }

    @Override
    @LayoutRes
    protected int getLayoutId() {
        return R.layout.gallery_media_grid_item;
    }

    @Override
    protected void onBindViewHolder(@NonNull GalleryViewHolder holder, Photo item) {
        MediaGrid grid = (MediaGrid) holder.itemView;
        grid.setEnableCheck(mEnableCheck);
        grid.bindMedia(mLayoutManager, item);
        grid.setOnMediaGridClickListener(this);
    }


    private void setCheckStatus(Item item, com.androidx.matisse.internal.ui.widget.MediaGrid mediaGrid) {
        SelectionSpec spec = SelectionSpec.getInstance();
        if (spec.countable) {
            int checkedNum = mSelectedCollection.checkedNumOf(item);
            if (checkedNum > 0) {
                mediaGrid.setCheckEnabled(true);
                mediaGrid.setCheckedNum(checkedNum);
            } else {
                if (mSelectedCollection.maxSelectableReached()) {
                    mediaGrid.setCheckEnabled(false);
                    mediaGrid.setCheckedNum(CheckView.UNCHECKED);
                } else {
                    mediaGrid.setCheckEnabled(true);
                    mediaGrid.setCheckedNum(checkedNum);
                }
            }
        } else {
            boolean selected = mSelectedCollection.isSelected(item);
            if (selected) {
                mediaGrid.setCheckEnabled(true);
                mediaGrid.setChecked(true);
            } else {
                if (mSelectedCollection.maxSelectableReached()) {
                    mediaGrid.setCheckEnabled(false);
                    mediaGrid.setChecked(false);
                } else {
                    mediaGrid.setCheckEnabled(true);
                    mediaGrid.setChecked(false);
                }
            }
        }
    }


    protected void callbackPhotoItemClickListener(Photo item) {
        if (mOnPhotoItemClickListener != null) {
            int index = indexOf(item);
            mOnPhotoItemClickListener.onPhotoItemClick(index, item);
        }
    }



    @Override
    public void onThumbnailClicked(ImageView thumbnail, Photo item, MediaGrid grid) {
        // 图片点击
        callbackPhotoItemClickListener(item);
    }

    @Override
    public void onCheckViewClicked(CheckView checkView, Photo item, MediaGrid grid) {
        // 选择
    }
}
