package cn.shareimge.compress.bean;

import android.graphics.Bitmap;

/**
 * File description.
 *
 * @author lihongjun
 * @date 2018/1/12
 */

public interface IShareBean {

    /**
     * 分享内容类型
     *
     * @return 内容类型
     */
    int getMediaType();

    /**
     * 海报或者webpage 图片地址
     *
     * @return String
     */
    String getImageUrl();

    /**
     * 获取主图 如果有此图 直接进行压缩 不再进行下载
     *
     * @return Bitmap
     */
    Bitmap getOriginBitmap();


    /**
     * 设置海报数据
     *
     * @param data byte[]
     */
    void setPosterImageData(byte[] data);

    /**
     * 设置海报缩略图
     *
     * @param data byte[]
     */
    void setPosterThumbData(byte[] data);

    /**
     * 设置webpage模式
     *
     * @param data byte[]
     */
    void setWebPageData(byte[] data);

}
