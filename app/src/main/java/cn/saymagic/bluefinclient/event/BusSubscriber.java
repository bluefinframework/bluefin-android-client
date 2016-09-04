package cn.saymagic.bluefinclient.event;

import android.util.Log;

import rx.Subscriber;

/**
 * Created by saymagic on 16/8/31.
 */
public abstract class BusSubscriber<T> extends Subscriber<T> {

    private static final String TAG = "BusSubscriber";

    @Override
    public void onCompleted() {
        Log.d("BusSubscriber", "onCompleted");
    }

    @Override
    public void onError(Throwable e) {
        Log.e("BusSubscriber", "onError");
    }

    @Override
    public abstract void onNext(T o);

}