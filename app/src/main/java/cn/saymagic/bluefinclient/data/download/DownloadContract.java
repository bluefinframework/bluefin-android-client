package cn.saymagic.bluefinclient.data.download;

import java.io.OutputStream;

import rx.Observable;

/**
 * Created by saymagic on 16/10/26.
 */
public interface DownloadContract {

    Observable<Float> download(String url, OutputStream outputStream);

    DownloadContract URL_DOWNLOADER = new UrlConnectionDownloader();

}
