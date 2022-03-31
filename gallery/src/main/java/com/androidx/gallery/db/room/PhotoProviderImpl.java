package com.androidx.gallery.db.room;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.sqlite.db.SimpleSQLiteQuery;

import com.androidx.gallery.exception.GalleryException;
import com.androidx.gallery.R;
import com.androidx.gallery.provider.PhotoProvider;
import com.androidx.gallery.entity.AlbumType;
import com.androidx.gallery.entity.Albums;
import com.androidx.gallery.entity.OrderType;
import com.androidx.gallery.entity.Photo;
import com.androidx.gallery.entity.PhotoMeta;
import com.androidx.gallery.entity.PhotoType;
import com.androidx.gallery.utils.PhotoUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author RAE
 * @date 2022/01/18
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
class PhotoProviderImpl extends BaseProvider implements PhotoProvider {

    private final PhotoDao mPhotoDao;
    private final AlbumsDao mAlbumsDao;

    PhotoProviderImpl(Context context) {
        super(context);
        mPhotoDao = mDatabase.getPhotoDao();
        mAlbumsDao = mDatabase.getAlbumsDao();
    }

    @Override
    public void onBindActivity(FragmentActivity activity) {
        // 应用内无需绑定Activity
    }

    @Override
    public int queryCount() {
        return mPhotoDao.count();
    }

    @Override
    public List<Photo> queryPhotos(int page, int pageSize, @Nullable String keyword, @Nullable String orderBy, @Nullable OrderType orderType) {
        StringBuilder sb = new StringBuilder();
        List<Object> args = new ArrayList<>();
        sb.append("SELECT * FROM PHOTOS WHERE state!=-1 ");
        if (!TextUtils.isEmpty(keyword)) {
            sb.append("AND fileName=? ");
            args.add(keyword);
        }
        // 默认按时间排序
        if (TextUtils.isEmpty(orderBy)) {
            orderBy = "createdAt";
            orderType = OrderType.DESC;
        }
        sb.append("ORDER BY ").append(orderBy).append(" ").append(orderType.name());
        sb.append("LIMIT ").append(pageSize).append(" OFFSET ").append(page * pageSize);
        return mPhotoDao.query(new SimpleSQLiteQuery(sb.toString(), args.toArray(new Object[0])));
    }

    @Override
    public boolean insert(Photo photo) {
        verifyPhoto(photo);
        return mPhotoDao.insert(photo) > 0;
    }

    @Override
    public boolean update(Photo photo) {
        if (photo.getId() == null) {
            throw new GalleryException(getString(R.string.gallery_error_update_photo_id));
        }
        verifyPhoto(photo);
        return mPhotoDao.update(photo) > 0;
    }

    @Override
    public boolean delete(Photo photo) {
        if (photo.getId() == null) {
            throw new GalleryException(getString(R.string.gallery_error_delete_photo_id));
        }
        Long albumId = photo.getAlbumId();
        Albums deletedAlbum = getSystemAlbum(AlbumType.DELETED);
        if (albumId.equals(deletedAlbum.getId())) {
            // 物理删除
            // 1、删除附加信息
            // 2、删除文件信息
            // 3、删除照片
            deleteMeta(photo, null);
            PhotoUtils.delete(photo.getFilePath());
            return mPhotoDao.delete(photo) > 0;
        }
        // 软删除
        photo.setState(-1);
        return mPhotoDao.update(photo) > 0;
    }

    @Override
    public boolean move(List<Photo> photos, Albums album) {
        boolean result = false;
        for (Photo photo : photos) {
            photo.setAlbumId(album.getId());
            photo.setState(0);
            result |= mPhotoDao.update(photo) > 0;
        }
        return result;
    }

    @Override
    public boolean addMeta(Photo photo, String name, String value) {
        PhotoMeta meta = new PhotoMeta();
        meta.setMetaName(name);
        meta.setMetaValue(value);
        meta.setPhotoId(photo.getId());
        return mPhotoDao.insert(meta) > 0;
    }

    @Override
    public boolean deleteMeta(Photo photo, @Nullable String name) {
        if (name == null) {
            // 删除所有
            return mPhotoDao.deletePhotoMeta(photo.getId()) > 0;
        }
        return mPhotoDao.deletePhotoMeta(photo.getId(), name) > 0;
    }

    @Override
    public List<PhotoMeta> queryMetas(Photo photo) {
        return mPhotoDao.queryPhotoMetas(photo.getId());
    }


    /**
     * 校验照片合法性并处理默认值字段
     * @param photo 照片
     */
    protected void verifyPhoto(Photo photo) {
        if (!PhotoUtils.exist(photo.getFilePath())) {
            throw new GalleryException(getString(R.string.gallery_error_photo_file_not_found, PhotoUtils.formatFilePath(mContext, photo.getFilePath())));
        }
        // 默认文件名
        if (TextUtils.isEmpty(photo.getFileName())) {
            photo.setFileName(new File(photo.getFilePath()).getName());
        }
        // 默认照片名称 = 文件名称
        if (TextUtils.isEmpty(photo.getName())) {
            photo.setName(photo.getFileName());
        }

        // 类型为空时默认是照片
        if (TextUtils.isEmpty(photo.getPhotoType())) {
            photo.setPhotoType(PhotoType.PHOTO.name());
        }

        // 默认为未分类相册
        if (photo.getAlbumId() == null) {
            Albums album = getSystemAlbum(AlbumType.UNKNOWN);
            photo.setAlbumId(album.getId());
        }

        // 检验相册的合法性
        Albums albums = mAlbumsDao.queryAlbums(photo.getAlbumId());
        if (albums == null) {
            throw new GalleryException(getString(R.string.gallery_error_album_not_found));
        }

        // 文件大小为空时自动计算
        if (photo.getFileSize() == null) {
            photo.setFileSize(PhotoUtils.calcFileSize(photo.getFilePath()));
        }

        // 计算图片宽高
        if (photo.getPhotoWidth() == null || photo.getPhotoHeight() == null) {
            Bitmap bitmap = BitmapFactory.decodeFile(photo.getFilePath());
            long width = bitmap.getWidth();
            long height = bitmap.getHeight();
            photo.setPhotoWidth(width);
            photo.setPhotoHeight(height);
            bitmap.recycle();
        }

        // 默认创建时间
        if (photo.getCreatedAt() == null) {
            photo.setCreatedAt(System.currentTimeMillis());
        }
    }


    /**
     * 获取系统相册
     * @param type 相册类型
     * @return 相册
     * @throws GalleryException 获取不到时抛出异常
     */
    protected Albums getSystemAlbum(AlbumType type) {
        // 从相册中查询
        Albums albums = mAlbumsDao.queryAlbumsByType(type.name());
        if (albums == null) {
            // 新建一个相册
            albums = new Albums();
            String typeName = type.name().toLowerCase(Locale.ROOT);
            int id = mContext.getResources().getIdentifier("gallery_" + typeName + "_albums_name", "string", mContext.getPackageName());
            albums.setName(getString(id));
            albums.setCode(type.name());
            albums.setAlbumType(type.name());
            albums.setCreatedAt(System.currentTimeMillis());
            if (mAlbumsDao.insert(albums) <= 0) {
                throw new GalleryException(getString(R.string.gallery_error_insert_unknown_albums));
            }
            // 重新查询
            albums = mAlbumsDao.queryAlbumsByType(AlbumType.UNKNOWN.name());
        }

        if (albums == null) {
            throw new GalleryException(getString(R.string.gallery_error_insert_unknown_albums));
        }

        return albums;
    }
}
