package cn.shareimge.compress;

import android.content.Context;
import android.graphics.Bitmap;

import cn.shareimge.compress.bean.IShareBean;
import cn.shareimge.compress.compress.ImageCompressFunction;
import cn.shareimge.compress.download.IShareImageDownLoad;
import cn.shareimge.compress.download.ShareImageDownLoadObersevable;
import cn.shareimge.compress.util.RxSchedulersHelper;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.functions.BiFunction;

/**
 * File description.
 * 分享图片内容准备
 *
 * @param <T> 继承至 ISahreBean
 * @author lihongjun
 * @date 2018/1/12
 */

public class ContentManager<T extends IShareBean> {

    /**
     * 资源
     */
    private Context mContext;

    /**
     * 构造方法
     * @param context 资源
     */
    public ContentManager(Context context) {
        mContext = context;
    }

    /**
     * 获取分享结构图
     *
     * @param shareBean          需要加工的数据
     * @param result             结果通知
     * @param shareImageDownLoad 图片下载方式
     */
    public void getShareContent(T shareBean, Observer<T> result, IShareImageDownLoad shareImageDownLoad) {
        getShareContent(shareBean, result, shareImageDownLoad, null, null);
    }

    /**
     * 获取分享结构图
     *
     * @param shareBean               需要加工的数据
     * @param result                  结果通知
     * @param shareImageDownLoad      图片下载方式
     * @param extralBitmapObservable  额外的bitmap
     * @param bitmapOpreatorFuncation 两个bitmap的操作 如拼接 或者贴图
     */
    public void getShareContent(T shareBean, Observer<T> result, IShareImageDownLoad shareImageDownLoad, Observable extralBitmapObservable,
                                BiFunction<Bitmap, Bitmap, Bitmap> bitmapOpreatorFuncation) {
        if (shareBean == null || result == null || shareImageDownLoad == null) {
            return;
        }
        if (extralBitmapObservable == null || bitmapOpreatorFuncation == null) {
            new ShareImageDownLoadObersevable(mContext, shareBean, shareImageDownLoad)
                    .flatMap(ImageCompressFunction.getCompressFunction(shareBean))
                    .compose(RxSchedulersHelper.io_main())
                    .subscribe(result);
        } else {
            Observable.zip(new ShareImageDownLoadObersevable(mContext, shareBean, shareImageDownLoad),
                    extralBitmapObservable, bitmapOpreatorFuncation)
                    .flatMap(ImageCompressFunction.getCompressFunction(shareBean))
                    .compose(RxSchedulersHelper.io_main())
                    .subscribe(result);
        }
    }
}
