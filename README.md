# ShareImageCompress
分享到微信微博或者QQ时图片压缩并生成缩略图

针对微信分享时需要压缩图片到较小的图片尺寸 并且需要设置缩略32k的限制.
针对业务场景新增图片压缩前的拼接贴二维码或者一些其他操作.

1.分享图片模式
```java
ShareImageData shareImageData = ShareImageDataUtil.create("http://pic35.photophoto.cn/20150522/0034034855249416_b.jpg"
                        , MediaType.MEDIA_TYPE_IMG,null);
                new ContentManager<ShareImageData>(MainActivity.this).getShareContent(shareImageData, new Observer<ShareImageData>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ShareImageData shareImageData) {
                        Log.e("shareimage","size:" + shareImageData.getPosterImageData().length);
                        // shareImageData 里包含主图byte[]数据(shareImageData.getPosterImageData()) 和缩略图byte[](shareImageData.getPosterThumbData())
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        Log.e("shareimage","complete");
                    }
                },new GlideImageDownload());
```
2.分享链接模式
```java
ShareImageData shareImageData = ShareImageDataUtil.create("http://pic35.photophoto.cn/20150522/0034034855249416_b.jpg"
                        , MediaType.MEDIA_TYPE_WEB,null);
                new ContentManager<ShareImageData>(MainActivity.this).getShareContent(shareImageData, new Observer<ShareImageData>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ShareImageData shareImageData) {
                        Log.e("shareimage","size:" + shareImageData.getPosterImageData().length);
                        // 包含缩略图byte[] (shareImageData.getWebPageThumbData())
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        Log.e("shareimage","complete");
                    }
                },new GlideImageDownload());
```
3.微信小程序的图片压缩
```java
ShareImageData shareImageData = ShareImageDataUtil.create("http://pic35.photophoto.cn/20150522/0034034855249416_b.jpg"
                        , MediaType.MEDIA_TYPE_MINI_APP,null);
                new ContentManager<ShareImageData>(MainActivity.this).getShareContent(shareImageData, new Observer<ShareImageData>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ShareImageData shareImageData) {
                        Log.e("shareimage","size:" + shareImageData.getPosterImageData().length);
                        // 包含小程序图片数据 byte[] (shareImageData.getWebPageThumbData())
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        Log.e("shareimage","complete");
                    }
                },new GlideImageDownload());
```


4.图片附加操作
```java

private Observable<Bitmap> dowloadExtraObservable = Observable.create(emitter -> {
        Bitmap bitmap = Glide.with(getApplicationContext())
                .asBitmap()
                .load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1515476758278&di=1a65b298738b7467faff309b7df72fb3&imgtype=0&src=http%3A%2F%2Fscimg.jb51.net%2Fallimg%2F161207%2F102-16120H243090-L.jpg")
                .submit(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .get();
        Log.e(TAG, "onNext");
        emitter.onNext(bitmap);
        emitter.onComplete();
    });

IShareImageDownLoad shareImageDownLoad = ImageDownLoadFactory.factory(GlideImageDownload.class);
                new ContentManager<ShareImageData>(this).getShareContent(ShareImageDataUtil.create("http://f8.topitme.com/8/25/80/1125177570eea80258o.jpg",MediaType.MEDIA_TYPE_IMG,null), new Observer<ShareImageData>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ShareImageData shareImageData) {
                        Log.e(TAG,"mediatype:" + shareImageData.getMediaType() + "  postersize:" + shareImageData.getPosterImageData().length);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError");
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "onComplete");
                    }
                },shareImageDownLoad, dowloadExtraObservable, ((posterBitmap, bitmap2) -> {
                    Bitmap finalBitmap = Bitmap.createBitmap(posterBitmap.getWidth(), posterBitmap.getHeight(), Bitmap.Config.ARGB_8888);
                    Canvas canvas = new Canvas(finalBitmap);
                    canvas.drawBitmap(posterBitmap, 0, 0, null);
                    canvas.drawBitmap(bitmap2, 0, 0, null);
                    return finalBitmap;
                }));
```
