package com.androidx.gallery.ui.widget;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;

import com.androidx.gallery.GalleryManager;
import com.androidx.gallery.R;
import com.androidx.gallery.entity.Photo;
import com.androidx.gallery.provider.ImageLoader;
import com.androidx.matisse.internal.entity.SelectionSpec;
import com.androidx.matisse.internal.ui.widget.CheckView;

/**
 * @author RAE
 * @date 2022/01/24
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class MediaGrid extends com.androidx.matisse.internal.ui.widget.MediaGrid {

    public interface OnMediaGridClickListener {
        void onThumbnailClicked(ImageView thumbnail, Photo item, MediaGrid grid);

        void onCheckViewClicked(CheckView checkView, Photo item, MediaGrid grid);
    }

    protected ImageView mThumbnail;
    protected CheckView mCheckView;
    protected ImageView mGifTag;
    protected TextView mVideoDuration;
    protected Photo mPhoto;
    protected boolean mEnableCheck;
    protected int mImageResize;
    protected GridLayoutManager mLayoutManager;
    private OnMediaGridClickListener mListener;

    public MediaGrid(Context context) {
        super(context);
        initView();
    }

    public MediaGrid(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    protected void initView() {
        mThumbnail = (ImageView) findViewById(R.id.media_thumbnail);
        mCheckView = (CheckView) findViewById(R.id.check_view);
        mGifTag = (ImageView) findViewById(R.id.gif);
        mVideoDuration = (TextView) findViewById(R.id.video_duration);
    }

    public void bindMedia(GridLayoutManager layoutManager, Photo item) {
        mLayoutManager = layoutManager;
        mPhoto = item;
        loadImage();
        mCheckView.setVisibility(mEnableCheck ? View.VISIBLE : View.GONE);
    }

    public void setEnableCheck(boolean enableCheck) {
        mEnableCheck = enableCheck;
    }


    public void setOnMediaGridClickListener(OnMediaGridClickListener listener) {
        mListener = listener;
    }

    @Override
    public void onClick(View v) {
        if (mListener != null) {
            if (v == mThumbnail) {
                mListener.onThumbnailClicked(mThumbnail, mPhoto, this);
            } else if (v == mCheckView) {
                mListener.onCheckViewClicked(mCheckView, mPhoto, this);
            }
        }
    }

    protected void loadImage() {
        ImageLoader imageLoader = GalleryManager.getInstance().getImageLoader();
        if (mPhoto.isGif()) {
            imageLoader.loadGif(getContext(), mPhoto.getFilePath(), mThumbnail);
        } else {
            imageLoader.loadThumbnail(getContext(), getImageResize(), mThumbnail, Uri.parse(mPhoto.getFilePath()));
        }
    }


    private int getImageResize() {
        Context context = getContext();
        if (mImageResize == 0) {
            int spanCount = ((GridLayoutManager) mLayoutManager).getSpanCount();
            int screenWidth = context.getResources().getDisplayMetrics().widthPixels;
            int availableWidth = screenWidth - context.getResources().getDimensionPixelSize(
                    R.dimen.media_grid_spacing) * (spanCount - 1);
            mImageResize = availableWidth / spanCount;
            mImageResize = (int) (mImageResize * SelectionSpec.getInstance().thumbnailScale);
        }
        return mImageResize;
    }
}
