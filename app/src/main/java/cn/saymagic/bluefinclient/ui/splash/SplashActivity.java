package cn.saymagic.bluefinclient.ui.splash;

import android.os.Bundle;

import cn.saymagic.bluefinclient.R;
import cn.saymagic.bluefinclient.data.remote.ServerSessionContract;
import cn.saymagic.bluefinclient.ui.BaseActivity;
import cn.saymagic.bluefinclient.ui.UIController;

public class SplashActivity extends BaseActivity implements SplashContract.SplashView {

    private SplashContract.SplashPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        new SplashPresenter(this, ServerSessionContract.INSTANCE).subscribe();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unsubscribe();
    }

    @Override
    public void onLoginDone() {
        UIController.openApkActivity(this);
        finish();
    }

    @Override
    public void onLoginFailed() {
        UIController.openLoginActivity(this);
        finish();
    }

    @Override
    public void setPresenter(SplashContract.SplashPresenter presenter) {
        mPresenter = presenter;
    }
}
