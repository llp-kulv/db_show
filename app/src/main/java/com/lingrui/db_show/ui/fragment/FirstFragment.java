package com.lingrui.db_show.ui.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lingrui.db_show.R;
import com.lingrui.db_show.dbbean.BaseBean;
import com.lingrui.db_show.dbbean.ProductInfoBean;
import com.lingrui.db_show.excel.AbstractDBPanelListAdapter;
import com.lingrui.db_show.excel.ExcelUtils;
import com.lingrui.db_show.manager.DatabaseManager;
import com.lingrui.db_show.util.CollectionUtil;
import com.lingrui.db_show.util.Flog;
import com.lingrui.panellistlibrary.AbstractPanelListAdapter;
import com.lingrui.panellistlibrary.PanelListLayout;
import com.lingrui.panellistlibrary.defaultcontent.DBContentAdapter;
import com.lingrui.panellistlibrary.defaultcontent.IDBData;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ContentView(R.layout.activity_main)
public class FirstFragment extends MainBaseFragment {
    private static final String TAG = "FirstFragment";

    public static FirstFragment newInstance() {
        return new FirstFragment();
    }

    @ViewInject(R.id.id_pl_root)
    private PanelListLayout pl_root;

    @ViewInject(R.id.id_lv_content)
    private ListView lv_content;

    private AbstractPanelListAdapter adapter;

    private List<List<String>> contentList = new ArrayList<>();
    private List<IDBData> contentBeanList = new ArrayList<IDBData>();

    private List<Integer> itemWidthList = new ArrayList<>();

    private List<String> rowDataList = new ArrayList<>();


    @Override
    protected void createView(View view) {
        // initView();

        initRowDataList();
        initContentDataList();
        initItemWidthList();

        initAdapter();
        pl_root.setAdapter(adapter);
    }

    @Override
    public void onChange() {
        super.onChange();

        if (adapter == null) {
            return;
        }

        initContentDataList();
        adapter.setContentBeanList(contentBeanList);
        adapter.notifyDataSetChanged();
    }

    private void initAdapter(){

        adapter = new AbstractDBPanelListAdapter(getActivity(), pl_root, lv_content) {
            @Override
            protected BaseAdapter getContentAdapter() {
//                return new DBContentAdapter(getContext(), R.layout.defaultcontentitem, contentBeanList,
//                        itemWidthList, 150, lv_content);
                return null;
            }
        };

//        adapter = new AbstractPanelListAdapter(getContext(), pl_root, lv_content) {
//            @Override
//            protected BaseAdapter getContentAdapter() {
//                return null;
//            }
//        };
        adapter.setSwipeRefreshEnabled(false);
        adapter.setRowDataList(rowDataList);// must have
        adapter.setTitle("example");// optional
        // adapter.setOnRefreshListener(new CustomRefreshListener());// optional
        adapter.setContentDataList(contentList);// must have
        adapter.setContentBeanList(contentBeanList);
        adapter.setItemWidthList(itemWidthList);// must have
        adapter.setItemHeight(40);// optional, dp
    }

