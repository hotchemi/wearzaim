package wearable.android.zaim.net.utils;

import android.content.Context;
import android.util.Pair;

import org.scribe.builder.ServiceBuilder;
import org.scribe.model.Token;
import org.scribe.oauth.OAuthService;

import scribe.builder.api.ZaimApi;
import wearable.android.zaim.net.R;
import wearable.android.zaim.net.common.utils.PreferenceUtils;

public final class ZaimUtils {

    private ZaimUtils() {
    }

    public static OAuthService getOauthService(final Context context) {
        return new ServiceBuilder()
                .provider(ZaimApi.class)
                .apiKey(context.getString(R.string.api_key))
                .apiSecret(context.getString(R.string.api_secret))
                .callback(context.getString(R.string.callback))
                .build();
    }

    public static Token getAccessToken(Context context) {
        Pair<String, String> pair = PreferenceUtils.getAccessToken(context);
        return new Token(pair.first, pair.second);
    }

}