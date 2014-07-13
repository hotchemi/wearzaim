package wearable.android.zaim.net.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import wearable.android.zaim.net.common.utils.PreferenceUtils;

public class RouteActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Class cls = PreferenceUtils.isLogin(this) ? MainActivity.class : LoginActivity.class;
        startActivity(new Intent(getApplicationContext(), cls));
    }

}