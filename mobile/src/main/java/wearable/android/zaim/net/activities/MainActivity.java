package wearable.android.zaim.net.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;

import wearable.android.zaim.net.R;
import wearable.android.zaim.net.common.utils.PreferenceUtils;
import wearable.android.zaim.net.common.utils.ToastUtils;
import wearable.android.zaim.net.services.SendLoginStatusService;

public class MainActivity extends Activity {

    public static Intent createIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        PreferenceUtils.logOut(getApplicationContext());
        startService(SendLoginStatusService.createIntent(this, false));
        finish();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        }, 1000);
        ToastUtils.show(this, R.string.message_logout);
        return super.onOptionsItemSelected(item);
    }

}