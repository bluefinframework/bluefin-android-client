package cn.saymagic.bluefinclient.ui.apk;

import java.util.List;

import cn.saymagic.bluefinclient.data.model.Apk;
import cn.saymagic.bluefinclient.ui.base.ILoadingView;
import cn.saymagic.bluefinclient.ui.base.IPresenter;

/**
 * Created by saymagic on 16/9/1.
 */
public interface ApkContract {

    public interface IApkView extends ILoadingView<IApkPresenter> {

        void onApkDataLoaded(List<Apk> apks);

        void showErrorTip(int tipId);

    }

    public interface IApkPresenter extends IPresenter {

        void loadApks();

    }
}
