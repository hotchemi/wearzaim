package wearable.android.zaim.net.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.wearable.view.DismissOverlayView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.squareup.otto.Subscribe;

import wearable.android.zaim.net.BuildConfig;
import wearable.android.zaim.net.R;
import wearable.android.zaim.net.common.utils.PreferenceUtils;
import wearable.android.zaim.net.common.utils.ToastUtils;
import wearable.android.zaim.net.events.BusProvider;
import wearable.android.zaim.net.events.FailurePaymentEvent;
import wearable.android.zaim.net.events.SuccessPaymentEvent;
import wearable.android.zaim.net.helpers.IntentHelper;
import wearable.android.zaim.net.services.CreatePaymentService;
import wearable.android.zaim.net.views.SeekArc;

public class MainActivity extends Activity implements SeekArc.OnSeekArcChangeListener,
        View.OnClickListener {

    private TextView mAmountTextView;

    private GestureDetectorCompat mGestureDetector;

    private DismissOverlayView mDismissOverlayView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean isLogin = PreferenceUtils.isLogin(getApplicationContext());
        if (!BuildConfig.DEBUG && !isLogin) {
            setContentView(R.layout.activity_main_not_login);
            return;
        }

        setContentView(R.layout.activity_main);
        setUpLayout();
        ToastUtils.show(getApplicationContext(), R.string.message_not_login);
        BusProvider.getInstance().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BusProvider.getInstance().unregister(this);
    }

    @Override
    public void onProgressChanged(SeekArc seekArc, int progress, boolean fromUser) {
        mAmountTextView.setText(String.valueOf(progress));
    }

    @Override
    public void onStartTrackingTouch(SeekArc seekArc) {
    }

    @Override
    public void onStopTrackingTouch(SeekArc seekArc) {
    }

    @Override
    public void onClick(View v) {
        String payment = mAmountTextView.getText().toString();
        startService(CreatePaymentService.createIntent(this, payment));
    }

    @Subscribe
    public void onSuccessPayment(SuccessPaymentEvent event) {
        startActivity(IntentHelper.createConfirmationSuccessIntent(this, R.string.message_success_pay));
        finish();
    }

    @Subscribe
    public void onFailurePayment(FailurePaymentEvent event) {
        startActivity(IntentHelper.createConfirmationFailureIntent(this, R.string.message_failure_pay));
        ToastUtils.show(this, R.string.message_try_again);
    }

    private void setUpLayout() {
        SeekArc mSeekArc = (SeekArc) findViewById(R.id.seekarc);
        mSeekArc.setOnSeekArcChangeListener(this);
        mAmountTextView = (TextView) findViewById(R.id.amount);
        mAmountTextView.setOnClickListener(this);
        mDismissOverlayView = (DismissOverlayView) findViewById(R.id.dismiss_overlay);
        mGestureDetector = new GestureDetectorCompat(this, new LongPressListener());
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event) || super.dispatchTouchEvent(event);
    }

    private class LongPressListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public void onLongPress(MotionEvent event) {
            mDismissOverlayView.show();
        }
    }

}