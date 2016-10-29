package cn.saymagic.bluefinclient.ui.apk.itemview;

import android.view.View;

import cn.saymagic.bluefinclient.ui.base.IPresenter;
import cn.saymagic.bluefinclient.ui.base.IView;

/**
 * Created by saymagic on 16/10/27.
 */
public interface ApkItemViewContract {

    interface IApkItemViewPresenter extends IPresenter{

        void onClick(View view);

    }

    interface IApkItemView extends IView<IApkItemViewPresenter> {
    }

}
