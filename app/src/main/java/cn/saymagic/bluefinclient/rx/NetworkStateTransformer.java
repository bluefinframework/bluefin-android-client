package cn.saymagic.bluefinclient.rx;

import cn.saymagic.bluefinclient.BluefinApplication;
import cn.saymagic.bluefinclient.error.common.NetworkDisabledException;
import cn.saymagic.bluefinclient.util.NetworkUtil;
import rx.Observable;

/**
 * Created by saymagic on 16/9/4.
 */
public class NetworkStateTransformer<T> implements Observable.Transformer<T, T> {

    @Override
    public Observable<T> call(Observable<T> observable) {
        if (NetworkUtil.isConnected(BluefinApplication.getContext())) {
            return observable;
        }
        return Observable.error(new NetworkDisabledException());
    }
}
