package wearable.android.zaim.net.common.utils;

import android.util.Base64;

public final class Base64Utils {

    private Base64Utils() {
    }

    public static byte[] encode(final String str) {
        return Base64.encode(str.getBytes(), Base64.DEFAULT);
    }

    public static String decode(final byte[] data) {
        return new String(Base64.decode(data, Base64.DEFAULT));
    }

}