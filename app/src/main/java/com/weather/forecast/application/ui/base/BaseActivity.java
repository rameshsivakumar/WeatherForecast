package com.weather.forecast.application.ui.base;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.weather.forecast.R;
import com.weather.forecast.utils.DialogUtils;

public abstract class BaseActivity<T extends ViewDataBinding> extends AppCompatActivity {

    private T mViewDataBinding;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        performDataBinding();
    }

    private void performDataBinding() {
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId());
        setDataBinding();
        mViewDataBinding.executePendingBindings();
    }

    protected T getViewDataBinding(){
        return mViewDataBinding;
    }

    protected abstract int getLayoutId();

    protected abstract void setDataBinding();

    protected void showProgressDialog(int messageId) {
        if (null == progressDialog) {
            progressDialog = new ProgressDialog(this, R.style.alert_dialog_theme);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCancelable(false);
        }
        progressDialog.setMessage(getString(messageId));
        progressDialog.show();
    }

    protected void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.hide();
        }
    }

    protected void showNoInternetMessage() {
        DialogUtils.getAlertDialog(this, R.string.no_network_access_title, getResources().getString(R.string.no_network_access_message), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }, true, R.style.alert_dialog_theme).show();
    }

    protected void showErrorMessage(int messageId) {
        DialogUtils.getAlertDialog(this, -1, getResources().getString(messageId), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }, true, R.style.alert_dialog_theme).show();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if(progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }
}
