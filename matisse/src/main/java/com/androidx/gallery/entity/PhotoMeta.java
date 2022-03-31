package com.androidx.gallery.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * 照片附加信息
 * @author RAE
 * @date 2022/01/18
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
@Entity(tableName = "metas")
public class PhotoMeta implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private Long id;

    /**
     * 照片Id
     */
    private Long photoId;

    /**
     * 信息键
     */
    private String metaName;

    /**
     * 信息值
     */
    private String metaValue;

    public PhotoMeta() {
    }

    protected PhotoMeta(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        if (in.readByte() == 0) {
            photoId = null;
        } else {
            photoId = in.readLong();
        }
        metaName = in.readString();
        metaValue = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(id);
        }
        if (photoId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(photoId);
        }
        dest.writeString(metaName);
        dest.writeString(metaValue);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PhotoMeta> CREATOR = new Creator<PhotoMeta>() {
        @Override
        public PhotoMeta createFromParcel(Parcel in) {
            return new PhotoMeta(in);
        }

        @Override
        public PhotoMeta[] newArray(int size) {
            return new PhotoMeta[size];
        }
    };

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMetaName() {
        return metaName;
    }

    public void setMetaName(String metaName) {
        this.metaName = metaName;
    }

    public String getMetaValue() {
        return metaValue;
    }

    public void setMetaValue(String metaValue) {
        this.metaValue = metaValue;
    }

    public Long getPhotoId() {
        return photoId;
    }

    public void setPhotoId(Long photoId) {
        this.photoId = photoId;
    }
}
