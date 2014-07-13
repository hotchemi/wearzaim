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
import wearable.android.zaim.net.events.BusProvider;
import wearable.android.zaim.net.events.FailurePaymentEvent;
import wearable.android.zaim.net.events.SuccessPaymentEvent;

public class CreatePaymentService extends IntentService {

    private static final String TAG = CreatePaymentService.class.getSimpleName();

    public static final String KEY_PAYMENT = "payment";

    private static final String REQUEST_PATH = "/payment/request";

    private static final String RESET_PATH = "/payment/reset";

    private static final int CONNECT_TIMEOUT_MS = 100;

    public CreatePaymentService() {
        super(TAG);
    }

    public static Intent createIntent(Context context, String payment) {
        Intent intent = new Intent(context, CreatePaymentService.class);
        intent.putExtra(KEY_PAYMENT, payment);
        return intent;
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
            BusProvider.getInstance().post(new FailurePaymentEvent());
            return;
        }
        String payment = intent.getStringExtra(KEY_PAYMENT);
        Wearable.MessageApi.sendMessage(googleApiClient,
                REQUEST_PATH, RESET_PATH, Base64Utils.encode(payment));
        BusProvider.getInstance().post(new SuccessPaymentEvent());
    }

}