package cn.saymagic.bluefinclient.presenter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import cn.saymagic.bluefinclient.data.ServerSession;
import cn.saymagic.bluefinclient.ui.splash.SplashContract;
import cn.saymagic.bluefinclient.ui.splash.SplashPresenter;
import cn.saymagic.bluefinsdk.entity.PingResult;
import rx.Observable;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by saymagic on 16/9/6.
 */
@RunWith(MockitoJUnitRunner.class)
public class SplashPresenterTest {

    @Mock
    private SplashContract.SplashView mSplashView;

    @Mock
    private ServerSession mServerSession;

    private SplashPresenter mPresenter;

    @Before
    public void setUp() {
        mPresenter = new SplashPresenter(mSplashView, mServerSession);
    }

    @Test
    public void testSubscribeSuccess() {
        PingResult pingResult = mock(PingResult.class);
        when(mServerSession.loginLast()).thenReturn(Observable.just(pingResult));
        mPresenter.subscribe();

        verify(mSplashView).onLoginDone();
        verify(mSplashView, never()).onLoginFailed();
    }


    @Test
    public void testSubscribeFailed() {
        when(mServerSession.loginLast()).thenReturn(Observable.<PingResult>error(new Exception()));
        mPresenter.subscribe();

        verify(mSplashView, never()).onLoginDone();
        verify(mSplashView).onLoginFailed();
    }

    @After
    public void end() {
        mPresenter.unsubscribe();
    }

}
