package cn.shareimge.compress.download;

import android.content.Context;
import android.graphics.Bitmap;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

/**
 * File description.
 * 默认的gelide图片下载方式
 * @author lihongjun
 * @date 2018/1/12
 */

public class GlideImageDownload implements IShareImageDownLoad {

    @Override
    public Bitmap dowwnload(Context context, String url) throws Exception {
        return Glide.with(context)
                .asBitmap()
                .load(url)
                .submit(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .get();
    }
}
