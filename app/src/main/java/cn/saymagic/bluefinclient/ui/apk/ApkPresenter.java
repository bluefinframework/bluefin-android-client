package cn.saymagic.bluefinclient.ui.apk;

import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;
import java.util.List;

import cn.saymagic.bluefinclient.data.model.Apk;
import cn.saymagic.bluefinclient.data.remote.ServerSessionContract;
import cn.saymagic.bluefinclient.error.BluefinException;
import cn.saymagic.bluefinclient.rx.BackgroundJobTransformer;
import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by saymagic on 16/9/1.
 */
public class ApkPresenter implements ApkContract.IApkPresenter {

    private ServerSessionContract mServerContract;

    private WeakReference<ApkContract.IApkView> mView;

    private CompositeSubscription mCompositeSubscription;

    public ApkPresenter(@NonNull ServerSessionContract contract, @NonNull ApkContract.IApkView view) {
        this.mServerContract = contract;
        this.mView = new WeakReference<ApkContract.IApkView>(view);
        view.setPresenter(this);
        mCompositeSubscription = new CompositeSubscription();
    }

    @Override
    public void subscribe() {
        loadApks();
    }

    @Override
    public void unsubscribe() {
        mCompositeSubscription.clear();
    }

    @Override
    public void loadApks() {
        Subscription subscription = mServerContract.loadAllApks()
                .toList()
                .compose(new BackgroundJobTransformer())
                .subscribe(new ApkLoadSubscriber());
        mCompositeSubscription.add(subscription);
    }

    @Override
    public void signout() {
        mServerContract.saveServerUrl("");
        if (mView.get() != null) {
            mView.get().finishAndSwitchToLogin();
        }
    }

    class ApkLoadSubscriber extends Subscriber<List<Apk>> {

        @Override
        public void onStart() {
            super.onStart();
            if (mView.get() != null) {
                mView.get().startLoding();
            }
        }

        @Override
        public void onCompleted() {
            if (mView.get() != null) {
                mView.get().stopLoading();
            }
        }

        @Override
        public void onError(Throwable e) {
            if (mView.get() != null) {
                mView.get().showErrorTip(BluefinException.getErrorTipByErrorType(e));
                mView.get().stopLoading();
            }
        }

        @Override
        public void onNext(List<Apk> apks) {
            if (mView.get() != null) {
                mView.get().onApkDataLoaded(apks);
            }
        }
    }
}
