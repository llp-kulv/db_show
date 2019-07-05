package com.lingrui.db_show.ui.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lingrui.db_show.R;
import com.lingrui.db_show.dbbean.ProductInfoBean;
import com.lingrui.db_show.util.CollectionUtil;
import com.lingrui.db_show.util.Flog;

import java.util.ArrayList;
import java.util.List;

public class SurplusAdapter extends RecyclerView.Adapter<SurplusAdapter.ViewHolder> {
    private final Context mContext;
    private final List<ProductInfoBean> mData = new ArrayList<>();

    public SurplusAdapter(Context mContext, List<ProductInfoBean> list) {
        this.mContext = mContext;
        this.mData.clear();
        if (CollectionUtil.isNotEmpty(list)) {
            this.mData.addAll(list);
        }

        Flog.d("SurplusAdapter", "size:" + this.mData.size());
    }

    public void updateData(List<ProductInfoBean> list) {
        if (CollectionUtil.isEmpty(list)) {
            return;
        }

        this.mData.clear();
        mData.addAll(list);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_surplus, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        ProductInfoBean infoBean = mData.get(position);
        if (infoBean == null) {
            return;
        }

        if (infoBean.getProduct_count() > 0) {
            viewHolder.nameTv.setText(infoBean.getProduct_name());
            viewHolder.countTv.setText(String.valueOf(infoBean.getProduct_count()));
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTv;
        TextView countTv;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.product_name_tv);
            countTv = itemView.findViewById(R.id.product_count_tv);
        }
    }
}
