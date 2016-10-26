package cn.saymagic.bluefinclient.presenter;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import cn.saymagic.bluefinclient.data.remote.ServerSession;
import cn.saymagic.bluefinclient.rx.RxUnitTestTools;
import cn.saymagic.bluefinclient.ui.login.LoginContract;
import cn.saymagic.bluefinclient.ui.login.LoginPresenter;
import cn.saymagic.bluefinsdk.entity.PingResult;
import rx.Observable;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
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

    @Mock
    private LoginPresenter mLoginPresenter;

    @Before
    public void setUp() {
        RxUnitTestTools.openRxTools();
        mLoginPresenter = new LoginPresenter(mView, mServerSession);
    }

    @Test
    @Ignore
    public void testLoginSuccess1() {

        LoginPresenter loginPresenter = mock(LoginPresenter.class);

        List list = Mockito.mock(List.class);

        Mockito.when(list.size()).thenReturn(1);

        Mockito.verify(list).add(Matchers.anyObject());
        when(loginPresenter.test11()).thenReturn(1);

        verify(loginPresenter, never()).test11();
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
