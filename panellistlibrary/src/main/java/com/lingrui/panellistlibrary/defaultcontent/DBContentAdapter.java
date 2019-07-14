package com.lingrui.panellistlibrary.defaultcontent;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.lingrui.panellistlibrary.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * create by liulp
 * on 2019-07-14 17:51
 */
public class DBContentAdapter extends ArrayAdapter<IDBData> {
    private static final int MAX_ITEM_SIZE = 10;

    private int contentItemSize;

    private List<Integer> itemWidthList;

    private ListView lv_content;
    private int itemHeight;

    public DBContentAdapter(@NonNull Context context, int resource,
                            @NonNull List<IDBData> list, List<Integer> itemWidthList,
                            int itemHeight, ListView lv_content) {
        super(context, resource, list);
        this.contentItemSize = itemWidthList.size();
        this.itemWidthList = itemWidthList;
        this.lv_content = lv_content;
        this.itemHeight = itemHeight;
    }

    @Override
    public int getCount() {
        int count = super.getCount();
        Log.d("ljr", "getCount: " + count);
        return count;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view;
        DBContentAdapter.ViewHolder viewHolder;

        if (convertView == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.defaultcontentitem, parent, false);
            viewHolder = new DBContentAdapter.ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (DBContentAdapter.ViewHolder) view.getTag();
        }

        for (int i = 0; i < contentItemSize; i++) {
            IDBData itemData = getItem(position);
            if (itemData != null) {
                String text = "";
                if (i == 0) {
                    text = itemData.getProduct_name();
                } else if (i == 1) {
                    text = itemData.getProduct_price()+"";
                } else if (i == 2) {
                    text = itemData.getProduct_count() + "";
                } else if (i == 3) {
                    text = itemData.getProduct_total_price()+"";
                } else if (i == 4) {
                    text = itemData.getProduct_time();
                } else {
                    text = itemData.getInfo();
                }
                viewHolder.contentTextViewList.get(i).setText(text);
            }
        }

        if (lv_content.isItemChecked(position)) {
            view.setBackgroundColor(getContext().getResources().getColor(R.color.colorSelected));
        } else {
            view.setBackgroundColor(getContext().getResources().getColor(R.color.colorDeselected));
        }

        return view;
    }

    class ViewHolder {

        List<TextView> contentTextViewList = new ArrayList<>(10);

        ViewHolder(View view) {
            contentTextViewList.add((TextView) view.findViewById(R.id.id_tv_content1));
            contentTextViewList.add((TextView) view.findViewById(R.id.id_tv_content2));
            contentTextViewList.add((TextView) view.findViewById(R.id.id_tv_content3));
            contentTextViewList.add((TextView) view.findViewById(R.id.id_tv_content4));
            contentTextViewList.add((TextView) view.findViewById(R.id.id_tv_content5));
            contentTextViewList.add((TextView) view.findViewById(R.id.id_tv_content6));
            contentTextViewList.add((TextView) view.findViewById(R.id.id_tv_content7));
            contentTextViewList.add((TextView) view.findViewById(R.id.id_tv_content8));
            contentTextViewList.add((TextView) view.findViewById(R.id.id_tv_content9));
            contentTextViewList.add((TextView) view.findViewById(R.id.id_tv_content10));

            for (int i = 0; i < MAX_ITEM_SIZE; i++) {
                try {
                    contentTextViewList.get(i).setWidth(itemWidthList.get(i));
                } catch (Exception e) {
                    contentTextViewList.get(i).setWidth(0);
                }
                contentTextViewList.get(i).setHeight(itemHeight);
            }
        }
    }
}
