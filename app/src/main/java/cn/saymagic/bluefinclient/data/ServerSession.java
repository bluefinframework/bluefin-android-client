package cn.saymagic.bluefinclient.data;

import android.text.TextUtils;

import cn.saymagic.bluefinclient.BluefinApplication;
import cn.saymagic.bluefinclient.data.cache.CacheContract;
import cn.saymagic.bluefinclient.error.Login.NoServerUrlException;
import cn.saymagic.bluefinclient.rx.BackgroundJobTransformer;
import cn.saymagic.bluefinclient.rx.NetworkStateTransformer;
import cn.saymagic.bluefinrxsdk.BluefinRxAdapter;
import cn.saymagic.bluefinsdk.Bluefin;
import cn.saymagic.bluefinsdk.entity.PingResult;
import rx.Observable;
import rx.functions.Action1;

/**
 * Created by saymagic on 16/9/2.
 */
public class ServerSession {

    private static final String TAG = "ServerSession";

    private static volatile ServerSession INSTANCE;

    private static final String SERVER_URL_CACHE_KEY = "SERVER_URL_CACHE_KEY";


    public static ServerSession getInstance() {
        if (INSTANCE == null) {
            synchronized (ServerSession.class) {
                INSTANCE = new ServerSession();
            }
        }
        return INSTANCE;
    }

    public Observable<PingResult> login(final String serverUrl) {
        Bluefin.init(BluefinApplication.getContext(), serverUrl);
        return BluefinRxAdapter.ping()
                .compose(new BackgroundJobTransformer<PingResult>())
                .compose(new NetworkStateTransformer<PingResult>())
                .doOnNext(new Action1<PingResult>() {
                    @Override
                    public void call(PingResult pingResult) {
                        setServerUrl(serverUrl);
                    }
                });
    }

    public void setServerUrl(String serverUrl) {
        CacheContract.INSTANCE.save(SERVER_URL_CACHE_KEY, serverUrl);
    }

    public String getServerUrl() {
        return CacheContract.INSTANCE.get(SERVER_URL_CACHE_KEY);
    }

    public Observable<PingResult> loginLast() {
        String url;
        if (TextUtils.isEmpty((url = getServerUrl()))) {
            return Observable.error(new NoServerUrlException());
        }
        return login(url);
    }

}
