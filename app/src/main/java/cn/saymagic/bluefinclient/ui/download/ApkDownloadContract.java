package cn.saymagic.bluefinclient.ui.download;

import cn.saymagic.bluefinclient.ui.base.IPresenter;
import cn.saymagic.bluefinclient.ui.base.IView;

/**
 * Created by saymagic on 16/10/26.
 */
public interface ApkDownloadContract {


    interface IDownloadView extends IView<IDownloadPresenter> {

        void refreshDownloadState(float percentage);

        void onDownloadFinished();

        void onStartDownload();

        void onDownloadError(String errorMsg);
    }

    interface IDownloadPresenter extends IPresenter {

        void installApk();

    }
}


