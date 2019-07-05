package com.lingrui.db_show.ui.fragment;

import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.lingrui.db_show.R;
import com.lingrui.db_show.dbbean.OutStockBean;
import com.lingrui.db_show.manager.DatabaseManager;
import com.lingrui.db_show.ui.adapter.ConsumptionAdapter;
import com.lingrui.db_show.util.CollectionUtil;
import com.lingrui.db_show.util.Flog;
import com.lingrui.db_show.util.ScreenUtils;
import com.lingrui.db_show.widget.xrecyler.SpaceItemDecoration;
import com.lingrui.db_show.widget.xrecyler.XRecyclerView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

@ContentView(R.layout.fragment_second)
public class SecondFragment extends MainBaseFragment {
    private static final String TAG = "SecondFragment";

    public static SecondFragment newInstance() {
        return new SecondFragment();
    }

    @ViewInject(R.id.recyclerView)
    private XRecyclerView recyclerView;

    private ConsumptionAdapter consumptionAdapter = null;

    @Override
    protected void createView(View view) {
        consumptionAdapter = new ConsumptionAdapter(getActivity(), DatabaseManager.getInstance().getConsumptionInfo());

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
        recyclerView.setAdapter(consumptionAdapter);

        Flog.d(TAG, "SecondFragment createView surplusAdapter:" + consumptionAdapter);
    }

    @Override
    public void onChange() {
        super.onChange();

        if (consumptionAdapter == null) {
            return;
        }

        List<OutStockBean> list = DatabaseManager.getInstance().getConsumptionInfo();
        consumptionAdapter.updateData(list);
        Flog.d(TAG, "SecondFragment onChange list:" + (CollectionUtil.isEmpty(list) ? "0" : list.size()));
    }
}
