package com.lingrui.db_show.excel;


import android.content.Context;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.lingrui.db_show.dbbean.BaseBean;
import com.lingrui.panellistlibrary.AbstractPanelListAdapter;
import com.lingrui.panellistlibrary.PanelListLayout;
import com.lingrui.panellistlibrary.defaultcontent.DefaultContentAdapter;
import com.lingrui.panellistlibrary.defaultcontent.IDBData;

import java.util.List;

/**
 * create by liulp
 * on 2019-07-14 17:46
 */
public abstract class AbstractDBPanelListAdapter extends AbstractPanelListAdapter {
    /**
     * constructor
     *
     * @param context
     * @param pl_root
     * @param lv_content 内容的ListView
     */
    public AbstractDBPanelListAdapter(Context context, PanelListLayout pl_root, ListView lv_content) {
        super(context, pl_root, lv_content);
        isDefaultAdapter = true;
    }


}
