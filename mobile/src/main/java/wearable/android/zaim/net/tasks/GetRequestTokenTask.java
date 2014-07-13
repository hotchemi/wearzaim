package wearable.android.zaim.net.tasks;

import android.content.Context;
import android.os.AsyncTask;

import org.scribe.model.Token;

import wearable.android.zaim.net.utils.ZaimUtils;

public class GetRequestTokenTask extends AsyncTask<Void, Void, Token> {

    private Context mContext;

    private GetRequestTokenTaskCallback mCallback;

    public GetRequestTokenTask(Context context, GetRequestTokenTaskCallback callback) {
        mContext = context;
        mCallback = callback;
    }

    @Override
    protected void onPostExecute(Token requestToken) {
        mCallback.onSuccessGetRequestToken(requestToken);
    }

    @Override
    protected Token doInBackground(Void... params) {
        return ZaimUtils.getOauthService(mContext).getRequestToken();
    }

}