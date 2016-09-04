package cn.saymagic.bluefinclient.ui.login;

import cn.saymagic.bluefinclient.ui.base.ILoadingView;
import cn.saymagic.bluefinclient.ui.base.IPresenter;

/**
 * Created by saymagic on 16/9/2.
 */
public interface LoginContract {

    interface ILoginView extends ILoadingView<ILoginPresenter>{
        void onLoginDone();

        void stopLoadingWithErrorTip(int strId);

        void initServerUrl(String serverUrl);
    }

    interface ILoginPresenter extends IPresenter{

        void doLogin(String serverUrl);

    }
}
