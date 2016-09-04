package cn.saymagic.bluefinclient.ui.splash;

import cn.saymagic.bluefinclient.ui.base.IPresenter;
import cn.saymagic.bluefinclient.ui.base.IView;

/**
 * Created by saymagic on 16/9/4.
 */
public interface SplashContract {

    interface SplashView extends IView<SplashPresenter> {

        void onLoginDone();

        void onLoginFailed();

    }

    interface SplashPresenter extends IPresenter {

    }

}
