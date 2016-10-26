package cn.saymagic.bluefinclient.data.remote;

import android.text.TextUtils;

import java.util.List;

import cn.saymagic.bluefinclient.BluefinApplication;
import cn.saymagic.bluefinclient.data.cache.CacheContract;
import cn.saymagic.bluefinclient.data.model.Apk;
import cn.saymagic.bluefinclient.error.Login.NoServerUrlException;
import cn.saymagic.bluefinclient.rx.BackgroundJobTransformer;
import cn.saymagic.bluefinclient.rx.NetworkStateTransformer;
import cn.saymagic.bluefinrxsdk.BluefinRxAdapter;
import cn.saymagic.bluefinsdk.Bluefin;
import cn.saymagic.bluefinsdk.entity.BluefinApkData;
import cn.saymagic.bluefinsdk.entity.PingResult;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by saymagic on 16/9/2.
 */
public class ServerSession implements ServerSessionContract {

    private static final String TAG = "ServerSession";

    private static volatile ServerSession INSTANCE;

    private static final String SERVER_URL_CACHE_KEY = "SERVER_URL_CACHE_KEY";


    protected static ServerSession getInstance() {
        if (INSTANCE == null) {
            synchronized (ServerSession.class) {
                INSTANCE = new ServerSession();
            }
        }
        return INSTANCE;
    }

    private ServerSession() {

    }

    @Override
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

    @Override
    public void setServerUrl(String serverUrl) {
        CacheContract.INSTANCE.save(SERVER_URL_CACHE_KEY, serverUrl);
    }

    @Override
    public String getServerUrl() {
        return CacheContract.INSTANCE.get(SERVER_URL_CACHE_KEY);
    }

    @Override
    public Observable<Apk> loadAllApks() {
        return BluefinRxAdapter.listAllApks().compose(new NetworkStateTransformer<List<BluefinApkData>>()).concatMap(new Func1<List<BluefinApkData>, Observable<? extends BluefinApkData>>() {
            @Override
            public Observable<BluefinApkData> call(List<BluefinApkData> bluefinApkDatas) {
                return Observable.from(bluefinApkDatas);
            }
        }).map(new Func1<BluefinApkData, Apk>() {
            @Override
            public Apk call(BluefinApkData data) {
                return Apk.from(data);
            }
        });
    }

    @Override
    public Observable<PingResult> loginLast() {
        String url;
        if (TextUtils.isEmpty((url = getServerUrl()))) {
            return Observable.error(new NoServerUrlException());
        }
        return login(url);
    }

}
