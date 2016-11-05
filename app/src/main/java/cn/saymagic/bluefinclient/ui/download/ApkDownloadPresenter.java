package cn.saymagic.bluefinclient.ui.download;

import android.content.Context;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;

import cn.saymagic.bluefinclient.data.download.DownloadPerformContract;
import cn.saymagic.bluefinclient.data.download.DownloadSaveContract;
import cn.saymagic.bluefinclient.data.model.Apk;
import cn.saymagic.bluefinclient.rx.BackgroundJobTransformer;
import cn.saymagic.bluefinclient.ui.UIController;
import cn.saymagic.bluefinclient.util.EncryUtil;
import cn.saymagic.bluefinclient.util.Logger;
import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by saymagic on 16/10/26.
 */
public class ApkDownloadPresenter implements ApkDownloadContract.IDownloadPresenter {

    private static final String TAG = "ApkDownloadPresenter";

    private WeakReference<ApkDownloadContract.IDownloadView> mDownloadView;

    private Apk mApk;

    private Context mContext;

    private DownloadPerformContract mDownloader;

    private DownloadSaveContract mSaver;

    private CompositeSubscription mCompositeSubscription;

    File mDownloadingFile;

    public ApkDownloadPresenter(ApkDownloadContract.IDownloadView mDownloadView,
                                Apk mApk,
                                Context mContext,
                                DownloadPerformContract downloadContract,
                                DownloadSaveContract downloadSaveContract) {
        this.mDownloadView = new WeakReference<ApkDownloadContract.IDownloadView>(mDownloadView);
        this.mApk = mApk;
        this.mContext = mContext;
        this.mDownloader = downloadContract;
        this.mSaver = downloadSaveContract;
        this.mCompositeSubscription = new CompositeSubscription();
    }

    @Override
    public void subscribe() {
        if (mDownloadView.get() != null) {
            mDownloadView.get().setPresenter(this);
        }
        File file = mContext.getCacheDir();
        try {
            mDownloadingFile = mSaver.getSaveFile(mApk.apkUrl, "apk");
            if (mDownloadingFile.exists()) {
                if (EncryUtil.getMD5(mDownloadingFile).equals(mApk.md5)) {
                    if (mDownloadView.get() != null) {
                        mDownloadView.get().doFinish();
                    }
                    installApk();
                    return;
                } else {
                    mDownloadingFile.delete();
                }
            }
            FileOutputStream fos = new FileOutputStream(mDownloadingFile);
            Subscription subscription = mDownloader
                    .download(mApk.apkUrl, fos)
                    .compose(new BackgroundJobTransformer())
                    .subscribe(new DownloadSubscriber());
            mCompositeSubscription.add(subscription);
        } catch (FileNotFoundException e) {
            Logger.logException(TAG, e);
        } catch (IOException e) {
            Logger.logException(TAG, e);
        }
    }

    @Override
    public void unsubscribe() {
        mCompositeSubscription.unsubscribe();
    }

    @Override
    public void installApk() {
        if (mDownloadingFile != null) {
            UIController.openInstallActivity(mContext, mDownloadingFile.getPath());
        }
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
