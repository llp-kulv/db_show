package com.lingrui.db_show.widget.excel;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lingrui.db_show.R;

public class ExcelViewFragmentTest extends Fragment {
    private static final String TAG = "ExcelFragment-item1";
    ExcelView excelView;
    MyAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_excel_test, null);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        excelView = getView().findViewById(R.id.excelView);
        excelView.setAdapter(adapter = new MyAdapter());
        excelView.setBackgroundColor(Color.YELLOW);
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        excelView.postDelayed(new Runnable() {
            @Override
            public void run() {
                int width = (int) (2 * Math.random());
                excelView.setDividerWidth(width);
            }
        }, 500);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.add("滚").setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add("大").setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add("粗").setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add("色").setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getTitle().equals("滚")) {
            excelView.scrollTo(800, 800);
        } else if (item.getTitle().equals("大")) {
            float weight = Math.max(2, (float) (10 * Math.random()));
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) excelView.getLayoutParams();
            params.weight = weight;
            excelView.setLayoutParams(params);
        } else if (item.getTitle().equals("粗")) {
            int width = (int) (20 * Math.random());
            excelView.setDividerWidth(width);
        } else if (item.getTitle().equals("色")) {
            int colors[] = {
                    Color.BLUE, Color.YELLOW, Color.GREEN, Color.RED, Color.LTGRAY,
                    Color.MAGENTA, Color.CYAN
            };
            int i = (int) (colors.length * Math.random());
            excelView.setDividerColor(colors[i]);
        }
        return super.onOptionsItemSelected(item);
    }

    static int col = 15, row = 20;

    class MyAdapter extends ExcelView.ExcelAdapter {
        private static final int horizontal_title = 1;
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
            return 150;
        }

        @Override
        public int getColWidth(int col) {
            Integer colWidth = colArr.get(col);
            if (colWidth == null) {
                return 100;
            }
            return colWidth;
        }

        @Override
        public int getCellViewType(int row, int col) {
            if (row == 0) {
                return horizontal_title;
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
                if (col % 3 == 0) {
                    tv.setText("abcd");
                    colArr.append(col, 100);
                } else {
                    tv.setText("H-----E----A-----D");
                    colArr.append(col, 300);
                }
                return convertView;
            }

            if (convertView == null) {
                convertView = new TextView(context);
                convertView.setBackgroundColor(Color.WHITE);
                newCount++;
                Log.i(TAG, "newCount=" + newCount);
            }
            TextView tv = (TextView) convertView;
            tv.setGravity(Gravity.CENTER);
            String item = String.format("%d, %d", row, col);
            tv.setText(item);
            Log.e(TAG, "item1=" + item);
            return convertView;
        }
    }
}
