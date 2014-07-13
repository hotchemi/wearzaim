package wearable.android.zaim.net.services;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import wearable.android.zaim.net.common.utils.Base64Utils;
import wearable.android.zaim.net.common.utils.PreferenceUtils;

public class GetLoginStatusService extends WearableListenerService {

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        super.onMessageReceived(messageEvent);
        boolean isLogin = Boolean.valueOf(Base64Utils.decode(messageEvent.getData()));
        if (isLogin) {
            PreferenceUtils.login(getApplicationContext(), true);
        } else {
            PreferenceUtils.logOut(getApplicationContext());
        }
    }

}
