package cn.saymagic.bluefinclient.ui.base;

/**
 * Created by saymagic on 16/9/1.
 */
public interface ILoadingView<T> extends IView<T> {

    void startLoding();

    void stopLoading();

}
