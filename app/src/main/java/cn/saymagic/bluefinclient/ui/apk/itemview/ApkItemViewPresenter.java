package cn.saymagic.bluefinclient.ui.apk.itemview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import java.lang.ref.WeakReference;

import cn.saymagic.bluefinclient.R;
import cn.saymagic.bluefinclient.data.model.Apk;
import cn.saymagic.bluefinclient.ui.UIController;

/**
 * Created by saymagic on 16/10/27.
 */
public class ApkItemViewPresenter implements ApkItemViewContract.IApkItemViewPresenter {

    private WeakReference<ApkItemViewContract.IApkItemView> mView;

    private Apk mApk;

    private Context mContext;

    public ApkItemViewPresenter(@NonNull ApkItemViewContract.IApkItemView view, Context context, @NonNull Apk apk) {
        this.mView = new WeakReference<ApkItemViewContract.IApkItemView>(view);
        this.mContext = context;
        this.mApk = apk;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.apk_item_containter:{
                if (mApk.installed) {
                    if (mApk.launchIntent != null) {
                        UIController.openActivityByIntent(mContext, mApk.launchIntent);
                    } else {
                        UIController.showToast(mContext, mContext.getString(R.string.error_no_intent));
                    }
                } else {
                    UIController.openDownloadActivity(mContext, mApk);
                }
                break;
            }
            case R.id.apk_more_version:{
                UIController.showToast(mContext, "更多版本");
            }
        }

    }

    @Override
    public void subscribe() {
        if (mView.get() != null) {
            mView.get().setPresenter(this);
        }
    }

    @Override
    public void unsubscribe() {

    }
}
