package wearable.android.zaim.net.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.GestureDetectorCompat;
import android.support.wearable.activity.ConfirmationActivity;
import android.support.wearable.view.DismissOverlayView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.squareup.otto.Subscribe;

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

    private boolean mIsLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BusProvider.getInstance().register(this);

        mIsLogin = PreferenceUtils.isLogin(getApplicationContext());
        if (!mIsLogin) {
            setContentView(R.layout.activity_main_not_login);
            return;
        }
        setContentView(R.layout.activity_main);
        setUpLayout();
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
        startActivity(IntentHelper.createConfirmationIntent(this, R.string.message_success_pay,
                ConfirmationActivity.SUCCESS_ANIMATION));
        finish();
    }

    @Subscribe
    public void onFailurePayment(FailurePaymentEvent event) {
        startActivity(IntentHelper.createConfirmationIntent(this, R.string.message_failure_pay,
                ConfirmationActivity.FAILURE_ANIMATION));
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
    public boolean dispatchTouchEvent(@NonNull MotionEvent event) {
        if (mIsLogin) {
            return mGestureDetector.onTouchEvent(event) || super.dispatchTouchEvent(event);
        } else {
            return super.dispatchTouchEvent(event);
        }
    }

    private class LongPressListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public void onLongPress(MotionEvent event) {
            mDismissOverlayView.show();
        }
    }

}