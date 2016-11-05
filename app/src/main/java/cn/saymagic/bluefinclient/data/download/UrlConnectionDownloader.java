package cn.saymagic.bluefinclient.data.download;

import android.text.TextUtils;
import android.util.Log;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import cn.saymagic.bluefinclient.util.Logger;
import cn.saymagic.bluefinsdk.util.IOUtil;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by saymagic on 16/10/26.
 */
public class UrlConnectionDownloader extends BaseDownloader {

    private static final String TAG = "UrlConnectionDownloader";

    @Override
    public Observable<Float> download(final String downloadUrl, final OutputStream outputStream) {
        return Observable.create(new Observable.OnSubscribe<Float>() {
            @Override
            public void call(Subscriber<? super Float> subscriber) {
                if (TextUtils.isEmpty(downloadUrl)) {
                    subscriber.onError(new IllegalStateException());
                    return;
                }
                URL url = null;
                HttpURLConnection connection = null;
                InputStream inputStream = null;
                int length = 0;
                try {
                    url = new URL(downloadUrl);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setConnectTimeout(10000);
                    connection.setReadTimeout(10000);
                    connection.setDoInput(true);
                    connection.setUseCaches(false);
                    connection.setRequestMethod("GET");
                    connection.setRequestProperty("User-Agent", "Bluefin-Spider");
                    connection.setRequestProperty("Connection", "close");
                    connection.setRequestProperty("Http-version", "HTTP/1.1");
                    connection.setRequestProperty("Cache-Control", "no-transform");
                    connection.connect();
                    inputStream = connection.getInputStream();
                    length = connection.getContentLength();
                    int sizeRead = 0;
                    byte[] buffer = new byte[1024];
                    do {
                        int readSize = inputStream.read(buffer);
                        Thread.sleep(10);
                        if (readSize == -1) {
                            break;
                        }
                        outputStream.write(buffer, 0, readSize);
                        sizeRead += readSize;
                        Log.d(TAG, "downloaded percentage: " + ((sizeRead + 0f) / length) + " sizeRead : " + sizeRead + " length: " + length + " readSize: " + readSize);
                        subscriber.onNext(((sizeRead + 0f) / length));
                    } while (true);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    Logger.logException(TAG, e);
                    subscriber.onError(e);
                    return;
                } finally {
                    connection.disconnect();
                    IOUtil.close(inputStream);
                    IOUtil.close(outputStream);
                }
            }
        });
    }
}
