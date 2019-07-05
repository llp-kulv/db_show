package com.lingrui.db_show.ui.fragment;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.lingrui.db_show.DdApplication;
import com.lingrui.db_show.R;
import com.lingrui.db_show.contant.ExcelSize;
import com.lingrui.db_show.dbbean.ProductInfoBean;
import com.lingrui.db_show.excel.ExcelUtils;
import com.lingrui.db_show.manager.DatabaseManager;
import com.lingrui.db_show.ui.adapter.SurplusAdapter;
import com.lingrui.db_show.util.CollectionUtil;
import com.lingrui.db_show.util.Flog;
import com.lingrui.db_show.util.ScreenUtils;
import com.lingrui.db_show.widget.excel.ExcelView;
import com.lingrui.db_show.widget.excel.ExcelViewFragmentTest;
import com.lingrui.db_show.widget.xrecyler.SpaceItemDecoration;
import com.lingrui.db_show.widget.xrecyler.XRecyclerView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.fragment_first)
public class FirstFragment extends MainBaseFragment {
    private static final String TAG = "FirstFragment";

    public static FirstFragment newInstance() {
        return new FirstFragment();
    }

    @ViewInject(R.id.excelView)
    private ExcelView excelView;

    private FirstAdapter surplusAdapter = null;
    static int col = 10, row = 20;
    static String[] headNameArr = null;

    @Override
    protected void createView(View view) {
        headNameArr = ExcelUtils.getHeadNames(getContext());
        col = headNameArr.length;
        List<ProductInfoBean> list = DatabaseManager.getInstance().getSurplusInfo();
        row = list.size() > row ? list.size() : row;

        excelView.setAdapter(surplusAdapter = new FirstAdapter(list));
        excelView.setBackgroundColor(Color.YELLOW);
        Flog.d(TAG, "FirstFragment createView row:" + row + " col:" + col+" item:"+list.get(0));
        excelView.postDelayed(new Runnable() {
            @Override
            public void run() {
                int width = (int) (2 * Math.random());
                excelView.setDividerWidth(width);
            }
        }, 500);
    }

    @Override
    public void onChange() {
        super.onChange();

        if (surplusAdapter == null) {
            return;
        }

        List<ProductInfoBean> list = DatabaseManager.getInstance().getSurplusInfo();
        surplusAdapter.notifyDataSetChanged();
        Flog.d(TAG, "FirstFragment onChange list:" + (CollectionUtil.isEmpty(list) ? "0" : list.size()));
    }

    class FirstAdapter extends ExcelView.ExcelAdapter {
        private final List<ProductInfoBean> mList = new ArrayList<>();

        public FirstAdapter(List<ProductInfoBean> list) {
            if (CollectionUtil.isNotEmpty(list)) {
                mList.addAll(list);
            }
        }

        public void updateData(List<ProductInfoBean> list) {
            if (CollectionUtil.isNotEmpty(list)) {
                mList.clear();
                mList.addAll(list);
                notifyDataSetChanged();
            }
        }

        private static final int HORIZONTAL_TITLE = 1;
        int newCount = 0;
        private final SparseArray<Integer> colArr = new SparseArray<>();

        @Override
        public ExcelView.Span querySpan(int row, int col) {
            return null;
        }

        @Override
        public int getColCount() {
            return col;
        }

        @Override
        public int getRowCount() {
            return row;
        }

        @Override
        public int getRowHeight(int row) {
            return ExcelSize.DEFAULT_EXCEL_HEIGHT;
        }

        @Override
        public int getColWidth(int col) {
            Integer colWidth = colArr.get(col);
            if (colWidth == null) {
                return ExcelSize.DEFAULT_EXCEL_WIDTH;
            }
            return colWidth;
        }

        @Override
        public int getCellViewType(int row, int col) {
            if (row == 0) {
                return HORIZONTAL_TITLE;
            }
            return 0;
        }

        @Override
        public View getCellView(Context context, View convertView, int row, int col) {
            if (getCellViewType(row, col) == 1) {
                // head
                if (convertView == null) {
                    convertView = new TextView(context);
                }
                TextView tv = (TextView) convertView;
                tv.setGravity(Gravity.CENTER);
                for (int i = 0; i < headNameArr.length; i++) {
                    if (i == col) {
                        tv.setText(headNameArr[col]);

                        if (TextUtils.equals(headNameArr[col], getString(R.string.col_date))) {
                            colArr.append(col, ExcelSize.DEFAULT_EXCEL_WIDTH * 3);
                        } else {
                            colArr.append(col, ExcelSize.DEFAULT_EXCEL_WIDTH);
                        }
                        return convertView;
                    }
                }
                return convertView;
            }

            if (convertView == null) {
                convertView = new TextView(context);
                convertView.setBackgroundColor(Color.YELLOW);
                newCount++;
                Log.i(TAG, "newCount=" + newCount);
            }
            TextView tv = (TextView) convertView;
            tv.setGravity(Gravity.CENTER);

            if (row < mList.size()) {
                ProductInfoBean infoBean = mList.get(row - 1);
                Flog.d(TAG, "infoBean:" + infoBean);
                // <item>名称</item> <item>单价</item> <item>数量</item> <item>总价</item> <item>@string/col_date</item> <item>备注</item>
                if (col == 0) {
                    tv.setText(infoBean.getProduct_name());
                } else if (col == 1) {
                    tv.setText(infoBean.getProduct_price());
                } else if (col == 2) {
                    tv.setText(infoBean.getProduct_count());
                } else if (col == 3) {
                    tv.setText(infoBean.getProduct_total_price());
                } else if (col == 4) {
                    tv.setText(infoBean.getProduct_total_price());
                } else if (col == 5) {
                    tv.setText(infoBean.getInfo());
                }
            }

//            String item = String.format("%d, %d", row, col);
//            tv.setText(item);
            Flog.e(TAG, "col:" + col + " row:" + row);
            return convertView;
        }
    }
}
