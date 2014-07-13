package wearable.android.zaim.net.common.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {

    private ToastUtils() {
    }

    public static void show(Context context, int messageId) {
        Toast.makeText(context, context.getText(messageId), Toast.LENGTH_SHORT).show();
    }

}