    private void initView() {
        //设置listView为多选模式，长按自动触发
        lv_content.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        lv_content.setMultiChoiceModeListener(new MultiChoiceModeCallback());

        //listView的点击监听
        lv_content.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "你选中的position为：" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public class CustomRefreshListener implements SwipeRefreshLayout.OnRefreshListener {
        @Override
        public void onRefresh() {
            // you can do sth here, for example: make a toast:
            Toast.makeText(getActivity(), "custom SwipeRefresh listener", Toast.LENGTH_SHORT).show();
            // don`t forget to call this
            adapter.getSwipeRefreshLayout().setRefreshing(false);
        }
    }

    /**
     * 生成一份横向表头的内容
     *
     * @return List<String>
     */
    private void initRowDataList() {
        rowDataList.addAll(Arrays.asList(ExcelUtils.getHeadNames(getContext())));
    }

    /**
     * 初始化content数据
     */
    private void initContentDataList() {
//        for (int i = 1; i <= 3; i++) {
//            List<String> data = new ArrayList<>();
//            data.add("第" + i + "行第一个");
//            data.add("第" + i + "行第二个");
//            data.add("第" + i + "行第三个");
//            data.add("第" + i + "行第四个");
//            data.add("第" + i + "行第五个");
//            data.add("第" + i + "行第六个");
//            data.add("第" + i + "行第七个");
//            contentList.add(data);
//        }

        List<ProductInfoBean> list = DatabaseManager.getInstance().getSurplusInfo();
        if (list != null && list.size() > 0) {
            contentBeanList.clear();
            contentBeanList.addAll(list);
        }
    }

    /**
     * 初始化 content 部分每一个 item 的每个数据的宽度
     */
    private void initItemWidthList() {
        int[] widths = ExcelUtils.getHeadWidth(getContext());
        itemWidthList.addAll(Arrays.asList(Arrays.stream(widths).boxed().toArray(Integer[]::new)));
    }

    /**
     * 更新content数据源
     */
    private void changeContentDataList() {
        contentList.clear();
        for (int i = 1; i < 500; i++) {
            List<String> data = new ArrayList<>();
            data.add("第" + i + "第一个");
            data.add("第" + i + "第二个");
            data.add("第" + i + "第三个");
            data.add("第" + i + "第四个");
            data.add("第" + i + "第五个");
            data.add("第" + i + "第六个");
            data.add("第" + i + "第七个");
            contentList.add(data);
        }
    }

    /**
     * 插入一个数据
     */
    private void insertData() {
        List<String> data = new ArrayList<>();
        data.add("插入1");
        data.add("插入2");
        data.add("插入3");
        data.add("插入4");
        data.add("插入5");
        data.add("插入6");
        data.add("插入7");
        contentList.add(5, data);
    }

    /**
     * 删除一个数据
     */
    private void removeData() {
        contentList.remove(10);
    }

    /**
     * 多选模式的监听器
     */
    private class MultiChoiceModeCallback implements ListView.MultiChoiceModeListener {

        private View actionBarView;
        private TextView tv_selectedCount;

        /**
         * 进入ActionMode时调用
         * 可设置一些菜单
         *
         * @param mode
         * @param menu
         * @return
         */
//        @Override
//        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
//            // menu
//            getMenuInflater().inflate(R.menu.menu_multichoice, menu);
//            // actionBar
//            if (actionBarView == null) {
//                actionBarView = LayoutInflater.from(MainActivity.this).inflate(R.layout.actionbar_listviewmultichoice, null);
//                tv_selectedCount = (TextView) actionBarView.findViewById(R.id.id_tv_selectedCount);
//            }
//            mode.setCustomView(actionBarView);
//            return true;
//        }

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        /**
         * 和onCreateActionMode差不多的时机调用，不写逻辑
         *
         * @param mode
         * @param menu
         * @return
         */
        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            return false;
        }

        /**
         * 当ActionMode的菜单项被点击时
         *
         * @param mode
         * @param item
         * @return
         */
//        @Override
//        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
//            switch (item.getItemId()) {
//                case R.id.id_menu_selectAll:
//                    for (int i = 0; i < lv_content.getAdapter().getCount(); i++) {
//                        lv_content.setItemChecked(i, true);
//                    }
//                    tv_selectedCount.setText(String.valueOf(lv_content.getAdapter().getCount()));
//                    break;
//                case R.id.id_menu_draw:
//                    //draw
//                    SparseBooleanArray booleanArray = lv_content.getCheckedItemPositions();
//                    Log.d("ybz", booleanArray.toString());
//
//                    List<Integer> checkedItemPositionList = new ArrayList<>();
//                    for (int i = 0; i < contentList.size(); i++) {
//                        if (lv_content.isItemChecked(i)) {
//                            checkedItemPositionList.add(i);
//                            Log.d("ybz", "被选中的item： " + i);
//                        }
//                    }
//
//                    StringBuilder checkedItemString = new StringBuilder();
//                    for (int i = 0; i < checkedItemPositionList.size(); i++) {
//                        checkedItemString.append(checkedItemPositionList.get(i) + ",");
//                    }
//
//                    Toast.makeText(MainActivity.this, "你选中的position有：" + checkedItemString, Toast.LENGTH_SHORT).show();
//                    break;
//                default:
//                    break;
//            }
//            return true;
//        }

        /**
         * 退出ActionMode时调用
         *
         * @param mode
         */
        @Override
        public void onDestroyActionMode(ActionMode mode) {
            lv_content.clearChoices();
        }

        /**
         * 当item的选中状态发生改变时调用
         *
         * @param mode
         * @param position
         * @param id
         * @param checked
         */
        @Override
        public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
            int selectedCount = lv_content.getCheckedItemCount();
            tv_selectedCount.setText(String.valueOf(selectedCount));
            ((ArrayAdapter) lv_content.getAdapter()).notifyDataSetChanged();
        }
    }
}
