package com.lingrui.db_show;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.StringRes;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.lingrui.db_show.ui.dialog.CustomAlertDialog;
import com.lingrui.db_show.util.Flog;
import com.lingrui.db_show.util.KdActivityManager;
import com.lingrui.db_show.util.ViewUtils;

import org.xutils.x;

import java.lang.ref.WeakReference;

public class BaseActivity extends AppCompatActivity {
    private static final String TAG = "BaseActivity";

    protected View title_layout;
    protected ImageView left_title_iv;
    protected TextView center_title_tv;
    protected TextView right_title_tv;
    protected boolean hasTitleView = true;

    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        x.view().inject(this);

        mFragmentManager = getSupportFragmentManager();
        initTitle();
        createView();
        KdActivityManager.getInstance().addActivity(this);
    }

    protected void setTitleVis(boolean show) {
        if (title_layout != null) {
            title_layout.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }

    private void initTitle() {
        title_layout = this.findViewById(R.id.title_layout);
        left_title_iv = this.findViewById(R.id.left_icon_iv);
        if (left_title_iv == null) {
            hasTitleView = false;
            return;
        }
        left_title_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseActivity.this.finish();
            }
        });
        center_title_tv = this.findViewById(R.id.title_tv);
        right_title_tv = this.findViewById(R.id.right_tv);
        right_title_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickTitleRight();
            }
        });
    }

    protected void setPageTitle(@StringRes int title) {
        setPageTitle(ViewUtils.getString(title));
    }

    protected void setPageTitle(String title) {
        if (center_title_tv == null) {
            return;
        }

        center_title_tv.setText(title);
    }

    protected void createView() {

    }

    protected void clickTitleRight() {

    }

    @Override
    public void startActivity(Intent intent) {
        try {
            super.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            // ignore
            Flog.d(TAG, "ActivityNotFoundException e:" + e);
        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // ignore
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        KdActivityManager.getInstance().removeActivity(this);
    }

    protected abstract static class WeakHandler<T extends BaseActivity> extends Handler {

        private final WeakReference<T> fragmentReference;

        public WeakHandler(T reference) {
            fragmentReference = new WeakReference<T>(reference);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            final T reference = fragmentReference.get();
            if (reference == null) {
                return;
            }
            handleInnerMessage(reference, msg);
        }

        public abstract void handleInnerMessage(T reference, Message msg);
    }

    protected BaseFragment addFragment(int layoutID, String pageName, String prePageName,
                                       Bundle bundle, BaseFragment firstFragment) {
        BaseFragment curFragment = null;
        BaseFragment preFragment = null;
        FragmentTransaction transaction = null;

        if (prePageName != null && prePageName.equals(pageName)) {
            curFragment = (BaseFragment) mFragmentManager.findFragmentByTag(pageName);
            if (bundle != null && curFragment != null) {
                curFragment.update(bundle);
                return curFragment;
            }
        }

        if (mFragmentManager != null) {
            transaction = mFragmentManager.beginTransaction();
            if (pageName != null) {
                curFragment = (BaseFragment) mFragmentManager.findFragmentByTag(pageName);
            }

            if (prePageName != null) {
                preFragment = (BaseFragment) mFragmentManager.findFragmentByTag(prePageName);
            }
        }

        if (transaction == null) {
            return null;
        }

        if (null != preFragment) {
            transaction.hide(preFragment);
        }

        if (curFragment != null) {
            curFragment.update(bundle);
            transaction.show(curFragment);
            transaction.commitAllowingStateLoss();
        } else {
            transaction.add(layoutID, firstFragment, pageName);
            transaction.addToBackStack(pageName);
            transaction.commitAllowingStateLoss();
        }
        return firstFragment;
    }

    @Override
    public void onBackPressed() {
        final CustomAlertDialog dialog = new CustomAlertDialog(this);
        dialog.builder().setCanceledOnTouchOutside(false)
                .setContent(R.string.text_content_exit)
                .setPositiveButton(R.string.text_positive,
                        new View.OnClickListener() {
                            public void onClick(View v) {
                                KdActivityManager.getInstance().finishAll();
                                dialog.dismiss();
                            }
                        })
                .setNegativeButton(R.string.text_negative, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
        dialog.show();
    }
}
