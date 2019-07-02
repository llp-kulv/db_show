package com.lingrui.db_show.xrecyler;

public interface BaseRefreshHeader {
    int STATE_NORMAL = 0;
    int STATE_RELEASE_TO_REFRESH = 1;
    int STATE_REFRESHING = 2;
    int STATE_DONE = 3;

    void onMove(float var1);

    boolean releaseAction();

    void refreshComplete();
}
