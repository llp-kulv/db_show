package com.lingrui.db_show.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.lingrui.db_show.DdApplication;
import com.lingrui.db_show.R;
import com.lingrui.db_show.dbbean.InStockBean;
import com.lingrui.db_show.dbbean.OutStockBean;
import com.lingrui.db_show.dbbean.ProductInfoBean;
import com.lingrui.db_show.dbbean.TestBean;
import com.lingrui.db_show.manager.DatabaseManager;
import com.lingrui.db_show.ui.activity.ExportActivity;
import com.lingrui.db_show.ui.activity.MainActivity;
import com.lingrui.db_show.util.DateUtils;
import com.lingrui.db_show.util.Flog;
import com.lingrui.db_show.util.ScreenUtils;

import org.xutils.DbManager;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.Date;
import java.util.List;

@ContentView(R.layout.fragment_other)
public class OtherFragment extends MainBaseFragment {
    private static final String TAG = "OtherFragment";

    public static OtherFragment newInstance() {
        return new OtherFragment();
    }

    @ViewInject(R.id.add_select_sp)
    private Spinner add_select_sp;

    @ViewInject(R.id.export_excel_btn)
    private Button export_excel_btn;

    @ViewInject(R.id.add_count_tv)
    private EditText add_count_tv;

    @ViewInject(R.id.add_price_tv)
    private EditText add_price_tv;

    @ViewInject(R.id.add_stock)
    private Button add_stock;

    private String addSelectText;

    @ViewInject(R.id.consumption_select_sp)
    private Spinner consumption_select_sp;

    @ViewInject(R.id.consumption_count_tv)
    private EditText consumption_count_tv;

    @ViewInject(R.id.consumption_price_tv)
    private EditText consumption_price_tv;

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
        String price = add_price_tv.getText().toString().trim();
        if (TextUtils.isEmpty(addSelectText) || TextUtils.isEmpty(count) || TextUtils.isEmpty(price)) {
            Toast.makeText(getContext(), R.string.tip_input_info, Toast.LENGTH_SHORT).show();
            return;
        }

        price = handlePrice(price);

        InStockBean bean = new InStockBean();
        bean.setProduct_name(addSelectText);
        bean.setProduct_count(Integer.parseInt(count));
        bean.setProduct_time(DateUtils.dateToStrLong(new Date()));
        bean.setProduct_price(Double.valueOf(price));
        bean.setInfo(add_info_tv.getText().toString());
        DatabaseManager.getInstance().addInStock(bean);

        ScreenUtils.hideSoftKeyboardIfShow(getActivity());
        Toast.makeText(getContext(), "保持成功", Toast.LENGTH_LONG).show();
        notifyChange();
    }

    @Event(R.id.consumption_stock)
    private void consumptionStock(View view) {
        String count = consumption_count_tv.getText().toString().trim();
        String price = consumption_price_tv.getText().toString().trim();
        if (TextUtils.isEmpty(consumptionSelectText) || TextUtils.isEmpty(count) || TextUtils.isEmpty(price)) {
            Toast.makeText(getContext(), R.string.tip_input_info, Toast.LENGTH_SHORT).show();
            return;
        }

        price = handlePrice(price);

        OutStockBean bean = new OutStockBean();
        bean.setProduct_name(consumptionSelectText);
        bean.setProduct_count(Integer.parseInt(count));
        bean.setProduct_time(DateUtils.dateToStrLong(new Date()));
        bean.setInfo(consumption_info_tv.getText().toString());

        WhereBuilder builder = WhereBuilder.b();
        builder.and("product_name", "=", bean.getProduct_name());
        ProductInfoBean infoBean = DatabaseManager.getInstance().getSurplusInfo(builder);
        if (infoBean == null || bean.getProduct_count() > infoBean.getProduct_count()) {
            if (infoBean != null) {
                Flog.d(TAG, "consumption productInfoBeans 库存总数size:" + infoBean.getProduct_count());
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

    private void reset() {
        add_select_sp.setSelection(0);
        add_select_sp.setSelection(0);
        add_info_tv.setText(null);
        consumption_info_tv.setText(null);
        consumption_count_tv.setText(null);
        add_count_tv.setText(null);
        add_price_tv.setText(null);
        consumption_price_tv.setText(null);
    }

    @Event(R.id.export_excel_btn)
    private void clickExport(View view) {
        Intent exportIntent = new Intent(getActivity(), ExportActivity.class);
        startActivity(exportIntent);
    }


    private String handlePrice(String sourcePrice) {
        if (sourcePrice.startsWith(".")) {
            sourcePrice = "0" + sourcePrice;
        }

        if (!sourcePrice.contains(".")) {
            return sourcePrice;
        }

        String[] sp = sourcePrice.split("\\.");
        String suffix = sp[1];
        if (suffix.length() <= 2) {
            return sourcePrice;
        }

        int pre = Integer.parseInt(sp[0]);
        String dotTwoStr = sp[1].substring(0, 2);
        int dotTwoNumber = Integer.parseInt(dotTwoStr);

        int newTwoNumber = dotTwoNumber + 1;
        if (newTwoNumber < 100) {
            return pre + "." + newTwoNumber;
        }

        return (pre + 1) + "";
    }

//    public static void main(String[] args){
//        String ss = handlePrice(".193");
//        System.out.println("ss:"+ss);
//    }

}
