package cn.saymagic.bluefinclient.data.download;

import java.io.OutputStream;

import rx.Observable;

/**
 * Created by saymagic on 16/10/26.
 */
public abstract class BaseDownloader implements DownloadPerformContract {

    @Override
    public abstract Observable<Float> download(String url, OutputStream outputStream);

}
