package cn.saymagic.bluefinclient.ui.login;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import butterknife.BindView;
import cn.pedant.SweetAlert.SweetAlertDialog;
import cn.saymagic.bluefinclient.R;
import cn.saymagic.bluefinclient.data.remote.ServerSessionContract;
import cn.saymagic.bluefinclient.ui.BaseActivity;
import cn.saymagic.bluefinclient.ui.UIController;

public class LoginActivity extends BaseActivity implements LoginContract.ILoginView {


    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.login_layout_serverurl_til)
    TextInputLayout mServerurlTil;
    @BindView(R.id.login_layout_serverurl_tv)
    EditText mServerurlTv;

    private SweetAlertDialog mDialog;

    private LoginContract.ILoginPresenter mPresenter;
    private MenuItem mLoginMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        new LoginPresenter(this, ServerSessionContract.INSTANCE).subscribe();
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(null);
            actionBar.setHomeAsUpIndicator(android.R.drawable.ic_menu_close_clear_cancel);
        }
        mServerurlTil.setHint(getString(R.string.login_view_server_url));
        mServerurlTv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (mLoginMenu == null) {
                    return;
                }
                mLoginMenu.setEnabled(!TextUtils.isEmpty(mServerurlTv.getText().toString()));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.login, menu);
        mLoginMenu = menu.findItem(R.id.menu_item_login);
        mLoginMenu.setEnabled(!TextUtils.isEmpty(mServerurlTv.getText().toString()));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_item_login) {
            login();
        }
        return super.onOptionsItemSelected(item);
    }

    private void login() {
        mPresenter.doLogin(mServerurlTv.getText().toString());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void startLoding() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
        mDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        mDialog.getProgressHelper().setBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        mDialog.setTitleText(getString(R.string.login_view_connecting));
        mDialog.setCancelable(false);
        mDialog.show();
    }

    @Override
    public void stopLoading() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.subscribe();
    }

    @Override
    public void setPresenter(LoginContract.ILoginPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onLoginDone() {
        UIController.openApkActivity(this);
        this.finish();
    }

    @Override
    public void stopLoadingWithErrorTip(int strId) {
        if (mDialog != null) {
            mDialog.setTitleText(getString(strId)).changeAlertType(SweetAlertDialog.ERROR_TYPE);
        }
    }

    @Override
    public void initServerUrl(String serverUrl) {
        if (TextUtils.isEmpty(mServerurlTv.getText().toString()) && !TextUtils.isEmpty(serverUrl)) {
            mServerurlTv.setText(serverUrl);
            mServerurlTv.setSelection(serverUrl.length());
            if (mLoginMenu != null) {
                mLoginMenu.setEnabled(true);
            }
        }
    }
}
