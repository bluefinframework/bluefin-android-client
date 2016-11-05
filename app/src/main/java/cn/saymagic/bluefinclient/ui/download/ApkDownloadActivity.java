package cn.saymagic.bluefinclient.ui.download;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.daimajia.numberprogressbar.NumberProgressBar;

import butterknife.BindView;
import butterknife.OnClick;
import cn.saymagic.bluefinclient.R;
import cn.saymagic.bluefinclient.data.download.DownloadPerformContract;
import cn.saymagic.bluefinclient.data.download.DownloadSaveContract;
import cn.saymagic.bluefinclient.data.model.Apk;
import cn.saymagic.bluefinclient.image.ImageLoaderContract;
import cn.saymagic.bluefinclient.ui.BaseActivity;
import cn.saymagic.bluefinclient.ui.UIController;

public class ApkDownloadActivity extends BaseActivity implements ApkDownloadContract.IDownloadView {

    private ApkDownloadContract.IDownloadPresenter mPresenter;

    @BindView(R.id.download_icon)
    ImageView mIcon;

    @BindView(R.id.download_version)
    TextView mVersion;

    @BindView(R.id.progress_bar)
    NumberProgressBar mProgressBar;

    @BindView(R.id.download_title)
    TextView mTitle;

    @BindView(R.id.install_apk)
    Button mInstallButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        Apk apk = intent.getParcelableExtra(UIController.EXTRA_APK_TRANSMISSION);
        if (apk == null) {
            UIController.finishActivity(this);
            return;
        }
        new ApkDownloadPresenter(this,
                apk,
                this,
                DownloadPerformContract.URL_DOWNLOADER,
                DownloadSaveContract.DEFAULT).subscribe();
        mTitle.setText(apk.name);
        mVersion.setText(apk.versionName);
        ImageLoaderContract.INSTANCE.load(this,
                apk.icon,
                TextDrawable.builder().buildRect(String.valueOf(TextUtils.isEmpty(apk.name) ? "" : apk.name.charAt(0)), Color.GRAY),
                mIcon);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_apk_download;
    }

    @Override
    public void refreshDownloadState(float percentage) {
        mProgressBar.setProgress((int) (percentage * 100));
    }

    @Override
    public void onDownloadFinished() {
        mProgressBar.setVisibility(View.GONE);
        mInstallButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void onStartDownload() {

    }

    @Override
    public void onDownloadError(String errorMsg) {

    }

    @Override
    public void doFinish() {
        UIController.finishActivity(this);
    }

    @Override
    public void setPresenter(ApkDownloadContract.IDownloadPresenter presenter) {
        this.mPresenter = presenter;
    }

    @OnClick(R.id.install_apk)
    public void onViewClick(View view) {
        mPresenter.installApk();
    }
}
