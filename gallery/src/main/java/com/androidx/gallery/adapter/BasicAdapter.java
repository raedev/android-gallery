package com.androidx.gallery.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidx.gallery.GalleryManager;
import com.androidx.gallery.adapter.holder.GalleryViewHolder;
import com.androidx.gallery.exception.GalleryException;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * 基础的适配器
 * @author RAE
 * @date 2022/01/20
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public abstract class BasicAdapter<T> extends RecyclerView.Adapter<GalleryViewHolder> {

    protected final List<T> mDataList = new ArrayList<>();
    protected final List<T> mSelectedList = new ArrayList<>();
    protected WeakReference<GalleryManager> mManagerWeakReference;

    public BasicAdapter() {
        mManagerWeakReference = new WeakReference<>(GalleryManager.getInstance());
    }

    @LayoutRes
    protected int getLayoutId() {
        return 0;
    }

    protected GalleryManager getGalleryManager() {
        return mManagerWeakReference.get();
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    @NonNull
    @Override
    public GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (getLayoutId() == 0) {
            throw new GalleryException("请重写getLayoutId或者覆盖onCreateViewHolder实现布局的创建");
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(getLayoutId(), parent, false);
        return new GalleryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryViewHolder holder, int position) {
        T data = mDataList.get(position);
        onBindViewHolder(holder, data);
    }

    public void setDataList(List<T> data) {
        mDataList.clear();
        mDataList.addAll(data);
    }

    public int indexOf(T item) {
        return mDataList.indexOf(item);
    }

    /**
     * 清除所有选择
     */
    public void clearSelectItems() {
        mDataList.clear();
        notifyDataSetChanged();
    }

    /**
     * 绑定数据
     * @param holder base view holder
     * @param item data item
     */
    protected abstract void onBindViewHolder(@NonNull GalleryViewHolder holder, T item);

    /**
     * 加载图片
     * @param filePath 图片地址
     * @param view ImageView
     */
    protected void showImageFile(String filePath, ImageView view) {
        getGalleryManager().getImageLoader().loadImage(view.getContext(), filePath, view);
    }

}