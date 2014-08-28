package wearable.android.zaim.net.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.view.GridViewPager;

import wearable.android.zaim.net.BuildConfig;
import wearable.android.zaim.net.R;
import wearable.android.zaim.net.adapters.GridPagerAdapter;
import wearable.android.zaim.net.common.utils.PreferenceUtils;

public class MainActivity extends Activity {

    GridViewPager gridViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (BuildConfig.DEBUG) {
            boolean isNotLogin = PreferenceUtils.isNotLogin(getApplicationContext());
            if (!isNotLogin) {
                setContentView(R.layout.activity_main_not_login);
                return;
            }
        }
        setContentView(R.layout.activity_main);
        gridViewPager = (GridViewPager) findViewById(R.id.grid_view_pager);
        GridPagerAdapter adapter = new GridPagerAdapter(getFragmentManager());
        gridViewPager.setAdapter(adapter);
    }

}