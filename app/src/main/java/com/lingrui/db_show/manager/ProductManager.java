package com.lingrui.db_show.manager;

import android.content.Context;

import com.lingrui.db_show.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductManager {
    private final Context mContext;
    private List<String> allProductList = new ArrayList<>();

    public ProductManager(Context context) {
        mContext = context;

        String[] arr = mContext.getResources().getStringArray(R.array.product_list);
        allProductList.addAll(Arrays.asList(arr));
    }


}
