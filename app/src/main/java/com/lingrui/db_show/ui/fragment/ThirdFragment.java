package com.lingrui.db_show.ui.fragment;

import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.lingrui.db_show.R;
import com.lingrui.db_show.dbbean.InStockBean;
import com.lingrui.db_show.manager.DatabaseManager;
import com.lingrui.db_show.ui.adapter.InStockAdapter;
import com.lingrui.db_show.util.CollectionUtil;
import com.lingrui.db_show.util.Flog;
import com.lingrui.db_show.util.ScreenUtils;
import com.lingrui.db_show.xrecyler.SpaceItemDecoration;
import com.lingrui.db_show.xrecyler.XRecyclerView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

@ContentView(R.layout.fragment_third)
public class ThirdFragment extends MainBaseFragment {
    private static final String TAG = "ThirdFragment";

    public static ThirdFragment newInstance() {
        return new ThirdFragment();
    }

    @ViewInject(R.id.recyclerView)
    private XRecyclerView recyclerView;

    private InStockAdapter inStockAdapter = null;

    @Override
    protected void createView(View view) {
        inStockAdapter = new InStockAdapter(getActivity(), DatabaseManager.getInstance().getInStockInfo());

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
        recyclerView.setAdapter(inStockAdapter);

        Flog.d(TAG, "ThirdFragment createView inStockAdapter:" + inStockAdapter);
    }

    @Override
    public void onChange() {
        super.onChange();

        if (inStockAdapter == null) {
            return;
        }

        List<InStockBean> list = DatabaseManager.getInstance().getInStockInfo();
        inStockAdapter.updateData(list);
        Flog.d(TAG, "ThirdFragment onChange list:" + (CollectionUtil.isEmpty(list) ? "0" : list.size()));
    }
}
