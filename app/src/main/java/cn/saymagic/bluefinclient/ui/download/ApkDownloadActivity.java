package cn.saymagic.bluefinclient.ui.download;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.daimajia.numberprogressbar.NumberProgressBar;

import butterknife.BindView;
import butterknife.OnClick;
import cn.saymagic.bluefinclient.R;
import cn.saymagic.bluefinclient.data.download.DownloadContract;
import cn.saymagic.bluefinclient.data.model.to.ApkTransmission;
import cn.saymagic.bluefinclient.ui.BaseActivity;
import cn.saymagic.bluefinclient.ui.UIController;

public class ApkDownloadActivity extends BaseActivity implements ApkDownloadContract.IDownloadView {

    private ApkDownloadContract.IDownloadPresenter mPresenter;

    private int val = 0;

    @BindView(R.id.progress_bar)
    NumberProgressBar mProgressBar;

    @BindView(R.id.download_title)
    TextView mTitle;

    @BindView(R.id.install_apk)
    Button mInstallButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        ApkTransmission apkTransmission = intent.getParcelableExtra(UIController.EXTRA_APK_TRANSMISSION);
        if (apkTransmission == null) {
            UIController.finishActivity(this);
            return;
        }
        new ApkDownloadPresenter(this, apkTransmission, this, DownloadContract.URL_DOWNLOADER).subscribe();
        mTitle.setText(apkTransmission.getName());
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
        mInstallButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void onStartDownload() {

    }

    @Override
    public void onDownloadError(String errorMsg) {

    }

    @Override
    public void setPresenter(ApkDownloadContract.IDownloadPresenter presenter) {
        this.mPresenter = presenter;
    }

    @OnClick(R.id.install_apk)
    public void onViewClick(View view) {

    }
}
