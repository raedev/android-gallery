package com.androidx.gallery.db.os;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;

import com.androidx.gallery.callback.GalleryDataSourceCallBack;
import com.androidx.gallery.entity.Albums;
import com.androidx.gallery.entity.OrderType;
import com.androidx.gallery.entity.Photo;
import com.androidx.gallery.entity.PhotoMeta;
import com.androidx.gallery.entity.PhotoType;
import com.androidx.gallery.exception.GalleryException;
import com.androidx.gallery.provider.PhotoProvider;
import com.androidx.matisse.internal.entity.Album;
import com.androidx.matisse.internal.entity.Item;
import com.androidx.matisse.internal.model.AlbumCollection;
import com.androidx.matisse.internal.model.AlbumMediaCollection;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * 系统照片
 * @author RAE
 * @date 2022/01/20
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public class PhotoProviderImpl implements PhotoProvider, LifecycleEventObserver, AlbumCollection.AlbumCallbacks, AlbumMediaCollection.AlbumMediaCallbacks {

    private final AlbumCollection mAlbumCollection = new AlbumCollection();
    private final AlbumMediaCollection mAlbumMediaCollection = new AlbumMediaCollection();
    private final List<Photo> mItems = new ArrayList<>();
    private final Context mContext;
    private WeakReference<Activity> mActivityWeakReference;

    public PhotoProviderImpl(Context context) {
        mContext = context;
    }

    @Override
    public void onBindActivity(FragmentActivity activity) {
        activity.getLifecycle().addObserver(this);
        mActivityWeakReference = new WeakReference<>(activity);
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
            }, 100);
            return;
        }
        mAlbumCollection.onCreate(activity, this);
        mAlbumMediaCollection.onCreate(activity, this);
        mAlbumCollection.loadAlbums();
    }

    @Override
    public int queryCount() {
        return mItems.size();
    }

    @Override
    public List<Photo> queryPhotos(int page, int pageSize, @Nullable String keyword, @Nullable String orderBy, @Nullable OrderType orderType) {
        int size = pageSize - 1;
        int start = page * size;
        int end = Math.min(start + size, mItems.size());
        if (start > mItems.size()) {
            return null;
        }
        return mItems.subList(start, end);
    }

    @Override
    public boolean insert(Photo photo) {
        throw new GalleryException("不支持insert功能");
    }

    @Override
    public boolean update(Photo photo) {
        throw new GalleryException("不支持更新功能");
    }

    @Override
    public boolean delete(Photo photo) {
        throw new GalleryException("不支持删除功能");
    }

    @Override
    public boolean move(List<Photo> photos, Albums album) {
        throw new GalleryException("不支持移动功能");
    }

    @Override
    public boolean addMeta(Photo photo, String name, String value) {
        throw new GalleryException("不支持addMeta功能");
    }

    @Override
    public boolean deleteMeta(Photo photo, @Nullable String name) {
        throw new GalleryException("不支持deleteMeta功能");
    }

    @Override
    public List<PhotoMeta> queryMetas(Photo photo) {
        throw new GalleryException("不支持queryMetas功能");
    }

    @Override
    public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
        if (event == Lifecycle.Event.ON_DESTROY) {
            source.getLifecycle().removeObserver(this);
            mAlbumCollection.onDestroy();
            mAlbumMediaCollection.onDestroy();
        }
    }

    @Override
    public void onAlbumLoad(Cursor cursor) {
        while (cursor.moveToNext()) {
            Album album = Album.valueOf(cursor);
            if (album.isAll()) {
                // 加载媒体信息
                mAlbumMediaCollection.load(album, false);
                break;
            }
        }
    }

    @Override
    public void onAlbumReset() {

    }

    @Override
    public void onAlbumMediaLoad(Cursor cursor) {
        mItems.clear();
        while (cursor.moveToNext()) {
            Item item = Item.valueOf(cursor);
            if (!item.isVideo() && !item.isImage()) {
                continue;
            }
            Photo photo = new Photo();
            photo.setPhotoType(item.isImage() ? PhotoType.PHOTO.name() : PhotoType.VIDEO.name());
            photo.setFilePath(item.uri.toString());
            photo.setFileSize(item.size);
            photo.setVideoLength((double) item.duration);
            // 查询图片其他信息
            photo.setFileName(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DISPLAY_NAME)));
            photo.setName(photo.getFileName());
//            photo.setPhotoWidth((long) cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.WIDTH)));
//            photo.setPhotoHeight((long) cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.HEIGHT)));
//            photo.setCreatedAt(cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATE_ADDED)));
            mItems.add(photo);
        }
        // 加载完成
        if (mActivityWeakReference != null && mActivityWeakReference.get() instanceof GalleryDataSourceCallBack) {
            ((GalleryDataSourceCallBack) mActivityWeakReference.get()).onGalleryLoadFinish();
        }
    }

    @Override
    public void onAlbumMediaReset() {

    }

}
