package com.lingrui.db_show.ui.fragment;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.lingrui.db_show.R;
import com.lingrui.db_show.dbbean.InStockBean;
import com.lingrui.db_show.dbbean.OutStockBean;
import com.lingrui.db_show.dbbean.ProductInfoBean;
import com.lingrui.db_show.manager.DatabaseManager;
import com.lingrui.db_show.ui.activity.MainActivity;
import com.lingrui.db_show.util.DateUtils;
import com.lingrui.db_show.util.Flog;
import com.lingrui.db_show.util.ScreenUtils;

import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.Date;

@ContentView(R.layout.fragment_other)
public class OtherFragment extends MainBaseFragment {
    private static final String TAG = "OtherFragment";

    public static OtherFragment newInstance() {
        return new OtherFragment();
    }

    @ViewInject(R.id.add_select_sp)
    private Spinner add_select_sp;

    @ViewInject(R.id.add_count_tv)
    private EditText add_count_tv;

    @ViewInject(R.id.add_stock)
    private Button add_stock;

    private String addSelectText;

    @ViewInject(R.id.consumption_select_sp)
    private Spinner consumption_select_sp;

    @ViewInject(R.id.consumption_count_tv)
    private EditText consumption_count_tv;

    @ViewInject(R.id.consumption_info_tv)
    private EditText consumption_info_tv;

    @ViewInject(R.id.add_info_tv)
    private EditText add_info_tv;

    @ViewInject(R.id.consumption_stock)
    private Button consumption_stock;

    private String consumptionSelectText;

    private String[] dataArr;

    protected void createView(View view) {
        dataArr = getResources().getStringArray(R.array.product_list);
        initAddAdapter();
        initConsumptionAdapter();
    }

    private void initConsumptionAdapter() {
        //适配器
        final ArrayAdapter arr_adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, dataArr);
        //设置样式
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        consumption_select_sp.setAdapter(arr_adapter);

        consumption_select_sp.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    consumptionSelectText = (String) arr_adapter.getItem(position);
                }
                Flog.d(TAG, "position:" + position + " consumptionSelectText:" + consumptionSelectText);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Flog.d(TAG, "Nothing");
            }
        });
    }

    private void initAddAdapter() {
        //适配器
        final ArrayAdapter arr_adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, dataArr);
        //设置样式
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        add_select_sp.setAdapter(arr_adapter);

        add_select_sp.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    addSelectText = (String) arr_adapter.getItem(position);
                }
                Flog.d(TAG, "position:" + position + " addSelectText:" + addSelectText);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Flog.d(TAG, "Nothing");
            }
        });
    }

    @Event(R.id.add_stock)
    private void addStock(View view) {
        String count = add_count_tv.getText().toString().trim();
        if (TextUtils.isEmpty(addSelectText) || TextUtils.isEmpty(count)) {
            Toast.makeText(getContext(), "必须选择产品和数量", Toast.LENGTH_SHORT).show();
            return;
        }

        InStockBean bean = new InStockBean();
        bean.setProduct_name(addSelectText);
        bean.setIn_count(Integer.parseInt(count));
        bean.setIn_time(DateUtils.dateToStrLong(new Date()));
        bean.setIn_info(add_info_tv.getText().toString());
        DatabaseManager.getInstance().addInStock(bean);

        ScreenUtils.hideSoftKeyboardIfShow(getActivity());
        Toast.makeText(getContext(), "保持成功", Toast.LENGTH_LONG).show();
        notifyChange();
    }

    @Event(R.id.consumption_stock)
    private void consumptionStock(View view) {
        String count = consumption_count_tv.getText().toString().trim();
        Flog.d(TAG, "consumptionStock consumptionSelectText:" + consumptionSelectText + " count:" + count);
        if (TextUtils.isEmpty(consumptionSelectText) || TextUtils.isEmpty(count)) {
            Toast.makeText(getContext(), "必须选择产品和数量", Toast.LENGTH_SHORT).show();
            return;
        }

        OutStockBean bean = new OutStockBean();
        bean.setProduct_name(consumptionSelectText);
        bean.setOut_count(Integer.parseInt(count));
        bean.setOut_time(DateUtils.dateToStrLong(new Date()));
        bean.setOut_info(consumption_info_tv.getText().toString());

        WhereBuilder builder = WhereBuilder.b();
        builder.and("product_name", "=", bean.getProduct_name());
        ProductInfoBean infoBean = DatabaseManager.getInstance().getSurplusInfo(builder);
        if (infoBean == null || bean.getOut_count() > infoBean.getCurrent_count()) {
            if (infoBean != null) {
                Flog.d(TAG, "consumption productInfoBeans 库存总数size:" + infoBean.getCurrent_count());
            }
            Toast.makeText(getContext(), "无法领取的产品，因为领取数量超过库存数量", Toast.LENGTH_SHORT).show();
            return;
        }
        DatabaseManager.getInstance().addOutStock(bean);

        ScreenUtils.hideSoftKeyboardIfShow(getActivity());
        Toast.makeText(getContext(), "保持成功", Toast.LENGTH_LONG).show();
        notifyChange();
    }

    private void notifyChange() {
        Activity activity = getActivity();
        if (activity instanceof MainActivity) {
            ((MainActivity) activity).change();
        }

        reset();
    }

    private void reset(){
        add_select_sp.setSelection(0);
        add_select_sp.setSelection(0);
        add_info_tv.setText(null);
        consumption_info_tv.setText(null);
        consumption_count_tv.setText(null);
        add_count_tv.setText(null);
    }
}
