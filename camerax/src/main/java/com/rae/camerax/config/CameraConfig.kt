package com.rae.camerax.config

import android.os.Parcelable

/**
 * 相机配置文件
 * @author RAE
 * @date 2022/06/14
 * @copyright Copyright (c) https://github.com/raedev All rights reserved.
 */
@kotlinx.parcelize.Parcelize
data class CameraConfig(
    var ratio: Int, // 相机比例
    var ext: String, // 扩展配置，一般为JSON
) : Parcelable