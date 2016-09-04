package cn.saymagic.bluefinclient.ui.login;

import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;

import cn.saymagic.bluefinclient.data.ServerSession;
import cn.saymagic.bluefinclient.error.BluefinException;
import cn.saymagic.bluefinsdk.entity.PingResult;
import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by saymagic on 16/9/2.
 */
public class LoginPresenter implements LoginContract.ILoginPresenter {

    private WeakReference<LoginContract.ILoginView> mLoginView;

    private CompositeSubscription mCompositeSubscription;

    private ServerSession mServerSession;

    public LoginPresenter(@NonNull LoginContract.ILoginView loginView, @NonNull ServerSession serverSession) {
        this.mLoginView = new WeakReference<LoginContract.ILoginView>(loginView);
        this.mServerSession = serverSession;
        this.mCompositeSubscription = new CompositeSubscription();
        loginView.setPresenter(this);
    }

    @Override
    public void doLogin(String serverUrl) {
        Subscription subscription = mServerSession
                .login(serverUrl).subscribe(new LoginSubscriber());
        mCompositeSubscription.add(subscription);
    }

    @Override
    public void subscribe() {
        LoginContract.ILoginView view = mLoginView.get();
        if (view != null) {
            view.initServerUrl(mServerSession.getServerUrl());
        }
    }

    @Override
    public void unsubscribe() {
        mCompositeSubscription.clear();
    }

    class LoginSubscriber extends Subscriber<PingResult> {

        @Override
        public void onStart() {
            super.onStart();
            LoginContract.ILoginView loginView = mLoginView.get();
            if (loginView != null) {
                loginView.startLoding();
            }
        }

        @Override
        public void onCompleted() {
            LoginContract.ILoginView loginView = mLoginView.get();
            if (loginView != null) {
                loginView.stopLoading();
                loginView.onLoginDone();
            }
        }

        @Override
        public void onError(Throwable e) {
            LoginContract.ILoginView loginView = mLoginView.get();
            if (loginView != null) {
                loginView.stopLoadingWithErrorTip(BluefinException.getErrorTipByErrorType(e));
            }
        }

        @Override
        public void onNext(PingResult pingResult) {

        }

    }
}
