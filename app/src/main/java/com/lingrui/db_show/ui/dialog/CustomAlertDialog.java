package com.lingrui.db_show.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lingrui.db_show.BaseActivity;
import com.lingrui.db_show.DdApplication;
import com.lingrui.db_show.R;

public class CustomAlertDialog {
    private final BaseActivity mActivity;
    private Dialog mDialog;

    private TextView iv_title;

    private TextView iv_content;

    private Button btn_negative;

    private Button btn_positive;

    public CustomAlertDialog(BaseActivity activity) {
        this.mActivity = activity;
    }

    public CustomAlertDialog builder() {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.dialog_custom_alert, null);
        iv_title = view.findViewById(R.id.iv_title);
        iv_content = view.findViewById(R.id.iv_content);
        btn_negative = view.findViewById(R.id.btn_negative);
        btn_positive = view.findViewById(R.id.btn_positive);

        mDialog = new MyDialog(mActivity, R.style.AlertDialogStyle);
        mDialog.setContentView(view);
        mDialog.setCanceledOnTouchOutside(false);
        return this;
    }

    public void show() {
        if (mActivity.isFinishing()) {
            return;
        }

        if (mDialog != null && !mDialog.isShowing()) {
            iv_title.setVisibility(TextUtils.isEmpty(iv_title.getText().toString()) ? View.GONE : View.VISIBLE);
            iv_content.setVisibility(TextUtils.isEmpty(iv_content.getText().toString()) ? View.GONE : View.VISIBLE);
            btn_negative.setVisibility(TextUtils.isEmpty(btn_negative.getText().toString()) ? View.GONE : View.VISIBLE);
            btn_positive.setVisibility(TextUtils.isEmpty(btn_positive.getText().toString()) ? View.GONE : View.VISIBLE);

            mDialog.show();
        }
    }

    public void dismiss() {
        if (mDialog == null || !mDialog.isShowing()) {
            return;
        }

        mDialog.cancel();
    }

    private class MyDialog extends Dialog {
        public MyDialog(@NonNull Context context, @StyleRes int themeResId) {
            super(context, themeResId);
        }
    }

    public CustomAlertDialog setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
        mDialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
        return this;
    }

    public CustomAlertDialog setTitle(String title) {
        iv_title.setText(title);
        return this;
    }

    public CustomAlertDialog setContent(String content) {
        iv_content.setText(content);
        return this;
    }

    public CustomAlertDialog setTitle(@StringRes int title) {
        iv_title.setText(title);
        return this;
    }

    public CustomAlertDialog setContent(@StringRes int content) {
        iv_content.setText(content);
        return this;
    }

    public CustomAlertDialog setPositiveButton(@StringRes int positiveId, final View.OnClickListener listener) {
        return setPositiveButton(DdApplication.getInstance().getString(positiveId), listener);
    }

    public CustomAlertDialog setPositiveButton(String text, final View.OnClickListener listener) {
        btn_positive.setText(text);
        btn_positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    listener.onClick(v);
                }
            }
        });
        return this;
    }

    public CustomAlertDialog setNegativeButton(@StringRes int negativeId, final View.OnClickListener listener) {
        return setNegativeButton(DdApplication.getInstance().getString(negativeId), listener);
    }

    public CustomAlertDialog setNegativeButton(String text, final View.OnClickListener listener) {
        btn_negative.setText(text);
        btn_negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    listener.onClick(v);
                }
            }
        });
        return this;
    }

    public CustomAlertDialog setOnDismissListener(final DialogInterface.OnDismissListener onDismissListener) {
        mDialog.setOnDismissListener(onDismissListener);
        return this;
    }
}
