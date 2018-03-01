package cn.shareimge.compress.download;

import android.content.Context;
import android.graphics.Bitmap;

/**
 * File description.
 * 分享图片的下载接口
 * @author lihongjun
 * @date 2018/1/12
 */

public interface IShareImageDownLoad {

    /**
     * 图片同步下载
     * @param context 上下文
     * @param url 图片地址
     * @return 返回 bitmap
     * @throws Exception 下载异常
     */
    Bitmap dowwnload(Context context, String url) throws Exception;
}
