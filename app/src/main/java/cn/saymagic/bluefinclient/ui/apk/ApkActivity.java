package cn.saymagic.bluefinclient.ui.apk;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import cn.pedant.SweetAlert.SweetAlertDialog;
import cn.saymagic.bluefinclient.R;
import cn.saymagic.bluefinclient.data.model.Apk;
import cn.saymagic.bluefinclient.data.remote.ServerSessionContract;
import cn.saymagic.bluefinclient.ui.BaseActivity;
import cn.saymagic.bluefinclient.ui.UIController;
import cn.saymagic.bluefinclient.ui.common.widget.RecyclerViewEmptySupport;
import cn.saymagic.bluefinclient.ui.common.widget.SpacesItemDecoration;
import cn.saymagic.bluefinclient.util.UIUtil;

/**
 * Created by saymagic on 16/8/29.
 */
public class ApkActivity extends BaseActivity implements ApkContract.IApkView, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "MainActivity";

    @BindView(R.id.recycle_view)
    RecyclerViewEmptySupport mRecycleView;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.empty_view)
    TextView mEmptyView;


    private ApkContract.IApkPresenter mPresenter;

    private ApkAdapter mAdapter;

    @Override
    protected void initView() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);
        }
        new ApkPresenter(ServerSessionContract.INSTANCE, this).subscribe();
        mRecycleView.setEmptyView(mEmptyView);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecycleView.setLayoutManager(layoutManager);
        mRecycleView.addItemDecoration(new SpacesItemDecoration(UIUtil.dp2px(5)));
        mAdapter = new ApkAdapter(this, Collections.<Apk>emptyList());
        mRecycleView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.apk, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_item_sign_out) {
            mPresenter.signout();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }


    @Override
    public void onRefresh() {
        mPresenter.loadApks();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unsubscribe();
    }


    @Override
    public void onApkDataLoaded(List list) {
        mAdapter.setData(list);
    }

    @Override
    public void startLoding() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void stopLoading() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void setPresenter(ApkContract.IApkPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showErrorTip(int tipId) {
        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText(getString(tipId)).show();
    }

    @Override
    public void finishAndSwitchToLogin() {
        UIController.openLoginActivity(this);
        this.finish();
    }
}
