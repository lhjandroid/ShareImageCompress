package cn.shareimge.compress.bean;

import android.graphics.Bitmap;



/**
 * File description.
 *
 * @author lihongjun
 * @date 2018/1/12
 */

public class ShareImageData implements IShareBean {

    /**
     * 图片网络地址
     */
    private String imageUrl;
    /**
     *  图片分享类型
     */
    private int mediaType;
    private Bitmap originBitmap; // 可以直接传入bitmap进行压缩
    private byte[] posterImageData; // 海报主图
    private byte[] posterThumbData; // 海报缩略图
    private byte[] webPageThumbData; // webpage 缩略图


    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String getImageUrl() {
        return imageUrl;
    }

    public void setMediaType(int mediaType) {
        this.mediaType = mediaType;
    }

    @Override
    public int getMediaType() {
        return mediaType;
    }

    public void setOriginBitmap(Bitmap originBitmap) {
        this.originBitmap = originBitmap;
    }

    @Override
    public Bitmap getOriginBitmap() {
        return originBitmap;
    }

    @Override
    public void setPosterImageData(byte[] data) {
        this.posterImageData = data;
    }

    @Override
    public void setPosterThumbData(byte[] data) {
        this.posterThumbData = data;
    }

    @Override
    public void setWebPageData(byte[] data) {
        this.webPageThumbData = data;
    }

    public byte[] getPosterImageData() {
        return posterImageData;
    }

    public byte[] getPosterThumbData() {
        return posterThumbData;
    }

    public byte[] getWebPageThumbData() {
        return webPageThumbData;
    }

}
