package wearable.android.zaim.net.common.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Pair;

public final class PreferenceUtils {

    public static final String KEY_LOGIN = "isLogin";

    public static final String KEY_TOKEN = "token";

    public static final String KEY_SECRET = "secret";

    private PreferenceUtils() {
    }

    private static SharedPreferences getDefaultPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    private static SharedPreferences.Editor getDefaultEditor(Context context) {
        return getDefaultPreferences(context).edit();
    }

    public static boolean isLogin(Context context) {
        return getDefaultPreferences(context).getBoolean(KEY_LOGIN, false);
    }

    public static void saveAccessToken(Context context, String token, String secret) {
        SharedPreferences.Editor editor = getDefaultEditor(context);
        editor.putString(KEY_TOKEN, token);
        editor.putString(KEY_SECRET, secret);
        editor.commit();
        login(context, true);
    }

    public static void login(Context context, boolean isLogin) {
        SharedPreferences.Editor editor = getDefaultEditor(context);
        editor.putBoolean(KEY_LOGIN, isLogin);
        editor.commit();
    }

    public static Pair<String, String> getAccessToken(Context context) {
        SharedPreferences preferences = getDefaultPreferences(context);
        String token = preferences.getString(KEY_TOKEN, null);
        String secret = preferences.getString(KEY_SECRET, null);
        return new Pair<String, String>(token, secret);
    }

    public static void logOut(Context context) {
        getDefaultEditor(context).clear().commit();
    }

}