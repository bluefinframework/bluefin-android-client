package cn.saymagic.bluefinclient.event;

import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by saymagic on 16/8/31.
 */
public class RxBus {

    public static final String TAG = "RxBus";

    private volatile static RxBus bus;

    public static RxBus getInstance() {
        if (bus == null) {
            synchronized (RxBus.class) {
                if (bus == null) {
                    bus = new RxBus();
                }
            }
        }
        return bus;
    }

    private PublishSubject<Object> mEventBus = PublishSubject.create();

    public <T> Observable<T> toObservable(Class<T> tClass) {
        return mEventBus.ofType(tClass);
    }

    public Observable toObservable() {
        return mEventBus;
    }

    public void post(Object object) {
        mEventBus.onNext(object);
    }

}
