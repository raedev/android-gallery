package com.androidx.gallery.utils;

import android.content.Context;

import androidx.annotation.Nullable;

import com.androidx.gallery.internal.GalleryLog;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

/**
 * 图库方法
 * @author RAE
 * @date 2022/01/20
 * Copyright (c) https://github.com/raedev All rights reserved.
 */
public final class GalleryUtils {
    private GalleryUtils() {
    }

    /**
     * 读取配置文件
     */
    @Nullable
    public static String readerConfig(Context context) {
        InputStream stream = null;
        try {
            stream = context.getAssets().open("gallery.json");
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int length = 0;
            byte[] buffer = new byte[512];
            while ((length = stream.read(buffer)) != -1) {
                out.write(buffer, 0, length);
            }
            String text = out.toString();
            closeStream(out);
            closeStream(stream);
            return text;
        } catch (IOException e) {
            GalleryLog.e("读取配置文件异常", e);
        }
        closeStream(stream);
        return null;
    }


    private static void closeStream(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (IOException e) {
            GalleryLog.e("关闭流异常", e);
        }
    }
}
