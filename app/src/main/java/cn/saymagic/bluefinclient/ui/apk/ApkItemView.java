package cn.saymagic.bluefinclient.ui.apk;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.bumptech.glide.Glide;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.saymagic.bluefinclient.R;
import cn.saymagic.bluefinclient.data.model.Apk;
import cn.saymagic.bluefinclient.ui.UIController;
import cn.saymagic.bluefinclient.ui.common.IAdapterView;
import cn.saymagic.bluefinclient.util.DateUtil;
import cn.saymagic.bluefinclient.util.UIUtil;

/**
 * Created by saymagic on 16/9/1.
 */
public class ApkItemView extends FrameLayout implements IAdapterView<Apk> {


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
        Glide.with(getContext())
                .load(apk.icon)
                .placeholder(TextDrawable.builder().buildRect(String.valueOf(apk.name.charAt(0)), Color.GRAY))
                .into(apkIcon);

        apkName.setText(apk.name + " [ " + apk.versionName + "-" + apk.versionCode + "]");
        apkPackageName.setText(apk.packageName);
        apkVersionInfo.setText(DateUtil.getDateString(new Date(apk.updateTime)));
        if (apk.installed) {
            apkContainter.setBackgroundResource(R.drawable.apk_item_bg);
            apkContainter.setClickable(true);
            apkContainter.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (apk.launchIntent != null) {
                        UIController.openActivityByIntent(getContext(), apk.launchIntent);
                    }
                }
            });
        } else {
            apkContainter.setBackgroundResource(R.drawable.apk_item_gray_bg);
        }

    }
}
