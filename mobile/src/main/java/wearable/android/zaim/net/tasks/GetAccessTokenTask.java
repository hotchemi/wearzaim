package wearable.android.zaim.net.tasks;

import android.content.Context;
import android.os.AsyncTask;

import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

import wearable.android.zaim.net.utils.ZaimUtils;

public class GetAccessTokenTask extends AsyncTask<String, Void, Token> {

    private OAuthService mService;

    private Token mRequestToken;

    private GetAccessTokenTaskCallback mCallback;

    public GetAccessTokenTask(Context context, Token requestToken, GetAccessTokenTaskCallback callback) {
        mService = ZaimUtils.getOauthService(context);
        mRequestToken = requestToken;
        mCallback = callback;
    }

    @Override
    protected void onPostExecute(Token accessToken) {
        mCallback.onSuccessGetAccessToken(accessToken);
    }

    @Override
    protected Token doInBackground(String... params) {
        return mService.getAccessToken(mRequestToken, new Verifier(params[0]));
    }

}