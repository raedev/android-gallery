package com.androidx.gallery.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * 相册
 * @author RAE
 * @date 2022/01/18
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
@Entity(tableName = "albums")
public class Albums implements Parcelable {

    /**
     * 相册ID
     */
    @PrimaryKey(autoGenerate = true)
    private Long id;

    /**
     * 父相册ID
     * <p>相册是个目录，相册下面还有子相册</p>
     */
    private Long parentId;

    /**
     * 相册名称
     */
    private String name;

    /**
     * 相册程序编码（代码中的取值）
     */
    private String code;

    /**
     * 相册跟业务关联的代码
     */
    private String relationCode;

    /**
     * 相册类型
     * <ul>
     * <li>normal: 普通相册</li>
     * <li>favorite: 收藏相册</li>
     * <li>deleted: 已删除的相册</li>
     * <li>unknown: 未分类的相册</li>
     * </ul>
     */
    @ColumnInfo(defaultValue = "normal")
    private String albumType;


    /**
     * 创建相册时间戳
     */
    private Long createdAt;

    public Albums() {
    }

    protected Albums(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        if (in.readByte() == 0) {
            parentId = null;
        } else {
            parentId = in.readLong();
        }
        name = in.readString();
        code = in.readString();
        relationCode = in.readString();
        albumType = in.readString();
        if (in.readByte() == 0) {
            createdAt = null;
        } else {
            createdAt = in.readLong();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(id);
        }
        if (parentId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(parentId);
        }
        dest.writeString(name);
        dest.writeString(code);
        dest.writeString(relationCode);
        dest.writeString(albumType);
        if (createdAt == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(createdAt);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Albums> CREATOR = new Creator<Albums>() {
        @Override
        public Albums createFromParcel(Parcel in) {
            return new Albums(in);
        }

        @Override
        public Albums[] newArray(int size) {
            return new Albums[size];
        }
    };

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRelationCode() {
        return relationCode;
    }

    public void setRelationCode(String relationCode) {
        this.relationCode = relationCode;
    }

    public String getAlbumType() {
        return albumType;
    }

    public void setAlbumType(String albumType) {
        this.albumType = albumType;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }
}
