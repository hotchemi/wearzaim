package wearable.android.zaim.net.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public final class NetWorkUtils {

    private NetWorkUtils() {
    }

    public static boolean isConnect(final Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return info != null && info.isConnected();
    }

    public static boolean isNotConnect(final Context context) {
        return !isConnect(context);
    }

}