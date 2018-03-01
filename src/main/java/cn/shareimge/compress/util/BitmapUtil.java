package cn.shareimge.compress.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * File description.
 * 分享图片bitmap工具类
 * @author lihongjun
 * @date 2018/1/12
 */

public class BitmapUtil {

    // 分享的图片最大数据
    public static final int MAX_IMAGE_DATA_LENGTH = 500 * 1024;
    // 分享图片的最大缩放数据大小
    public static final int MAX_THUMBNAIL_DATA_LENGTH = 32 * 1024;
    // 分享小程序的缩略图大小
    public static final int MAX_MINI_THUMBNAIL_DATA_LENGTH = 128 * 1024;

    // 是否手动停止了图片的压缩处理
    private boolean isCompressStop;

    public void setCompressStop(boolean compressStop) {
        isCompressStop = compressStop;
    }

    /**
     * 对缩放过的图片进行质量压缩,并检验是否符合要求,如果不符合在进行最后的尺寸压缩
     *
     * @param bitmap
     * @param maxLength
     * @return
     */
    public byte[] getScaledImageBytes(Bitmap bitmap, int maxLength) {
        isCompressStop = false;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int quality = 98;
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
        while (outputStream.toByteArray().length > maxLength && !isCompressStop) {
            if (quality < 20) {
                break;
            }
            outputStream.reset();
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            quality -= 2;
        }
        if (outputStream.toByteArray().length > maxLength && !isCompressStop) {
            return getImageDataByScale(outputStream, maxLength);
        }
        return outputStream.toByteArray();
    }


    /**
     * 缩放压缩
     *
     * @param outStream 输出流
     * @param maxLength 压缩的最大byte size
     * @return
     */
    private byte[] getImageDataByScale(ByteArrayOutputStream outStream, int maxLength) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(outStream.toByteArray(), 0, outStream.toByteArray().length);
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        int scale = Math.max(w, h);
        int step = 0;
        while (bitmap.getByteCount() > maxLength && !isCompressStop) {
            int rate = scale - (step += 20) / scale;
            int nw = w * rate;
            int nh = h * rate;
            Bitmap.createScaledBitmap(bitmap, nw, nh, true);
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        return outputStream.toByteArray();
    }

    /**
     * 获取缩略图
     *
     * @param bitmap
     * @return 缩略图数组
     */
    public byte[] getImageThumbnail(Bitmap bitmap) {
        return getImageThumbnail(bitmap,MAX_THUMBNAIL_DATA_LENGTH);
    }

    /**
     * 缩略图数组
     * @param bitmap 传入的bitmap
     * @param maxLength 压缩到多少
     * @return byte 数组
     */
    public byte[] getImageThumbnail(Bitmap bitmap,int maxLength) {
        if (bitmap == null) {
            return null;
        }
        double scale = getThumbScale(bitmap.getWidth(), bitmap.getHeight(), maxLength);
        return getImageThumbnail(bitmap, (int) (bitmap.getWidth() * scale), (int) (bitmap.getHeight() * scale));
    }

    /**
     * 获取压缩到指定大小时图片的缩放比例
     *
     * @param srcWidth
     * @param srcHeight
     * @param maxSize
     * @return
     */
    private double getThumbScale(int srcWidth, int srcHeight, int maxSize) {
        return Math.sqrt((float) maxSize / (float) (4 * srcWidth * srcHeight));
    }


    /**
     * 获取缩略图
     *
     * @param bitmap 文件路径
     * @param width 缩略图宽度
     * @param height 缩略图高度
     * @return 图像byte数组
     */
    private byte[] getImageThumbnail(Bitmap bitmap, int width, int height) {
        byte[] result = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        //获取图片高度
        int h = bitmap.getWidth();
        int w = bitmap.getHeight();
        int scaleWidth = w / width;
        int scaleHeight = h / height;
        int scale = 1;
        if (scaleWidth < scaleHeight) {
            scale = scaleWidth;
        } else {
            scale = scaleHeight;
        }
        //判断缩放比是否符合条件
        if (scale <= 0) {
            scale = 1;
        }
        options.inSampleSize = LogarithmUtil.getLargePowerSize(scale, 2);
        // 重新读入图片，读取缩放后的bitmap，注意这次要把inJustDecodeBounds 设为 false
        options.inJustDecodeBounds = false;
        try {
            byte[] data = bitmap2Byte(bitmap);
            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, options);
            result = bitmap2Byte(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * bitmap 转byte数组
     * @param bitmap
     * @return
     * @throws IOException
     */
    private byte[] bitmap2Byte(Bitmap bitmap) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        byte[] data = bos.toByteArray();
        bos.close();
        return data;
    }
}
