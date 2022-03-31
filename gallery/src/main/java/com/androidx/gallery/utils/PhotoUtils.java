package com.androidx.gallery.utils;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import com.androidx.gallery.R;

import java.io.File;

/**
 * 照片常用工具方法
 * @author RAE
 * @date 2022/01/18
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public final class PhotoUtils {

    /**
     * 校验文件是否存在
     * @param path 路径
     * @return 是否存在
     */
    public static boolean exist(String path) {
        if (TextUtils.isEmpty(path)) {
            return false;
        }
        return new File(path).exists();
    }

    /**
     * 计算文件大小
     * @param filePath 文件路径
     */
    public static Long calcFileSize(String filePath) {
        if (!exist(filePath)) {
            return 0L;
        }
        return new File(filePath).length();
    }

    /**
     * 格式化更加友好的提示路径
     * @param filePath 文件路径
     * @return 路径
     */
    public static String formatFilePath(Context context, String filePath) {
        filePath = filePath.replace(Environment.getExternalStorageDirectory().getPath(), context.getString(R.string.gallery_external_storage_name));
        return filePath;
    }

    /**
     * 删除文件
     * @param filePath 文件路径
     */
    public static boolean delete(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return false;
        }
        File file = new File(filePath);
        if (file.exists()) {
            return file.delete();
        }
        return false;
    }
}
