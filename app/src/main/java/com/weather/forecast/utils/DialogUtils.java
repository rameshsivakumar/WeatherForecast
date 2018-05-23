package com.weather.forecast.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.weather.forecast.R;

public class DialogUtils {
    private DialogUtils() {}

    public static AlertDialog getAlertDialog(Context context, int titleID, String message, DialogInterface.OnClickListener onclickListener, boolean isCancelable, int themeResId) {
        AlertDialog.Builder builder;
        builder = themeResId != -1 ? new AlertDialog.Builder(context, themeResId) : new AlertDialog.Builder(context);
        if (titleID != -1) {
            builder.setTitle(context.getResources().getString(titleID));
        }
        if (message.trim().isEmpty())
            throw new IllegalArgumentException("Alert Dialog message can't be empty!");
        builder.setMessage(message);

        builder.setNeutralButton(context.getResources().getString(R.string.ok), onclickListener);
        builder.setCancelable(isCancelable);
        return builder.show();
    }
}
