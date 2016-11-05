package cn.saymagic.bluefinclient.ui.apk;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.saymagic.bluefinclient.R;
import cn.saymagic.bluefinclient.data.model.Apk;
import cn.saymagic.bluefinclient.image.ImageLoaderContract;
import cn.saymagic.bluefinclient.ui.apk.itemview.ApkItemViewContract;
import cn.saymagic.bluefinclient.ui.apk.itemview.ApkItemViewPresenter;
import cn.saymagic.bluefinclient.ui.common.IAdapterView;
import cn.saymagic.bluefinclient.util.DateUtil;
import cn.saymagic.bluefinclient.util.UIUtil;

/**
 * Created by saymagic on 16/9/1.
 */
public class ApkItemView extends FrameLayout implements IAdapterView<Apk>, ApkItemViewContract.IApkItemView {


    @BindView(R.id.apk_icon)
    ImageView apkIcon;
    @BindView(R.id.apk_name)
    TextView apkName;
    @BindView(R.id.apk_version_info)
    TextView apkVersionInfo;
    @BindView(R.id.apk_package_name)
    TextView apkPackageName;
    @BindView(R.id.apk_item_containter)
    View apkContainter;
    @BindView(R.id.apk_more_version)
    Button apkMoreVersionButton;

    private Apk mApk;

    private ApkItemViewContract.IApkItemViewPresenter mPresenter;

    private static final int PADDING = UIUtil.dp2px(5);

    public ApkItemView(Context context) {
        super(context);
        init();
    }


    public ApkItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.apk_recycleview_item, this, true);
        ButterKnife.bind(this);
    }

    @Override
    public void bind(@NonNull final Apk apk) {
        this.mApk = apk;
        ImageLoaderContract.INSTANCE.load(getContext(),
                apk.icon,
                TextDrawable.builder().buildRect(String.valueOf(TextUtils.isEmpty(apk.name) ? "" : apk.name.charAt(0)), Color.GRAY),
                apkIcon);

        apkName.setText(apk.name + " [ " + apk.versionName + " / " + apk.versionCode + "]");
        apkPackageName.setText(apk.packageName);
        apkVersionInfo.setText(DateUtil.getDateString(new Date(apk.updateTime)));
        apkContainter.setClickable(true);
        if (apk.installed) {
            apkContainter.setBackgroundResource(R.drawable.apk_item_bg);
        } else {
            apkContainter.setBackgroundResource(R.drawable.apk_item_gray_bg);
        }
        new ApkItemViewPresenter(this, getContext(), mApk).subscribe();
    }

    @Override
    public void setPresenter(ApkItemViewContract.IApkItemViewPresenter presenter) {
        this.mPresenter = presenter;
    }

    @OnClick (value = {R.id.apk_more_version, R.id.apk_item_containter})
    public void onViewClick(View v) {
        if (mPresenter != null) {
            mPresenter.onClick(v);
        }
    }
}
