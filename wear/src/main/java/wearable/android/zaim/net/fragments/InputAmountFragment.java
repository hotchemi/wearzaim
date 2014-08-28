package wearable.android.zaim.net.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.wearable.activity.ConfirmationActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.otto.Subscribe;

import butterknife.ButterKnife;
import butterknife.InjectView;
import wearable.android.zaim.net.R;
import wearable.android.zaim.net.common.utils.ToastUtils;
import wearable.android.zaim.net.events.BusProvider;
import wearable.android.zaim.net.events.FailurePaymentEvent;
import wearable.android.zaim.net.events.SuccessPaymentEvent;
import wearable.android.zaim.net.helpers.IntentHelper;
import wearable.android.zaim.net.services.CreatePaymentService;
import wearable.android.zaim.net.views.SeekArc;

public class InputAmountFragment extends Fragment implements
        SeekArc.OnSeekArcChangeListener, View.OnClickListener {

    @InjectView(R.id.seek_arc)
    SeekArc seekArc;

    @InjectView(R.id.amount)
    TextView amountTextView;

    private Activity activity;

    public static InputAmountFragment newInstance() {
        return new InputAmountFragment();
    }

    public InputAmountFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BusProvider.getInstance().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_input_amount, container, false);
        ButterKnife.inject(this, view);
        seekArc.setOnSeekArcChangeListener(this);
        amountTextView.setOnClickListener(this);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BusProvider.getInstance().unregister(this);
    }

    @Override
    public void onClick(View v) {
        String payment = amountTextView.getText().toString();
        activity.startService(CreatePaymentService.createIntent(activity, payment));
    }

    @Override
    public void onProgressChanged(SeekArc seekArc, int progress, boolean fromUser) {
        amountTextView.setText(String.valueOf(progress));
    }

    @Override
    public void onStartTrackingTouch(SeekArc seekArc) {
    }

    @Override
    public void onStopTrackingTouch(SeekArc seekArc) {
    }

    @Subscribe
    public void onSuccessPayment(SuccessPaymentEvent event) {
        startActivity(IntentHelper.createConfirmationIntent(activity, R.string.message_success_pay,
                ConfirmationActivity.SUCCESS_ANIMATION));
        getActivity().finish();
    }

    @Subscribe
    public void onFailurePayment(FailurePaymentEvent event) {
        startActivity(IntentHelper.createConfirmationIntent(activity, R.string.message_failure_pay,
                ConfirmationActivity.FAILURE_ANIMATION));
        ToastUtils.show(activity, R.string.message_try_again);
    }

}
