package com.lingrui.db_show.ui.fragment;

import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.lingrui.db_show.R;
import com.lingrui.db_show.dbbean.ProductInfoBean;
import com.lingrui.db_show.manager.DatabaseManager;
import com.lingrui.db_show.ui.adapter.SurplusAdapter;
import com.lingrui.db_show.util.CollectionUtil;
import com.lingrui.db_show.util.Flog;
import com.lingrui.db_show.util.ScreenUtils;
import com.lingrui.db_show.widget.xrecyler.SpaceItemDecoration;
import com.lingrui.db_show.widget.xrecyler.XRecyclerView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

@ContentView(R.layout.fragment_one)
public class FirstFragmentBak extends MainBaseFragment {
    private static final String TAG = "FirstFragment";

    public static FirstFragmentBak newInstance() {
        return new FirstFragmentBak();
    }

    @ViewInject(R.id.recyclerView)
    private XRecyclerView recyclerView;

    private SurplusAdapter surplusAdapter = null;

    @Override
    protected void createView(View view) {
        surplusAdapter = new SurplusAdapter(getActivity(), DatabaseManager.getInstance().getSurplusInfo());

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        SpaceItemDecoration decoration = new SpaceItemDecoration(ScreenUtils.dip2px(5));
        recyclerView.addItemDecoration(decoration);
        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                Flog.d(TAG, "onRefresh");
            }

            @Override
            public void onLoadMore() {
                Flog.d(TAG, "onLoadMore");
            }
        });
        recyclerView.setPullRefreshEnabled(false);
        recyclerView.setAdapter(surplusAdapter);

        Flog.d(TAG, "FirstFragment createView surplusAdapter:" + surplusAdapter);
    }

    @Override
    public void onChange() {
        super.onChange();

        if (surplusAdapter == null) {
            return;
        }

        List<ProductInfoBean> list = DatabaseManager.getInstance().getSurplusInfo();
        surplusAdapter.updateData(list);
        Flog.d(TAG, "FirstFragment onChange list:" + (CollectionUtil.isEmpty(list) ? "0" : list.size()));
    }
}
