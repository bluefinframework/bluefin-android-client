package cn.saymagic.bluefinclient.presenter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import cn.saymagic.bluefinclient.data.model.Apk;
import cn.saymagic.bluefinclient.data.remote.ApkRepositoryContract;
import cn.saymagic.bluefinclient.rx.RxUnitTestTools;
import cn.saymagic.bluefinclient.ui.apk.ApkContract;
import cn.saymagic.bluefinclient.ui.apk.ApkPresenter;
import rx.Observable;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by saymagic on 16/9/6.
 */
@RunWith(MockitoJUnitRunner.class)
public class ApkPresenterTest {

    @Mock
    private ApkContract.IApkView mApkView;

    @Mock
    private ApkRepositoryContract mApkRepository;

    private ApkPresenter mApkPresenter;

    @Before
    public void setUp() {
        RxUnitTestTools.openRxTools();
        mApkPresenter = new ApkPresenter(mApkRepository, mApkView);
    }

    @Test
    public void testLoadApkSuccess() {
        Apk apk1 = mock(Apk.class);
        Apk apk2 = mock(Apk.class);
        Apk apk3 = mock(Apk.class);
        Apk apk4 = mock(Apk.class);
        when(mApkRepository.loadAllApks()).thenReturn(Observable.just(apk1, apk2, apk3, apk4));

        mApkPresenter.loadApks();

        verify(mApkRepository).loadAllApks();
        verify(mApkView).startLoding();
        verify(mApkView).stopLoading();
        verify(mApkView).onApkDataLoaded(anyList());
        verify(mApkView, never()).showErrorTip(anyInt());
    }

    @Test
    public void testLoadFailed() {
        when(mApkRepository.loadAllApks()).thenReturn(Observable.<Apk>error(new Exception()));
        mApkPresenter.loadApks();

        verify(mApkRepository).loadAllApks();
        verify(mApkView).startLoding();
        verify(mApkView).stopLoading();
        verify(mApkView).showErrorTip(anyInt());
        verify(mApkView, never()).onApkDataLoaded(anyList());
    }

    @After
    public void end() {
        mApkPresenter.unsubscribe();
    }

}
