package com.lingrui.db_show;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lingrui.db_show.util.Flog;

import org.xutils.x;

import java.lang.ref.WeakReference;

public abstract class BaseFragment extends Fragment {
    private static final String TAG = "BaseFragment";
    private boolean injected = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return x.view().inject(this, inflater, container);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!injected) {
            x.view().inject(this, this.getView());
        }

        createLoader();
        createView(view);
        Flog.d(TAG, "onViewCreated:" + this + " injected:" + injected);
    }

    protected void createLoader() {

    }

    protected void destroyLoader() {

    }

    protected abstract void createView(View view);

    @Override
    public void startActivity(Intent intent) {
        try {
            super.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            // ignore
            Flog.d(TAG, "ActivityNotFoundException e:" + e);
        }
    }

    public void update(Bundle arg) {
    }

    public boolean back() {
        return true;
    }

    protected abstract static class WeakHandler<T> extends Handler {

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
            handleInnerMessage(msg);
        }

        public abstract void handleInnerMessage(Message msg);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        destroyLoader();
    }
}
