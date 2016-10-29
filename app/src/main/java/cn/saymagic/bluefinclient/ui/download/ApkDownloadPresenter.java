package cn.saymagic.bluefinclient.ui.download;

import android.content.Context;
import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.lang.ref.WeakReference;

import cn.saymagic.bluefinclient.data.download.DownloadContract;
import cn.saymagic.bluefinclient.data.model.to.ApkTransmission;
import cn.saymagic.bluefinclient.rx.BackgroundJobTransformer;
import cn.saymagic.bluefinclient.ui.UIController;
import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by saymagic on 16/10/26.
 */
public class ApkDownloadPresenter implements ApkDownloadContract.IDownloadPresenter {

    private WeakReference<ApkDownloadContract.IDownloadView> mDownloadView;

    private ApkTransmission mApkTransmission;

    private Context mContext;

    private DownloadContract mDownloader;

    private CompositeSubscription mCompositeSubscription;
    File f;

    public ApkDownloadPresenter(ApkDownloadContract.IDownloadView mDownloadView, ApkTransmission mApkTransmission, Context mContext, DownloadContract downloadContract) {
        this.mDownloadView = new WeakReference<ApkDownloadContract.IDownloadView>(mDownloadView);
        this.mApkTransmission = mApkTransmission;
        this.mContext = mContext;
        this.mDownloader = downloadContract;
        this.mCompositeSubscription = new CompositeSubscription();
    }

    @Override
    public void subscribe() {
        if (mDownloadView.get() != null) {
            mDownloadView.get().setPresenter(this);
        }
        File file = new File(Environment.getExternalStorageDirectory()
                .getAbsolutePath());
        f = new File(file, mApkTransmission.getName() + ".apk");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(f);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            Subscription subscription = mDownloader
                    .download(mApkTransmission.apkUrl, bos)
                    .compose(new BackgroundJobTransformer())
                    .subscribe(new DownloadSubscriber());
            mCompositeSubscription.add(subscription);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void unsubscribe() {
        mCompositeSubscription.unsubscribe();
    }

    @Override
    public void installApk() {
        UIController.showToast(mContext, "install!");
    }

    class DownloadSubscriber extends Subscriber<Float> {

        @Override
        public void onStart() {
            if (mDownloadView.get() != null) {
                mDownloadView.get().onStartDownload();
            }
        }

        @Override
        public void onCompleted() {
            if (mDownloadView.get() != null) {
                mDownloadView.get().onDownloadFinished();
            }
        }

        @Override
        public void onError(Throwable e) {
            if (mDownloadView.get() != null) {
                mDownloadView.get().onDownloadError("");
            }
        }

        @Override
        public void onNext(Float aFloat) {
            if (mDownloadView.get() != null) {
                mDownloadView.get().refreshDownloadState(aFloat);
            }
        }
    }
}
