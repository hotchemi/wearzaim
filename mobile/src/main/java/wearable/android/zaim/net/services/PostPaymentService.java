package wearable.android.zaim.net.services;

import android.text.format.DateFormat;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import org.scribe.model.OAuthRequest;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

import java.util.Calendar;

import wearable.android.zaim.net.R;
import wearable.android.zaim.net.common.utils.Base64Utils;
import wearable.android.zaim.net.utils.ZaimUtils;

public class PostPaymentService extends WearableListenerService {

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        super.onMessageReceived(messageEvent);
        String payment = Base64Utils.decode(messageEvent.getData());
        execute(payment);
    }

    private void execute(String payment) {
        final OAuthRequest request = new OAuthRequest(Verb.POST, getString(R.string.payment));
        request.addBodyParameter("category_id", "101");
        request.addBodyParameter("genre_id", "10101");
        request.addBodyParameter("amount", payment);
        String date = DateFormat.format("'yyyy'-'MM'-'dd'", Calendar.getInstance()).toString();
        request.addBodyParameter("date", date);
        request.addBodyParameter("from_account_id", "1");
        final Token accessToken = ZaimUtils.getAccessToken(getApplicationContext());
        final OAuthService service = ZaimUtils.getOauthService(getApplicationContext());
        service.signRequest(accessToken, request);
        request.send();
    }

}
