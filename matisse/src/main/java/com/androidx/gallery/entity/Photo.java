package com.androidx.gallery.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * 照片信息
 * @author RAE
 * @date 2022/01/18
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
@Entity(tableName = "photos")
public class Photo implements Parcelable {

    /**
     * 照片ID
     */
    @PrimaryKey(autoGenerate = true)
    private Long id;

    /**
     * 相册ID
     */
    private Long albumId;

    /**
     * 照片状态
     * <ul>
     *     <li>正常 = 0</li>
     *     <li>删除 = -1</li>
     * </ul>
     */
    @ColumnInfo(defaultValue = "0")
    private Integer state = 0;

    /**
     * 照片名
     */
    private String name;

    /**
     * 照片文件名
     */
    private String fileName;

    /**
     * 文件类型
     * photo: 照片
     * video: 视频
     */
    @ColumnInfo(defaultValue = "photo")
    private String photoType;

    /**
     * 照片保存路径
     */
    private String filePath;

    /**
     * 文件大小
     */
    private Long fileSize;

    /**
     * 照片宽度
     */
    private Long photoWidth;

    /**
     * 照片高度
     */
    private Long photoHeight;

    /**
     * [地理信息] 经度
     */
    private Double longitude;

    /**
     * [地理信息] 纬度
     */
    private Double latitude;

    /**
     * [地理信息] 方位角
     */
    private Double azimuth;

    /**
     * [地理信息] 俯仰角
     */
    private Double pitch;

    /**
     * [地理信息] 侧倾角
     */
    private Double roll;

    /**
     * 视频长度
     */
    private Double videoLength;


    /**
     * 照片的其他信息，一般是JSON格式。
     * <p>如果字段不够可以通过{@link PhotoMeta} 来保存</p>
     */
    private String meta;

    /**
     * 照片创建时间戳
     */
    private Long createdAt;

    public Photo() {
    }

    protected Photo(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        if (in.readByte() == 0) {
            albumId = null;
        } else {
            albumId = in.readLong();
        }
        if (in.readByte() == 0) {
            state = null;
        } else {
            state = in.readInt();
        }
        name = in.readString();
        fileName = in.readString();
        photoType = in.readString();
        filePath = in.readString();
        if (in.readByte() == 0) {
            fileSize = null;
        } else {
            fileSize = in.readLong();
        }
        if (in.readByte() == 0) {
            photoWidth = null;
        } else {
            photoWidth = in.readLong();
        }
        if (in.readByte() == 0) {
            photoHeight = null;
        } else {
            photoHeight = in.readLong();
        }
        if (in.readByte() == 0) {
            longitude = null;
        } else {
            longitude = in.readDouble();
        }
        if (in.readByte() == 0) {
            latitude = null;
        } else {
            latitude = in.readDouble();
        }
        if (in.readByte() == 0) {
            azimuth = null;
        } else {
            azimuth = in.readDouble();
        }
        if (in.readByte() == 0) {
            pitch = null;
        } else {
            pitch = in.readDouble();
        }
        if (in.readByte() == 0) {
            roll = null;
        } else {
            roll = in.readDouble();
        }
        if (in.readByte() == 0) {
            videoLength = null;
        } else {
            videoLength = in.readDouble();
        }
        meta = in.readString();
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
        if (albumId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(albumId);
        }
        if (state == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(state);
        }
        dest.writeString(name);
        dest.writeString(fileName);
        dest.writeString(photoType);
        dest.writeString(filePath);
        if (fileSize == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(fileSize);
        }
        if (photoWidth == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(photoWidth);
        }
        if (photoHeight == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(photoHeight);
        }
        if (longitude == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(longitude);
        }
        if (latitude == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(latitude);
        }
        if (azimuth == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(azimuth);
        }
        if (pitch == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(pitch);
        }
        if (roll == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(roll);
        }
        if (videoLength == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(videoLength);
        }
        dest.writeString(meta);
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

    public static final Creator<Photo> CREATOR = new Creator<Photo>() {
        @Override
        public Photo createFromParcel(Parcel in) {
            return new Photo(in);
        }

        @Override
        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getAzimuth() {
        return azimuth;
    }

    public void setAzimuth(Double azimuth) {
        this.azimuth = azimuth;
    }

    public Double getPitch() {
        return pitch;
    }

    public void setPitch(Double pitch) {
        this.pitch = pitch;
    }

    public Double getRoll() {
        return roll;
    }

    public void setRoll(Double roll) {
        this.roll = roll;
    }

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPhotoType() {
        return photoType;
    }

    public void setPhotoType(String photoType) {
        this.photoType = photoType;
    }


    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public Long getPhotoWidth() {
        return photoWidth;
    }

    public void setPhotoWidth(Long photoWidth) {
        this.photoWidth = photoWidth;
    }

    public Long getPhotoHeight() {
        return photoHeight;
    }

    public void setPhotoHeight(Long photoHeight) {
        this.photoHeight = photoHeight;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Double getVideoLength() {
        return videoLength;
    }

    public void setVideoLength(Double videoLength) {
        this.videoLength = videoLength;
    }

    // region 公开方法

    public boolean isGif() {
        return PhotoType.valueOf(this.photoType) == PhotoType.GIF;
    }

    // endregion
}
