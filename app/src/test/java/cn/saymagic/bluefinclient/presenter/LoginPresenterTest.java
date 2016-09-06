package cn.saymagic.bluefinclient.presenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import cn.saymagic.bluefinclient.data.ServerSession;
import cn.saymagic.bluefinclient.rx.RxUnitTestTools;
import cn.saymagic.bluefinclient.ui.login.LoginContract;
import cn.saymagic.bluefinclient.ui.login.LoginPresenter;
import cn.saymagic.bluefinsdk.entity.PingResult;
import rx.Observable;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by saymagic on 16/9/6.
 */
@RunWith(MockitoJUnitRunner.class)
public class LoginPresenterTest {

    @Mock
    private LoginContract.ILoginView mView;

    @Mock
    private ServerSession mServerSession;

    private LoginPresenter mLoginPresenter;

    @Before
    public void setUp() {
        RxUnitTestTools.openRxTools();
        mLoginPresenter = new LoginPresenter(mView, mServerSession);
    }

    @Test
    public void testLoginSuccess() {
        PingResult result = mock(PingResult.class);
        when(mServerSession.login(anyString())).thenReturn(Observable.just(result));
        mLoginPresenter.doLogin(anyString());
        verify(mServerSession).login(anyString());
        verify(mView).startLoding();
        verify(mView).stopLoading();
        verify(mView).onLoginDone();
    }

    @Test
    public void testLoginFailed() {
        when(mServerSession.login(anyString())).thenReturn(Observable.<PingResult>error(new Exception()));
        mLoginPresenter.doLogin(anyString());
        verify(mServerSession).login(anyString());
        verify(mView).startLoding();
        verify(mView).stopLoadingWithErrorTip(anyInt());
    }

    @Test
    public void testSubscribe() {
        String str = "http://bluefin.saymagic.cn";
        when(mServerSession.getServerUrl()).thenReturn(str);
        mLoginPresenter.subscribe();
        verify(mView).initServerUrl(str);
    }

    public void end() {
        mLoginPresenter.unsubscribe();
    }
}
