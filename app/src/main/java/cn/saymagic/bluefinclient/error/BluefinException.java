package cn.saymagic.bluefinclient.error;

import android.support.annotation.Nullable;

import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;

import cn.saymagic.bluefinclient.R;
import cn.saymagic.bluefinclient.error.common.NetworkDisabledException;


/**
 * Created by saymagic on 16/9/2.
 */
public class BluefinException {

    public static int getErrorTipByErrorType(@Nullable Throwable throwable) {
        return getErrorTipByErrorType(throwable, R.string.error_unknow);
    }

    public static int getErrorTipByErrorType(@Nullable Throwable throwable, int defaultError) {
        if (throwable == null) {
            return R.string.error_empty;
        }
        if (throwable instanceof cn.saymagic.bluefinsdk.exception.BluefinException) {
            throwable = throwable.getCause();
        }
        if(throwable instanceof NetworkDisabledException){
            return R.string.error_no_network;
        }else if (throwable instanceof MalformedURLException) {
            return R.string.error_url_error;
        }else if(throwable instanceof ConnectException){
            return R.string.error_cannot_connect;
        } else if (throwable instanceof SocketTimeoutException) {
            return R.string.error_connect_timeout;
        }
        return defaultError;
    }
}
