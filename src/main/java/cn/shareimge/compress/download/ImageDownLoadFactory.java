package cn.shareimge.compress.download;

/**
 * Created by lihongjun on 2018/1/16.
 * 图片下载工厂
 */

public class ImageDownLoadFactory {

    public static  <T extends IShareImageDownLoad> T factory(Class<T> clazz) throws Exception {
        IShareImageDownLoad shareImageDownLoad = null;
        Class download = Class.forName(clazz.getName());
        download.getDeclaredConstructor().setAccessible(true);
        shareImageDownLoad = (IShareImageDownLoad) download.newInstance();
        return (T) shareImageDownLoad;
    }
}
