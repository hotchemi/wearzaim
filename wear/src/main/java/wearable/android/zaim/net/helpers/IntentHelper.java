package wearable.android.zaim.net.helpers;

import android.content.Context;
import android.content.Intent;
import android.support.wearable.activity.ConfirmationActivity;

public final class IntentHelper {

    private IntentHelper() {
    }

    public static Intent createConfirmationSuccessIntent(Context context, int messageId) {
        return new Intent(context, ConfirmationActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION)
                .putExtra(ConfirmationActivity.EXTRA_ANIMATION_TYPE, ConfirmationActivity.SUCCESS_ANIMATION)
                .putExtra(ConfirmationActivity.EXTRA_MESSAGE, context.getString(messageId));
    }

    public static Intent createConfirmationFailureIntent(Context context, int messageId) {
        return new Intent(context, ConfirmationActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION)
                .putExtra(ConfirmationActivity.EXTRA_ANIMATION_TYPE, ConfirmationActivity.FAILURE_ANIMATION)
                .putExtra(ConfirmationActivity.EXTRA_MESSAGE, context.getString(messageId));
    }

}