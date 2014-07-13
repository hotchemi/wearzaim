package wearable.android.zaim.net.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Wearable;

import java.util.concurrent.TimeUnit;

import wearable.android.zaim.net.common.utils.Base64Utils;

public class SendLoginStatusService extends IntentService {

    private static final String TAG = SendLoginStatusService.class.getSimpleName();

    private static final String KEY_LOGIN = "login";

    private static final String REQUEST_PATH = "/login/request";

    private static final String RESET_PATH = "/login/reset";

    private static final int CONNECT_TIMEOUT_MS = 100;

    public SendLoginStatusService() {
        super(TAG);
    }

    public static Intent createIntent(Context context, boolean login) {
        Intent intent = new Intent(context, SendLoginStatusService.class);
        intent.putExtra(KEY_LOGIN, login);
        return new Intent(context, SendLoginStatusService.class);
    }

    @Override
    public void onHandleIntent(Intent intent) {
        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .build();
        ConnectionResult result = googleApiClient.blockingConnect(CONNECT_TIMEOUT_MS,
                TimeUnit.MILLISECONDS);
        if (!result.isSuccess()) {
            Log.e(TAG, "Failed to connect to GoogleApiClient.");
            return;
        }
        String login = String.valueOf(intent.getBooleanExtra(KEY_LOGIN, false));
        Wearable.MessageApi.sendMessage(googleApiClient,
                REQUEST_PATH, RESET_PATH, Base64Utils.encode(login));
    }

}