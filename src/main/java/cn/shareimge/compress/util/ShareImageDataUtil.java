package cn.shareimge.compress.util;

import android.graphics.Bitmap;

import cn.shareimge.compress.bean.ShareImageData;

/**
 * Created by lihongjun on 2018/1/17.
 */

public class ShareImageDataUtil {

    /**
     * 够着分享imagedata 数据
     * @param url 图片地址
     * @param mediaType  分享内容的类型
     * @param originBitmap 可以直接传入bitmap 后续直接进行压缩不用下载图片
     * @return
     */
    public static ShareImageData create(String url, int mediaType, Bitmap originBitmap) {
        ShareImageData data = new ShareImageData();
        data.setImageUrl(url);
        data.setMediaType(mediaType);
        data.setOriginBitmap(originBitmap);
        return data;
    }
}
