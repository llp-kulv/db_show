package com.lingrui.db_show.util;

import android.app.Activity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class KdActivityManager {
    private WeakReference<Activity> currentActivity;
    private static KdActivityManager mInstance;
    private Stack<WeakReference<Activity>> mQueue = new Stack<WeakReference<Activity>>();

    private KdActivityManager() {
    }

    public static KdActivityManager getInstance() {
        if (mInstance == null) {
            mInstance = new KdActivityManager();
        }
        return mInstance;
    }

    /**
     * 添加一个页面到栈顶
     */
    public void addActivity(Activity add) {
        removeActivity(add);

        this.currentActivity = new WeakReference<Activity>(add);
        mQueue.push(currentActivity);
    }

    /**
     * 清除掉已经finish的activity
     *
     * @param removeActivity 被finish的activity
     */
    public void removeActivity(Activity removeActivity) {
        List<WeakReference<Activity>> destroyedActivities = new ArrayList<WeakReference<Activity>>();
        for (WeakReference<Activity> wr : mQueue) {
            Activity activity = null;
            if ((activity = wr.get()) != null && removeActivity == activity) {
                destroyedActivities.add(wr);
            }
        }

        for (WeakReference<Activity> wr : destroyedActivities) {
            mQueue.remove(wr);
        }
    }

    /**
     * 清除栈中所有的页面，只在退出时调用
     */
    public void finishAll() {
        for (WeakReference<Activity> wr : mQueue) {
            Activity activity = null;
            if ((activity = wr.get()) != null && !activity.isFinishing()) {
                activity.finish();
            }
        }
    }

    /**
     * 获取当前显示的页面，刚启动的时候有可能是null
     *
     * @return
     */
    public Activity getCurrentActivity() {
        if (currentActivity == null)
            return null;
        return currentActivity.get();
    }

    /**
     * <p>
     * 获取当前页面用于显示dialog，目的并不是为了知道当前页面是什么而是为了在当前页面显示dialog，<br>
     * 增加此方法是因为ActivityGroup里面的Activity直接用于显示dialog会抛异常，所以只能返回ActivityGroup用于显示
     * </p>
     *
     * @return
     */
    public Activity getCurrentActivityToShowDialog() {
        Activity activity = getCurrentActivity();
        if (activity == null)
            return null;
        while (activity.getParent() != null) {
            activity = activity.getParent();
        }
        return activity;
    }

}
