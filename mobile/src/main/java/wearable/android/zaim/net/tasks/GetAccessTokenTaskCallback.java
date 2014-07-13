package wearable.android.zaim.net.tasks;

import org.scribe.model.Token;

public interface GetAccessTokenTaskCallback {

    void onSuccessGetAccessToken(Token accessToken);

}