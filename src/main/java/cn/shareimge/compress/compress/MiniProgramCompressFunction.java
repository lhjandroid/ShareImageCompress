package cn.shareimge.compress.compress;

import android.graphics.Bitmap;
import android.util.Log;

import cn.shareimge.compress.bean.IShareBean;
import cn.shareimge.compress.util.BitmapUtil;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * File description.
 * 小程序压缩图片方法
 * @author lihongjun
 * @date 2018/1/30
 * @param <T> 继承至 IShareBean
 */

public class MiniProgramCompressFunction<T extends IShareBean> implements Function<Bitmap,Observable<T>> {

    private static final String TAG = "PosterImageCompress";
    private T mShareBean;

    public MiniProgramCompressFunction(T shareBean) {
        mShareBean = shareBean;
    }

    @Override
    public Observable<T> apply(Bitmap originBitmap) throws Exception {
        Log.e(TAG, "压缩图片");
        BitmapUtil bitmapUtil = new BitmapUtil();
        if (originBitmap != null) {
            byte[] result = bitmapUtil.getImageThumbnail(originBitmap,BitmapUtil.MAX_MINI_THUMBNAIL_DATA_LENGTH);
            mShareBean.setWebPageData(result);
        }
        Log.e(TAG, "压缩图片完成");
        return Observable.just(mShareBean);
    }
}
