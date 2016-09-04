package cn.saymagic.bluefinclient.ui.splash;

import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;

import cn.saymagic.bluefinclient.data.ServerSession;
import cn.saymagic.bluefinsdk.entity.PingResult;
import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by saymagic on 16/9/4.
 */
public class SplashPresenter implements SplashContract.SplashPresenter {

    private WeakReference<SplashContract.SplashView> mView;

    private CompositeSubscription mCompositeSubscription;

    private ServerSession mServerSession;

    public SplashPresenter(@NonNull SplashContract.SplashView view, @NonNull ServerSession serverSession) {
        mServerSession = serverSession;
        mView = new WeakReference<SplashContract.SplashView>(view);
        mCompositeSubscription = new CompositeSubscription();
        view.setPresenter(this);
    }

    @Override
    public void subscribe() {
        Subscription subscription = mServerSession.loginLast().subscribe(new SplashSubscriber());
        mCompositeSubscription.add(subscription);
    }

    @Override
    public void unsubscribe() {
        mCompositeSubscription.clear();
    }

    private class SplashSubscriber extends Subscriber<PingResult> {

        @Override
        public void onCompleted() {
            SplashContract.SplashView view = mView.get();
            if (view != null) {
                view.onLoginDone();
            }
        }

        @Override
        public void onError(Throwable e) {
            SplashContract.SplashView view = mView.get();
            if (view != null) {
                view.onLoginFailed();
            }
        }

        @Override
        public void onNext(PingResult pingResult) {

        }
    }
}
