package cn.shareimge.compress.download;

import android.content.Context;
import android.graphics.Bitmap;
import cn.shareimge.compress.bean.IShareBean;
import io.reactivex.Observable;
import io.reactivex.Observer;

/**
 * File description.
 * 下载分享的图片数据
 * @author lihongjun
 * @date 2018/1/12
 * @param <T> 继承至 IShareBean
 */

public class ShareImageDownLoadObersevable<T extends IShareBean> extends Observable {

    /**
     * 资源 上下文
     */
    private Context mContext;
    /**
     * 分享图片数据
     */
    private T mShareBean;
    /**
     * 下载方式
     */
    private IShareImageDownLoad mShareImageDownLoad;

    /**
     * 够着方法
     * @param context
     * @param shareBean
     * @param shareImageDownLoad
     */
    public ShareImageDownLoadObersevable(Context context, T shareBean, IShareImageDownLoad shareImageDownLoad) {
        mContext = context;
        mShareBean = shareBean;
        mShareImageDownLoad = shareImageDownLoad;
    }

    @Override
    protected void subscribeActual(Observer observer) {
        try {
            Bitmap bitmap = mShareBean.getOriginBitmap();
            // 如果没有传递图片进来就下载图片 否则直接压缩传递进来的图片
            if (bitmap == null) {
                bitmap = mShareImageDownLoad.dowwnload(mContext,mShareBean.getImageUrl());
            }
            observer.onNext(bitmap);
            observer.onComplete();
        } catch (Exception e) {
            e.printStackTrace();
            observer.onError(e);
        }
    }
}
